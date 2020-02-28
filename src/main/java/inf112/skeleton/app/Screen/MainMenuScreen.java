package inf112.skeleton.app.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import inf112.skeleton.app.RoboRally;

public class MainMenuScreen implements Screen {
    private final RoboRally roboRally;
    OrthographicCamera camera;

    public MainMenuScreen(RoboRally roboRally) {
        this.roboRally = roboRally;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {
    // show
    }

    @Override
    public void render(float v) {
    // render
    }

    @Override
    public void resize(int i, int i1) {
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
