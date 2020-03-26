package inf112.skeleton.app.screen;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.ProgramCard;
import inf112.skeleton.app.RoboRally;


public class GameScreen extends InputAdapter implements Screen {

    public static final int TILE_AREA = 300;
    private TiledMap tiledMap;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer holeLayer;

    private MapObjects flagObjects;
    private MapObjects wallObjects;
    private MapObjects laserObjects;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private OrthographicCamera camera;

    private TiledMapTileLayer.Cell playerCell;
    private StaticTiledMapTile playerTilemap;

    private Stage stage;

    private Player player;
    private ImageButton cardButton;

    private RoboRally roboRally = RoboRally.getInstance();

    private static GameScreen SINGLE_INSTANCE = null;

    private GameScreen(){
        show();
    }

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
        flagLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Flag");
        holeLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Hole");
        boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");

        flagObjects = tiledMap.getLayers().get("flagObjects").getObjects();
        wallObjects = tiledMap.getLayers().get("wallObjects").getObjects();
        laserObjects = tiledMap.getLayers().get("laserObjects").getObjects();


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


        this.player.drawNewDeck();
        // make each card in the players deck into an imageButton
        for(int i = 0; i < 9; i++) {
            ProgramCard card = player.getProgramCard(i);
            cardButton = card.makeCardImageButton(player, this);
            cardButton.setPosition((float) ((cardButton.getWidth()*i)+(camera.viewportWidth/2)-cardButton.getWidth()*4.5), (float) (camera.viewportWidth*0.05));
            stage.addActor(cardButton);
        }

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, (float) 1/TILE_AREA);
        camera.setToOrtho(false, boardLayer.getWidth(), boardLayer.getHeight());
        camera.update();
        orthogonalTiledMapRenderer.setView(camera);

        renderPlayer();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        stage.act();
        stage.draw();
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

    public String getCurrentLayer(TiledMap tiledMap, Player player, String layer) {
        for (MapObject obj : tiledMap.getLayers().get(layer).getObjects()) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            int layer_x = (int) rect.x / GameScreen.TILE_AREA;
            int layer_y = (int) rect.y / GameScreen.TILE_AREA;

            if (player.getXPos() == layer_x && player.getYPos() == layer_y) {
                return obj.getName();
            }
        }
        return "";
    }

    /**
     * renders each card which is in the player hand
     */
    public void showPlayersHand() {
        ProgramCard[] hand = player.getHand();

        // checks if there are any cards in the hand before rendering any cards
        if (hand[0] != null) {
            for (int i = 0; i < 5; i++) {
                // Currently uses the cards presented at the start of each "round"
                if (hand[i] != null) {

                    ProgramCard card = hand[i];
                    int cardSizeDivisor = 6;
                    card.getCardButton().setSize(card.getTexture().getWidth() / cardSizeDivisor, card.getTexture().getHeight() / cardSizeDivisor);
                    card.getCardButton().setPosition((float) ((cardButton.getWidth()*i)+(camera.viewportWidth/2)-cardButton.getWidth()*2.5), (float) (camera.viewportWidth*0.2));
                }
            }
        }
    }

    public void unrenderPlayer() {
        playerLayer.setCell(player.getXPos(), player.getYPos(), null);
    }

    public void renderPlayer(){
        updatePlayerRotation();
        playerLayer.setCell(player.getXPos(), player.getYPos(), playerCell);
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
            unrenderPlayer();
            player.rotateCounterClockwise();
            renderPlayer();
        } else if (Input.Keys.RIGHT == code) {
            unrenderPlayer();
            player.rotateClockwise();
            renderPlayer();
        } else if (Input.Keys.DOWN == code) {
            unrenderPlayer();
            player.moveBackward(1);
            renderPlayer();
        } else if (Input.Keys.UP == code) {
            unrenderPlayer();
            player.moveForward(1);
            renderPlayer();
        } else if (Input.Keys.Q == code) {
            roboRally.setMenuScreen();
        }

        return false;
    }
}
