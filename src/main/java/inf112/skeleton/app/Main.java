package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.*;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo Rally";

        // TODO change screen size for each map.
        cfg.width = 800;
        cfg.height = 800;

        cfg.x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth())/2 - cfg.width/2;
        cfg.y = 0;

        new LwjglApplication(new RoboRally(), cfg);
    }
}