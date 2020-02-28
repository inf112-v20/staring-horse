package inf112.skeleton.app;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GameScreen implements Screen {

    private TiledMap tiledMap;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer holeLayer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private OrthographicCamera camera;

    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerWonCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private StaticTiledMapTile playerTilemap;

    private Stage stage;

    private Player player;
    private ImageButton cardButton;

    private final RoboRally roboRally;


    public GameScreen(RoboRally roboRally){
        this.roboRally = roboRally;

        tiledMap = new TmxMapLoader().load("Maps/backgroundTest.tmx");
        camera = new OrthographicCamera();
        //Gdx.input.setInputProcessor(this);

        player = new Player();
        player.loadAssets();

        playerCell = new TiledMapTileLayer.Cell();
        playerWonCell = new TiledMapTileLayer.Cell();
        playerDiedCell = new TiledMapTileLayer.Cell();

        playerTilemap = new StaticTiledMapTile(player.getAliveTexture());

        playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Player");
        flagLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Flag");
        holeLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Hole");
        boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");

        playerCell.setTile(playerTilemap);
        playerWonCell.setTile(playerTilemap);
        playerDiedCell.setTile(playerTilemap);

        // create a stage for image buttons.
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.player.drawNewDeck();
        // create 9 of the same button.
        for(int i = 0; i < 9; i++) {
            ProgramCard card = player.getProgramCard(i);
            cardButton = card.makeCardImageButton(player, this);
            cardButton.setPosition((float) (30 + 60*i), 55);
            stage.addActor(cardButton);
        }

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, (float) 1/300);
        camera.setToOrtho(false, boardLayer.getWidth(), boardLayer.getHeight());
        camera.update();
        orthogonalTiledMapRenderer.setView(camera);

        renderPlayer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

        checkTile();

        orthogonalTiledMapRenderer.render();

        stage.act();
        stage.draw();
    }

    /**
     * Checks if the player/robot is standing on a special tile
     * e.g. a flag or hole tile. Then changes the players texture and cell
     */
    public void checkTile() {
        // Checks if a player is on a flag, and switches texture.
        if (flagLayer.getCell(player.getXPos(), player.getYPos()) != null) {
            playerLayer.setCell(player.getXPos(), player.getYPos(), playerWonCell);
            playerTilemap.setTextureRegion(player.getWonTexture());
        }

        // Checks if a player is on a hole, and switches texture.
        if (holeLayer.getCell(player.getXPos(), player.getYPos()) != null) {
            playerLayer.setCell(player.getXPos(), player.getYPos(), playerDiedCell);
            playerTilemap.setTextureRegion(player.getDeadTexture());
        }

        // TODO add the rest of the special tiles (conveyor, wall and so on)
        // Potentially move this method to a different class.
        // and should probably only be called when the player has moved.
    }

    public void unrenderPlayer() {
        playerLayer.setCell(player.getXPos(), player.getYPos(), null);
    }

    public void renderPlayer(){
        playerLayer.setCell(player.getXPos(), player.getYPos(), playerCell);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
