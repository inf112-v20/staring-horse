package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import org.lwjgl.Sys;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


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
    private Vector2 playerVector;
    private StaticTiledMapTile playerTilemap;

    private Texture playerTextures;
    private TextureRegion aliveTexture;
    private TextureRegion deadTexture;
    private TextureRegion wonTexture;

    private int playerX;
    private int playerY;

    private Board board;
    private Robot playerRobot;

    public Game(){
        this.playerRobot = new Robot();
    }

    @Override
    public void create() {
        tiledMap = new TmxMapLoader().load("map.tmx");
        camera = new OrthographicCamera();

        Gdx.input.setInputProcessor(this);

        // Split the player textures.
        playerTextures = new Texture("player.png");
        TextureRegion[][] textureRegion = new TextureRegion(playerTextures).split(300,300);
        aliveTexture = textureRegion[0][0];
        deadTexture = textureRegion[0][1];
        wonTexture = textureRegion[0][2];

        playerCell = new TiledMapTileLayer.Cell();
        playerWonCell = new TiledMapTileLayer.Cell();
        playerDiedCell = new TiledMapTileLayer.Cell();

        playerTilemap = new StaticTiledMapTile(aliveTexture);

        playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Player");
        flagLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Flag");
        holeLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Hole");
        boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");

        playerCell.setTile(playerTilemap);

        playerWonCell.setTile(playerTilemap);

        playerDiedCell.setTile(playerTilemap);

        playerVector = new Vector2();

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1);

        camera.setToOrtho(false, 3000, 3000);
        camera.update();
        orthogonalTiledMapRenderer.setView(camera);

        this.board = new Board(boardLayer.getWidth(), boardLayer.getHeight());
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        playerLayer.setCell(playerX,playerY, playerCell);

        if (flagLayer.getCell(playerX, playerY) != null) {
            playerLayer.setCell(playerX ,playerY, playerWonCell);
            playerTilemap.setTextureRegion(wonTexture);
        }

        if (holeLayer.getCell(playerX, playerY) != null) {
            playerLayer.setCell(playerX, playerY, playerDiedCell);
            playerTilemap.setTextureRegion(deadTexture);
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

        // input and remove player texture from last cell
        if (Input.Keys.LEFT == code) {
            playerLayer.setCell(playerX, playerY, null);
            playerLayer.setCell(--playerX, playerY, playerCell);
        } else if (Input.Keys.RIGHT == code) {
            playerLayer.setCell(playerX, playerY, null);
            playerLayer.setCell(++playerX, playerY, playerCell);
        } else if (Input.Keys.DOWN == code) {
            playerLayer.setCell(playerX, playerY, null);
            playerLayer.setCell(playerX, --playerY, playerCell);
        } else if (Input.Keys.UP == code) {
            playerLayer.setCell(playerX, playerY, null);
            playerLayer.setCell(playerX, ++playerY, playerCell);
        }
        return false;
    }
}
