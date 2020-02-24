package inf112.skeleton.app;

import com.badlogic.gdx.Game;

public class RoboRally extends Game {

    public GameScreen gameScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }
}
