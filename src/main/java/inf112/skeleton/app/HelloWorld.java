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


public class HelloWorld extends InputAdapter implements ApplicationListener {
    private TiledMap tiledMap;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer holeLayer;
    private TmxMapLoader tmxLoader;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private OrthographicCamera camera;

    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerWonCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private Vector2 playerVector;
    private StaticTiledMapTile playerTilemap;

    private Texture playerTextures;
    private TextureRegion hmm;

    private int playerX;
    private int playerY;

    @Override
    public void create() {
        tiledMap = new TmxMapLoader().load("robotest.tmx");
        camera = new OrthographicCamera();

        Gdx.input.setInputProcessor(this);

        playerTextures = new Texture("player.png");
        TextureRegion textureRegion = new TextureRegion(playerTextures);
        TextureRegion[][] cool = textureRegion.split(300,300);
        hmm = cool[0][0];
        playerCell = new TiledMapTileLayer.Cell();
        playerWonCell = new TiledMapTileLayer.Cell();
        playerDiedCell = new TiledMapTileLayer.Cell();

        playerTilemap = new StaticTiledMapTile(hmm);

        playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Player");

        playerCell.setTile(playerTilemap);

        playerWonCell.setTile(playerTilemap);

        playerDiedCell.setTile(playerTilemap);

        playerVector = new Vector2();


        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1);

        boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");
        camera.setToOrtho(false, 1500, 1500);
        camera.update();
        orthogonalTiledMapRenderer.setView(camera);

    }

    @Override
    public void dispose() {
        playerTextures.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        playerLayer.setCell(playerX,playerY, playerCell);

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
