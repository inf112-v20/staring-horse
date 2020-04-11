package inf112.skeleton.app.screen;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.app.gamelogic.GameLoop;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.ProgramCard;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.robot.AI;
import inf112.skeleton.app.robot.IRobot;

import java.util.ArrayList;


public class GameScreen extends InputAdapter implements Screen {

    public static final int TILE_AREA = 300;
    private TiledMap tiledMap;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private OrthographicCamera camera;

    private TiledMapTileLayer.Cell playerCell;
    private StaticTiledMapTile playerTilemap;

    private Stage stage;

    private Player player;

    private RoboRally roboRally = RoboRally.getInstance();

    private static GameScreen SINGLE_INSTANCE = null;
    private GameLoop gameLoop;

    private ArrayList<ImageButton> selectableCardButtons;
    private ArrayList<Image> handCards;

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

        player = new Player();

        playerCell = new TiledMapTileLayer.Cell();
        player.loadAssets();

        playerTilemap = new StaticTiledMapTile(player.getTexture());

        playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Player");
        boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");

        playerCell.setTile(playerTilemap);


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

        gameLoop = new GameLoop(player, this);
        gameLoop.startNewRound();

        renderRobot(player);

        // test-AI does nothing
        AI ai = new AI();
        ai.moveForward(1);
        renderRobot(ai);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        if (player.hasWon()) {
            System.out.println("THE PLAYER HAS WON!");
            RoboRally.getInstance().setMenuScreen();
        }

        stage.act();
        stage.draw();
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    /**
     * Update PlayerCell-rotation based on Direction
     */
    public void updatePlayerRotation(){
        switch (player.getDirection()){
            case NORTH:
                playerCell.setRotation(TiledMapTileLayer.Cell.ROTATE_0);
                break;
            case WEST:
                playerCell.setRotation(TiledMapTileLayer.Cell.ROTATE_90);
                break;
            case SOUTH:
                playerCell.setRotation(TiledMapTileLayer.Cell.ROTATE_180);
                break;
            case EAST:
                playerCell.setRotation(TiledMapTileLayer.Cell.ROTATE_270);
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
                System.out.println("Pressed: imagebutton " + i);

                gameScreen.addCardToPlayerHand(player.getProgramCard(i));


                if(player.isHandFull()) {
                    //gameScreen.showPlayersHand();
                    gameLoop.startActivationPhase();
                }

                return true;
            }
        });

        return imageButton;
    }

    private void addCardToPlayerHand(ProgramCard programCard) {
        player.addCardToHand(programCard);

        handCards.get(player.getNumCardsInHand()-1).setVisible(true);
        changeImageTexture(handCards.get(player.getNumCardsInHand()-1), programCard.getTexture());
    }

    public void changeImageButtonDrawable(ImageButton button, ProgramCard card) {
        button.getStyle() .imageUp = new TextureRegionDrawable(new TextureRegion(card.getTexture()));
    }

    public void makePlayerDeckMatchSelectableCards() {
        for(int i = 0; i < 9; i++) {
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
            cardButton.setPosition((((cardButton.getWidth() + 10)*i)+(camera.viewportWidth+150)), (float) (30));
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
            image.setSize(card.getTexture().getWidth() / cardSizeDivisor, card.getTexture().getHeight() / cardSizeDivisor);
            image.setPosition(300 + (i*(card.getTexture().getWidth() / cardSizeDivisor)), 150);

            handCards.add(image);
            stage.addActor(image);

            image.setVisible(false);
        }
    }

    public void unrenderRobot(IRobot robot) {
        playerLayer.setCell(robot.getXPos(), robot.getYPos(), null);
    }

    public void renderRobot(IRobot robot){
        updatePlayerRotation();
        playerLayer.setCell(robot.getXPos(), robot.getYPos(), playerCell);
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
            unrenderRobot(player);
            player.rotateCounterClockwise();
            renderRobot(player);
        } else if (Input.Keys.RIGHT == code) {
            unrenderRobot(player);
            player.rotateClockwise();
            renderRobot(player);
        } else if (Input.Keys.DOWN == code) {
            unrenderRobot(player);
            player.moveBackward(1);
            renderRobot(player);
        } else if (Input.Keys.UP == code) {
            unrenderRobot(player);
            player.moveForward(1);
            renderRobot(player);
        } else if (Input.Keys.Q == code) {
            roboRally.setMenuScreen();
        }

        return false;
    }

    public void makeHandInvisible() {
        for (Image image:handCards) {
            image.setVisible(false);
        }
    }
}