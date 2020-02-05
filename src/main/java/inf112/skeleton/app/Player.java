package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player {

    private int x;
    private int y;
    private Direction direction;
    private int healthPoints;

    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerWonCell;
    private TiledMapTileLayer.Cell playerDiedCell;

    private Texture playerTextures;
    private TextureRegion aliveTexture;
    private TextureRegion deadTexture;
    private TextureRegion wonTexture;
    private TextureRegion[][] textureRegion;

    public Player() {
        this.x = 0;
        this.y = 0;
        playerTextures = new Texture("player.png");
        textureRegion = new TextureRegion(playerTextures).split(300,300);
        aliveTexture = new TextureRegion();
        deadTexture = new TextureRegion();
        wonTexture = new TextureRegion();
    }

    public TextureRegion getAliveTexture() {
        return aliveTexture;
    }

    public TextureRegion getDeadTexture() {
        return deadTexture;
    }

    public TextureRegion getWonTexture() {
        return wonTexture;
    }

    public void loadAssets() {
        aliveTexture = textureRegion[0][0];
        deadTexture = textureRegion[0][1];
        wonTexture = textureRegion[0][2];
    }
}
