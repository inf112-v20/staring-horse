package inf112.skeleton.app;

import com.badlogic.gdx.Game;

public class RoboRally extends Game {

    public GameScreen gameScreen;
<<<<<<< Updated upstream
=======
    public MainMenuScreen menuScreen;
>>>>>>> Stashed changes

    @Override
    public void create() {
        gameScreen = new GameScreen(this);
<<<<<<< Updated upstream
        setScreen(gameScreen);
    }
=======
        menuScreen = new MainMenuScreen(this);
        setScreen(gameScreen);
    }

    public void render() {
        super.render();
    }
>>>>>>> Stashed changes
}
