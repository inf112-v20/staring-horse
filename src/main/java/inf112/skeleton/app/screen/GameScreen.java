package inf112.skeleton.app.screen;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.ProgramCard;
import inf112.skeleton.app.RoboRally;


public class GameScreen implements Screen {

    private TiledMap tiledMap;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer holeLayer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private OrthographicCamera camera;

    private TiledMapTileLayer.Cell playerCell;
    private StaticTiledMapTile playerTilemap;

    private Stage stage;

    private Player player;
    private ImageButton cardButton;

    private final RoboRally roboRally;


    // TODO Organize constructor. (take things out that dont need to be there/make methods/move to new classes)
    public GameScreen(RoboRally roboRally){
        this.roboRally = roboRally;

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

        playerCell.setTile(playerTilemap);

        // create a stage for image buttons.
        stage = new Stage(new FitViewport(800,800, camera));
        Gdx.input.setInputProcessor(stage);

        this.player.drawNewDeck();
        // make each card in the players deck into an imageButton
        for(int i = 0; i < 9; i++) {
            ProgramCard card = player.getProgramCard(i);
            cardButton = card.makeCardImageButton(player, this);
            cardButton.setPosition((float) ((cardButton.getWidth()*i)+(camera.viewportWidth/2)-cardButton.getWidth()*4.5), (float) (camera.viewportWidth*0.05));
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
        // show
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updatePlayerRotation();

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
            System.out.println("Player on flag");
        }

        // Checks if a player is on a hole, and switches texture.
        if (holeLayer.getCell(player.getXPos(), player.getYPos()) != null) {
            System.out.println("Player died");
        }

        // TODO add the rest of the special tiles (conveyor, wall and so on)
        // Potentially move this method to a different class.
        // and should probably only be called when the player has moved.
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
                    card.getCardButton().setSize(card.getTexture().getWidth() / 6, card.getTexture().getHeight() / 6);
                    card.getCardButton().setPosition((float) ((cardButton.getWidth()*i)+(camera.viewportWidth/2)-cardButton.getWidth()*2.5), (float) (camera.viewportWidth*0.2));
                }
            }
        }
    }



    public void unrenderPlayer() {
        playerLayer.setCell(player.getXPos(), player.getYPos(), null);
    }

    public void renderPlayer(){
        playerLayer.setCell(player.getXPos(), player.getYPos(), playerCell);
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i,i1, true);
        // resize
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


}
