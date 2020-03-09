package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import inf112.skeleton.app.screen.GameScreen;
import inf112.skeleton.app.screen.MainMenuScreen;

public class RoboRally extends Game {
    private static RoboRally SINGLE_INSTANCE = null;

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

    public void render() {
        super.render();
    }
}
