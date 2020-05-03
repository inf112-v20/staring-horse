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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.gameLogic.GameLogic;
import inf112.skeleton.app.gameLogic.GameLoop;
import inf112.skeleton.app.robot.Player;
import inf112.skeleton.app.programCard.ProgramCard;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.robot.AI;
import inf112.skeleton.app.robot.IRobot;

import java.util.ArrayList;
import java.util.HashMap;


public class GameScreen extends InputAdapter implements Screen {

    public static final int TILE_AREA = 300;
    private TiledMap tiledMap;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private OrthographicCamera camera;

    private TiledMapTileLayer.Cell playerCell;

    private Stage stage;

    private Player player;
    private ArrayList<AI> aiList;

    private RoboRally roboRally = RoboRally.getInstance();

    private static GameScreen SINGLE_INSTANCE = null;
    private GameLoop gameLoop;

    private ArrayList<ImageButton> selectableCardButtons;
    private ArrayList<Image> handCards;

    private HashMap<IRobot,TiledMapTileLayer.Cell> robotCellHashMap;

    private SpriteBatch batch;
    private BitmapFont font;
    private Vector2 windowedScreenSize;

    private GameScreen(){}

    public static GameScreen getInstance() {
        if (SINGLE_INSTANCE == null)
            SINGLE_INSTANCE = new GameScreen();

        return SINGLE_INSTANCE;
    }

    @Override
    public void show() {
        tiledMap = new TmxMapLoader().load("Maps/backgroundTest.tmx");
        camera = new OrthographicCamera();

        ArrayList<Vector2> respawnPoints = GameLogic.getInstance().getAllPositionsFromObjectName("SpawnPoint");
        player = new Player();

        playerCell = new TiledMapTileLayer.Cell();

        playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Player");
        boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");

        playerCell.setTile(new StaticTiledMapTile(player.getTexture()));

        robotCellHashMap = new HashMap<>();
        robotCellHashMap.put(player, playerCell);

        aiList = new ArrayList<>();
        AI.resetRobotID();
        int aiNumber = 3;

        for(int i = 0; i < aiNumber; i++){
            AI newAI = new AI();

            TiledMapTileLayer.Cell aiCell = new TiledMapTileLayer.Cell();
            aiCell.setTile(new StaticTiledMapTile(newAI.getTexture()));

            robotCellHashMap.put(newAI, aiCell);
            aiList.add(newAI);
        }

        // create a stage for image buttons.
        stage = new Stage(new FitViewport(900,900, camera));

        // temporary, should be removed when we no longer need movement with the arrow keys
        InputProcessor inputProcessorOne = stage;
        InputProcessor inputProcessorTwo = this;
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, (float) 1/TILE_AREA);
        camera.setToOrtho(false, boardLayer.getWidth(), boardLayer.getHeight());
        camera.update();
        orthogonalTiledMapRenderer.setView(camera);

        makeSelectableCards();
        makePlayerHandImages();

        gameLoop = new GameLoop(player, aiList, this);
        gameLoop.startNewRound();

        setAllRespawnPoints(respawnPoints);

        renderRobot(player);
        for(IRobot ai:aiList){
            renderRobot(ai);
        }

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale((float) 1.2);
        font.setColor(Color.WHITE);

        Image playerIcon = new Image();
        playerIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(player.getTexture())));

        playerIcon.setSize(100, 100);
        playerIcon.setPosition(30, 50);

        stage.addActor(playerIcon);
    }

    private void setAllRespawnPoints(ArrayList<Vector2> respawnPoints) {
        for (int i = 0; i < getRobots().size(); i++) {
            getRobots().get(i).setRespawnPoint(respawnPoints.get(i));
        }
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        drawPlayerInfoText();

        stage.act();
        stage.draw();
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
        font.draw(batch, playerInfoText, 30, 300);
        // Button-pressing Info
        font.draw(batch, "'Q': Main Menu \n" +
                "'F': enter/exit Fullscreen", stage.getWidth()-75, 100);
        batch.end();
    }

    /**
     * Finish game when robot wins
     * @param robot winner
     */
    public void robotWin(IRobot robot){
        System.out.println("THE " + robot.getName() + " HAS WON!");
        RoboRally.getInstance().setMenuScreen();
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
            System.out.println("ALL OTHER ROBOTS DEAD!");
            robotWin(livingRobots.get(0));
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
                imageButton.setVisible(false);
                //System.out.println("Pressed: imagebutton " + i);

                gameScreen.addCardToPlayerHand(player.getProgramCard(i));


                if(player.isHandFull()) {
                    gameLoop.startActivationPhase();
                }

                return true;
            }
        });

        return imageButton;
    }

    private void addCardToPlayerHand(ProgramCard programCard) {
        player.addCardToHand(programCard);

        handCards.get(player.getNumberOfCardsInHand()-1).setVisible(true);
        changeImageTexture(handCards.get(player.getNumberOfCardsInHand()-1), programCard.getTexture());
    }

    public void changeImageButtonDrawable(ImageButton button, ProgramCard card) {
        button.getStyle() .imageUp = new TextureRegionDrawable(new TextureRegion(card.getTexture()));
    }

    public void makePlayerDeckMatchSelectableCards() {
        for(ImageButton card:selectableCardButtons){
            card.setVisible(false);
        }
        for(int i = 0; i < (9-(Math.min(player.getDamageTaken(),4))); i++) {
            selectableCardButtons.get(i).setVisible(true);
            changeImageButtonDrawable(selectableCardButtons.get(i), player.getProgramCard(i));
        }
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

    @Override
    public boolean keyUp(int code) {

        if (Input.Keys.LEFT == code) {
            for (IRobot robot : getRobots()) {
                GameLogic.getInstance().endOfPhaseCheck(robot);
            }
            unrenderRobot(player);
            player.rotateCounterClockwise();
            renderRobot(player);
        } else if (Input.Keys.RIGHT == code) {
            for (IRobot robot : getRobots()) {
                GameLogic.getInstance().endOfPhaseCheck(robot);
            }
            unrenderRobot(player);
            player.rotateClockwise();
            renderRobot(player);
        } else if (Input.Keys.DOWN == code) {
            for (IRobot robot : getRobots()) {
                GameLogic.getInstance().endOfPhaseCheck(robot);
            }
            unrenderRobot(player);
            player.move(1, Direction.oppositeOf(player.getDirection()));
            renderRobot(player);
        } else if (Input.Keys.UP == code) {
            for (IRobot robot : getRobots()) {
                GameLogic.getInstance().endOfPhaseCheck(robot);
            }

            unrenderRobot(player);
            player.move(1, player.getDirection());
            renderRobot(player);
        } else if (Input.Keys.Q == code) {
            roboRally.setMenuScreen();
        } else if (Input.Keys.F == code) {
            boolean fullScreen = Gdx.graphics.isFullscreen();
            if (fullScreen){
                Gdx.graphics.setWindowedMode((int) windowedScreenSize.x, (int) windowedScreenSize.y);
            }
            else {
                windowedScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
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
        }
    }

    public ArrayList<IRobot> getRobots(){
        ArrayList<IRobot> robots = new ArrayList<>();

        robots.add(player);
        robots.addAll(aiList);

        return robots;
    }
}