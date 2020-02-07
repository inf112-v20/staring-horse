package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.sun.scenario.effect.impl.prism.PrImage;

public class Player {

    private int xPos;
    private int yPos;
    private Direction direction;
    private int healthPoints;

    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerWonCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private StaticTiledMapTile tilemap;

    private Texture playerTextures;
    private TextureRegion aliveTexture;
    private TextureRegion deadTexture;
    private TextureRegion wonTexture;
    private TextureRegion[][] textureRegion;

    public Player() {
        this.xPos = 0;
        this.yPos = 0;
        this.playerTextures = new Texture("player.png");
        this.textureRegion = new TextureRegion(playerTextures).split(300,300);
        this.aliveTexture = new TextureRegion();
        this.deadTexture = new TextureRegion();
        this.wonTexture = new TextureRegion();

        this.tilemap = new StaticTiledMapTile(aliveTexture);
    }

    public StaticTiledMapTile getTilemap() {
        return this.tilemap;
    }

    public void setXPos(int x) { this.xPos = x; }

    public void setYPos(int y) { this.yPos = y; }

    public int getYPos() { return yPos; }

    public int getXPos() { return xPos; }


    public TextureRegion getAliveTexture() {
        return this.aliveTexture;
    }

    public TextureRegion getDeadTexture() {
        return this.deadTexture;
    }

    public TextureRegion getWonTexture() {
        return this.wonTexture;
    }

    public void loadAssets() {
        aliveTexture = textureRegion[0][0];
        deadTexture = textureRegion[0][1];
        wonTexture = textureRegion[0][2];
    }
}
