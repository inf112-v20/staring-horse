package inf112.skeleton.app.screen;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.gamelogic.GameLogic;
import inf112.skeleton.app.gamelogic.GameLoop;
import inf112.skeleton.app.robot.Player;
import inf112.skeleton.app.programcard.ProgramCard;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.robot.AI;
import inf112.skeleton.app.robot.IRobot;
import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen extends InputAdapter implements Screen {

    public static final int TILE_AREA = 300;
    private TiledMap tiledMap;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer laserLayer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private TiledMapTileLayer.Cell laserVerticalCell;
    private TiledMapTileLayer.Cell laserHorizontalCell;
    private TiledMapTileLayer.Cell laserCrossCell;

    private Stage stage;

    private Player player;
    private ArrayList<AI> aiList;

    private RoboRally roboRally = RoboRally.getInstance();

    private static GameScreen SINGLE_INSTANCE = null;
    private GameLoop gameLoop;
    private GameLogic gameLogic;

    private ArrayList<ImageButton> selectableCardButtons;
    private ArrayList<Image> handCards;

    private HashMap<IRobot,TiledMapTileLayer.Cell> robotCellHashMap;

    private SpriteBatch batch;
    private BitmapFont font;

    private Image playerIcon;

    // delay testing
    private float time;
    private boolean isWaiting;
    private int phase;
    private Table finishGamePopUp;
    private Label winOrLoseText;
    private TextButton activateCardsButton;

    private Skin mySkin;
    private ArrayList<Label> priorityLabelList = new ArrayList<>();
    private ArrayList<Label> handPriorityLabelList;
    private ArrayList<Vector2> respawnPoints;

    private GameScreen(){}

    public static GameScreen getInstance() {
        if (SINGLE_INSTANCE == null)
            SINGLE_INSTANCE = new GameScreen();

        return SINGLE_INSTANCE;
    }

    @Override
    public void show() {
        // remove when overhauling delay.
        time = 0f;
        this.isWaiting = false;
        this.phase = 0;
        tiledMap = new TmxMapLoader().load("Maps/" + roboRally.getGameMap());
        OrthographicCamera camera = new OrthographicCamera();

        this.gameLogic = new GameLogic();
        respawnPoints = gameLogic.getAllPositionsFromObjectName("SpawnPoint");
        player = new Player();

        initializeLaserLayer();
        TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();
        playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Player");
        TiledMapTileLayer boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");
        playerCell.setTile(new StaticTiledMapTile(player.getTexture()));

        robotCellHashMap = new HashMap<>();
        robotCellHashMap.put(player, playerCell);
        createAIs();

        // create a stage for image buttons.
        stage = new Stage(new FitViewport(900,900, camera));
        createInputMultiplexer();

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, (float) 1/TILE_AREA);
        camera.setToOrtho(false, boardLayer.getWidth(), boardLayer.getHeight());
        camera.update();
        orthogonalTiledMapRenderer.setView(camera);

        makeSelectableCards();
        makePlayerHandImages();

        gameLoop = new GameLoop(player, aiList, this);
        gameLoop.startNewRound();

        setAllRespawnPoints(respawnPoints);
        renderRobots(getRobots());
        createBatchFontAndPlayerIcon();
        createFinishGamePopUp();
        createActivateCardsButton();
    }

    private void createActivateCardsButton() {
        activateCardsButton = new TextButton("Activate\ncards", new Skin(Gdx.files.classpath("skin/star-soldier-ui.json")));
        stage.addActor(activateCardsButton);

        activateCardsButton.setPosition(stage.getWidth() - 175,150);
        activateCardsButton.setVisible(false);

        activateCardsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (player.isHandFull()){
                    isWaiting = true;
                    activateCardsButton.setVisible(false);
                }
            }
        });
    }


    private void initializeLaserLayer() {
        laserVerticalCell = new TiledMapTileLayer.Cell();
        laserHorizontalCell = new TiledMapTileLayer.Cell();
        laserCrossCell = new TiledMapTileLayer.Cell();

        laserLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Laser");
        laserVerticalCell.setTile(new StaticTiledMapTile(new TextureRegion(new Texture("laser.png"))));
        laserHorizontalCell.setTile(laserVerticalCell.getTile());
        laserCrossCell.setTile(new StaticTiledMapTile(new TextureRegion(new Texture("laserCross.png"))));

    }

    private void createAIs() {
        aiList = new ArrayList<>();
        AI.resetRobotID();
        int aiNumber = Math.min(roboRally.getAiNumber(),respawnPoints.size()-1);

        for(int i = 0; i < aiNumber; i++){
            AI newAI = new AI();
            newAI.setDifficulty(roboRally.getDifficultyAI());

            TiledMapTileLayer.Cell aiCell = new TiledMapTileLayer.Cell();
            aiCell.setTile(new StaticTiledMapTile(newAI.getTexture()));

            robotCellHashMap.put(newAI, aiCell);
            aiList.add(newAI);
        }
    }

    private void createBatchFontAndPlayerIcon() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale((float) 1.2);
        font.setColor(Color.WHITE);

        playerIcon = new Image();
        playerIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(player.getTexture())));
        playerIcon.setSize(50, 50);
        playerIcon.setPosition(30, 50);

        stage.addActor(playerIcon);
    }

    private void renderRobots(ArrayList<IRobot> robots) {
        for (IRobot robot : robots)
            renderRobot(robot);
    }

    private void createInputMultiplexer() {
        // creates input processors for both clicking on cards and keyboard.
        InputProcessor inputProcessorOne = stage;
        InputProcessor inputProcessorTwo = this;
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    private void setAllRespawnPoints(ArrayList<Vector2> respawnPoints) {
        for (int i = 0; i < getRobots().size(); i++) {
            getRobots().get(i).setRespawnPoint(respawnPoints.get(i));
        }
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(45/255f, 45/255f, 47/255f, 44/255f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        drawPlayerInfoText();
        if (phaseIsWaiting()) {
            phaseWait(Gdx.graphics.getDeltaTime(), gameLoop, this.phase);

        }

        stage.act();
        stage.draw();
    }

    /**
     * waits until time has exceeded speed and then startsactivationphase.
     * Maybe chuck this into gamelogic or something else entirely.
     */
    private void phaseWait(float deltaTime, GameLoop gameLoop, int phase) {
        time += deltaTime;
        double speed = 1; // be able to change this on the fly or in the main menu
        //System.out.println(time);
        if (time >= speed) {
            if (phase < 6) {
                gameLoop.startActivationPhase(phase);
                this.phase += 1;
            } else {
                this.isWaiting = false;
                this.phase = 0;
            }
            time = 0;
        }
    }

    /**
     * only true if not in an activationphase
     * @return boolean
     */
    private boolean phaseIsWaiting() {
        return this.isWaiting;
    }

    private void drawPlayerInfoText() {
        String playerInfoText = player.getName() + "\n" +
                "Lives: " + player.getLives() + "\n" +
                "Health: " + player.getHealthPoints() + "\n" +
                "Flags taken: " + player.getFlagsTaken() + "\n" +
                "Direction: " + player.getDirection() + "\n" +
                "Position: " + player.getPos().toString();
        batch.begin();
        // Player Info
        font.draw(batch, playerInfoText, playerIcon.getX(), playerIcon.getY() + 200);
        // Button-pressing Info
        font.draw(batch, "'Q': Main Menu \n" +
                "'F': enter/exit Fullscreen", stage.getWidth()-75, 100);
        batch.end();
    }

    /**
     * Finish game when player wins
     */
    public void playerWin(){
        showFinishGamePopUp("You win!");
    }

    /**
     * Finish game when player loses
     */
    public void playerLose(){
        showFinishGamePopUp("You lose!");
    }

    /**
     * Check if there is only one robot left and then finish game
     */
    public void onlyOneRobotLeftCheck(){
        ArrayList<IRobot> livingRobots = new ArrayList<>();

        for(IRobot robot:getRobots()){
            if(robot.isAlive())
                livingRobots.add(robot);
        }

        if(livingRobots.size() == 1) {
            System.out.println("ALL OTHER ROBOTS ARE DEAD!");
            playerWin();
        }
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    /**
     * Update robots Cell-rotation based on Direction
     * @param robot to update cell
     */
    public void updateRobotRotation(IRobot robot){
        TiledMapTileLayer.Cell cell = robotCellHashMap.get(robot);
        switch (robot.getDirection()){
            case NORTH:
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_0);
                break;
            case WEST:
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_90);
                break;
            case SOUTH:
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_180);
                break;
            case EAST:
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_270);
                break;
            default:
                break;
        }
    }

    public void drawLaserOnPos(Vector2 pos, Direction dir) {
        if (laserLayer.getCell((int) pos.x, (int) pos.y) != null) {
            if (laserLayer.getCell((int) pos.x, (int) pos.y) == laserHorizontalCell && (dir == Direction.EAST || dir == Direction.WEST)) {
                return;
            }
            else if (laserLayer.getCell((int) pos.x, (int) pos.y) == laserVerticalCell && (dir == Direction.NORTH || dir == Direction.SOUTH)) {
                return;
            }
            else {
                laserLayer.setCell((int) pos.x, (int) pos.y, laserCrossCell);
                return;
            }
        }
        if (dir == Direction.EAST || dir == Direction.WEST) {
            laserHorizontalCell.setRotation(TiledMapTileLayer.Cell.ROTATE_90);
            laserLayer.setCell((int) pos.x, (int) pos.y, laserHorizontalCell);
        }
        else if (dir == Direction.NORTH || dir == Direction.SOUTH) {
            laserLayer.setCell((int) pos.x, (int) pos.y, laserVerticalCell);
        }
    }

    public void unrenderLasers() {
        ArrayList<Vector2> laserPositions = gameLogic.getAllPositionsFromObjectName("Laser");
        for (Vector2 laserPos : laserPositions) {
            laserLayer.setCell((int) laserPos.x, (int) laserPos.y, null);
        }
    }

    /**
     * Creates a new ImageButton of a input card
     * @return a ImageButton.
     */
    public ImageButton makeCardImageButton(final Player player, final GameScreen gameScreen, final int i) {

        // create a drawable for each state of the button
        final Drawable drawable = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal("ProgramCards/ProgramCardAgain.png"))));
        final ImageButton imageButton = new ImageButton(drawable);

        imageButton.setSize((float) 200 / 4, (float) 340 / 4);

        imageButton.setPosition(30,55);

        imageButton.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                // could be useful in the future.
            }

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                if (!player.isHandFull() && !phaseIsWaiting()) {
                    imageButton.setVisible(false);
                    //System.out.println("Pressed: imagebutton " + i);
                    gameScreen.addCardToPlayerHand(player.getProgramCard(i));
                } else {
                    System.out.println("Hand is full");
                }


                if(player.isHandFull() && !phaseIsWaiting()) {
                    activateCardsButton.setVisible(true);
                }

                return true;
            }
        });

        return imageButton;
    }

    private void addCardToPlayerHand(ProgramCard programCard) {
        player.addCardToHand(programCard);
        Image image = handCards.get(player.getNumberOfCardsInHand()-1);
        image.setVisible(true);
        changeImageTexture(image, programCard.getTexture());

        // add label to the new image
        Label label = handPriorityLabelList.get(player.getNumberOfCardsInHand()-1);
        label.setText("" + programCard.getPriority());
        label.setVisible(true);

        // remove the same label from the selectable cards.
        for (Label l : priorityLabelList) {
            if (l.getText().equals(label.getText())) {
                l.remove();
            }
        }

    }

    public void changeImageButtonDrawable(ImageButton button, ProgramCard card) {
        button.getStyle() .imageUp = new TextureRegionDrawable(new TextureRegion(card.getTexture()));
    }

    public void makePlayerDeckMatchSelectableCards() {
        // remove all priority labels for the previous cards.
        removePriorityOnCard();
        for(ImageButton card:selectableCardButtons){
            card.setVisible(false);
        }
        for(int i = 0; i < (9-(Math.min(player.getDamageTaken(),4))); i++) {
            selectableCardButtons.get(i).setVisible(true);
            changeImageButtonDrawable(selectableCardButtons.get(i), player.getProgramCard(i));
            drawPriorityOnCard(i); // add priority label
        }
    }

    /**
     * Remove all labels and clear the Arraylist of labels.
     * Only the card deck for hand it removes itself in
     */
    private void removePriorityOnCard() {
        for (Label label : priorityLabelList) {
            label.remove();
        }
        priorityLabelList.clear();
    }

    private void drawPriorityOnCard(int i) {

        ImageButton card = selectableCardButtons.get(i);
        int priority = player.getProgramCard(i).getPriority();

        // add new label on correct position.
        // labels for Priority.
        Label priorityLabel = new Label("" + priority, mySkin);
        priorityLabel.setSize(card.getWidth(), card.getY());
        priorityLabel.setPosition(card.getX(), card.getY() + card.getHeight() - priorityLabel.getHeight());
        priorityLabel.setAlignment(Align.center);
        priorityLabelList.add(priorityLabel);
        stage.addActor(priorityLabel);
    }

    /**
     * make each card in a deck into an imageButton
     */
    private void makeSelectableCards(){
        this.player.drawNewDeck();

        selectableCardButtons = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            // placeholder for texture
            ProgramCard card = new ProgramCard();

            ImageButton cardButton = makeCardImageButton(player, this, i);
            selectableCardButtons.add(cardButton);

            float cardSizeDivisor = 3.8f;
            cardButton.setSize((float) card.getTexture().getWidth() / cardSizeDivisor, (float) card.getTexture().getHeight() / cardSizeDivisor);
            cardButton.setPosition(stage.getWidth()/2 + (int)((i-4.5) * (cardButton.getWidth() + 5)), 30);

            changeImageButtonDrawable(cardButton, card);

            stage.addActor(cardButton);
        }
    }

    public void changeImageTexture(Image image, Texture texture){
        image.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    private void makePlayerHandImages(){

        handCards = new ArrayList<>();
        handPriorityLabelList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            // placeholder for texture
            ProgramCard card = new ProgramCard();

            Image image = new Image();
            image.setDrawable(new TextureRegionDrawable(new TextureRegion(card.getTexture())));

            int cardSizeDivisor = 4;
            image.setSize((float) card.getTexture().getWidth() / cardSizeDivisor, (float) card.getTexture().getHeight() / cardSizeDivisor);
            image.setPosition(stage.getWidth()/2 + (int)((i-2.5) * (image.getWidth() + 5)), 150);

            handCards.add(image);
            stage.addActor(image);

            // make priorityLabel for each card
            mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
            Label handPriorityLabel = new Label(null, mySkin);
            handPriorityLabel.setPosition(image.getX() + 8 ,image.getY() - 4 );
            handPriorityLabel.setSize(image.getWidth(), image.getY());

            handPriorityLabelList.add(handPriorityLabel);
            stage.addActor(handPriorityLabel);

            handPriorityLabel.setVisible(false);
            image.setVisible(false);
        }
    }

    public void unrenderRobot(IRobot robot) {
        playerLayer.setCell((int) robot.getPos().x, (int) robot.getPos().y, null);
    }

    public void renderRobot(IRobot robot){
        if(robot.isAlive()) {
            updateRobotRotation(robot);
            playerLayer.setCell((int) robot.getPos().x, (int) robot.getPos().y, robotCellHashMap.get(robot));
        }
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i,i1, true);
    }

    @Override
    public void pause() {
        // pause
    }

    @Override
    public void resume() {
        // resume
    }

    @Override
    public void hide() {
        // hide
    }

    @Override
    public void dispose() {
        // dispose
    }

    private void showFinishGamePopUp(String text){
        winOrLoseText.setText(text);
        finishGamePopUp.setVisible(true);
    }

    private void createFinishGamePopUp() {
        Skin skin = new Skin(Gdx.files.classpath("skin/uiskin.json"));
        finishGamePopUp = new Table();
        stage.addActor(finishGamePopUp);

        TextButton backToMenu = new TextButton("Main menu", skin);
        backToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                roboRally.setMenuScreen();
            }
        });

        TextButton newGameAgain = new TextButton("New game with same settings", skin);
        newGameAgain.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                roboRally.setGameScreen();
            }
        });

        winOrLoseText = new Label("You win!", skin);
        winOrLoseText.setFontScale(2);
        winOrLoseText.setAlignment(Align.center);

        finishGamePopUp.add(winOrLoseText).expandX();
        finishGamePopUp.row();

        finishGamePopUp.add(newGameAgain).expandX();
        finishGamePopUp.row();

        finishGamePopUp.add(backToMenu).expandX();

        finishGamePopUp.setSize(450,300);
        finishGamePopUp.setPosition((stage.getWidth()/2) - (finishGamePopUp.getWidth()/2),(stage.getHeight()/2) - (finishGamePopUp.getHeight()/2));
        finishGamePopUp.setBackground( new TextureRegionDrawable(new TextureRegion(new Texture("Maps/Background.png"))).tint(new Color(0.5f,0.5f,0.5f,0.5f)));

        finishGamePopUp.setVisible(false);
    }

    @Override
    public boolean keyUp(int code) {
        player.setShouldNotMove(false);

        if (Input.Keys.LEFT == code) {
            for (IRobot robot : getRobots()) {
                gameLogic.endOfPhaseCheck(robot);
            }
            unrenderRobot(player);
            player.rotateCounterClockwise();
            renderRobot(player);
        } else if (Input.Keys.RIGHT == code) {
            for (IRobot robot : getRobots()) {
                gameLogic.endOfPhaseCheck(robot);
            }
            unrenderRobot(player);
            player.rotateClockwise();
            renderRobot(player);
        } else if (Input.Keys.DOWN == code) {
            for (IRobot robot : getRobots()) {
                gameLogic.endOfPhaseCheck(robot);
            }
            unrenderRobot(player);
            player.move(1, Direction.oppositeOf(player.getDirection()));
            renderRobot(player);
        } else if (Input.Keys.UP == code) {
            for (IRobot robot : getRobots()) {
                gameLogic.endOfPhaseCheck(robot);
            }

            unrenderRobot(player);
            player.move(1, player.getDirection());
            renderRobot(player);
        } else if (Input.Keys.Q == code) {
            roboRally.setMenuScreen();
        } else if (Input.Keys.F == code) {
            roboRally.toggleFullscreen();
        } else if (Input.Keys.SPACE == code) {
            gameLogic.endOfPhaseCheck(player);
        }

        return false;
    }

    public void makeHandInvisible() {
        int lockedCardsNumber = 0;
        if(player.getDamageTaken() >= 5){
            lockedCardsNumber = player.getDamageTaken()-5+1;
        }

        for (int i = handCards.size()-1; i >= lockedCardsNumber; i--) {
            handCards.get(i).setVisible(false);
            handPriorityLabelList.get(i).setVisible(false);
            // remove priority label and remove it from the list
        }
    }

    public ArrayList<IRobot> getRobots(){
        ArrayList<IRobot> robots = new ArrayList<>();

        robots.add(player);
        robots.addAll(aiList);

        return robots;
    }

    public GameLogic getGameLogic(){
        return this.gameLogic;
    }
}