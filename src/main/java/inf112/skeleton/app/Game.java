package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;


public class Game extends InputAdapter implements ApplicationListener {
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

    private Player player;

    public Game(){

    }

    @Override
    public void create() {
        tiledMap = new TmxMapLoader().load("map.tmx");
        camera = new OrthographicCamera();

        Gdx.input.setInputProcessor(this);

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

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, (float) 1/300);
        camera.setToOrtho(false, boardLayer.getWidth(), boardLayer.getHeight());
        camera.update();
        orthogonalTiledMapRenderer.setView(camera);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        playerLayer.setCell(player.getXPos(), player.getYPos(), playerCell);

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

        orthogonalTiledMapRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public boolean keyUp(int code) {

        // Detect when a key is let go and
        // inputs the player texture into a new cell and removes it from last cell
        if (Input.Keys.LEFT == code) {
            playerLayer.setCell(player.getXPos(), player.getYPos(), null);
            player.setXPos(player.getXPos()-1);
            playerLayer.setCell(player.getXPos(), player.getYPos(), playerCell);

        } else if (Input.Keys.RIGHT == code) {
            playerLayer.setCell(player.getXPos(), player.getYPos(), null);
            player.setXPos(player.getXPos()+1);
            playerLayer.setCell(player.getXPos(), player.getYPos(), playerCell);
        } else if (Input.Keys.DOWN == code) {
            playerLayer.setCell(player.getXPos(), player.getYPos(), null);
            player.setYPos(player.getYPos()-1);
            playerLayer.setCell(player.getXPos(), player.getYPos(), playerCell);
        } else if (Input.Keys.UP == code) {
            playerLayer.setCell(player.getXPos(), player.getYPos(), null);
            player.setYPos(player.getYPos()+1);
            playerLayer.setCell(player.getXPos(), player.getYPos(), playerCell);
        }
        return false;
    }
}
