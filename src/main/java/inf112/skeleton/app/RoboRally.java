package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import inf112.skeleton.app.screen.GameScreen;
import inf112.skeleton.app.screen.MainMenuScreen;

public class RoboRally extends Game {

    public GameScreen gameScreen;
    public MainMenuScreen menuScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen(this);
        menuScreen = new MainMenuScreen(this);

        setScreen(menuScreen);
    }

    public void render() {
        super.render();
    }
}
