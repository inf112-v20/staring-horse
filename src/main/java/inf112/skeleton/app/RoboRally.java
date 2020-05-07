package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.screen.GameScreen;
import inf112.skeleton.app.screen.MainMenuScreen;
import inf112.skeleton.app.screen.RulesScreen;

public class RoboRally extends Game {
    private static RoboRally SINGLE_INSTANCE = null;

    private String map;
    private int aiNumber;

    private Vector2 windowedScreenSize;

    private RoboRally() {}

    public static RoboRally getInstance() {
        if (SINGLE_INSTANCE == null)
            SINGLE_INSTANCE = new RoboRally();

        return SINGLE_INSTANCE;
    }

    @Override
    public void create() {
        setMenuScreen();
    }

    public void setMenuScreen() {
        setScreen(new MainMenuScreen());
    }

    public void setGameScreen(){
        setScreen(GameScreen.getInstance());
    }

    public void setRulesScreen() {
        setScreen(new RulesScreen());
    }

    public void render() {
        super.render();
    }

    public void setGameMap(String map){
        this.map = map;
    }

    public String getGameMap(){
        return this.map;
    }

    public int getAiNumber() {
        return aiNumber;
    }

    public void setAiNumber(int aiNumber) {
        this.aiNumber = aiNumber;
    }

    public void toggleFullscreen(){
        boolean fullScreen = Gdx.graphics.isFullscreen();
        if (fullScreen){
            Gdx.graphics.setWindowedMode((int) windowedScreenSize.x, (int) windowedScreenSize.y);
        }
        else {
            windowedScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
    }
}
