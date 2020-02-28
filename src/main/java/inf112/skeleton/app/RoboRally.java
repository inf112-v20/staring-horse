package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import inf112.skeleton.app.Screen.GameScreen;
import inf112.skeleton.app.Screen.MainMenuScreen;

public class RoboRally extends Game {

    public GameScreen gameScreen;
    public MainMenuScreen menuScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen(this);
        menuScreen = new MainMenuScreen(this);

        setScreen(gameScreen);
    }

    public void render() {
        super.render();
    }
}
