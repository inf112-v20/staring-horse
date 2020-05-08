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

    private String difficultyAI;

    private boolean debugMode;

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

    /**
     * Generate a MenuScreen
     */
    public void setMenuScreen() { setScreen(new MainMenuScreen()); }

    /**
     * Generate a GameScreen
     */
    public void setGameScreen(){
        setScreen(GameScreen.getInstance());
    }

    /**
     * Generate a RulesScreen
     */
    public void setRulesScreen() {
        setScreen(new RulesScreen());
    }

    public void render() {
        super.render();
    }

    /**
     * Set mapname
     * @param map the mapname
     */
    public void setGameMap(String map){
        this.map = map;
    }

    /**
     * Get gamemap mapname
     * @return mapname
     */
    public String getGameMap(){
        return this.map;
    }

    /**
     * Get the number of AIs
     * @return number of AI
     */
    public int getAiNumber() {
        return aiNumber;
    }

    /**
     * Set the number of AI
     * @param aiNumber number of AI
     */
    public void setAiNumber(int aiNumber) {
        this.aiNumber = aiNumber;
    }

    /**
     * Get difficulty of AI
     * @return string with difficulty level
     */
    public String getDifficultyAI() {
        return difficultyAI;
    }

    /**
     * Set the difficulty of AI
     * @param difficultyAI the difficulty
     */
    public void setDifficultyAI(String difficultyAI) {
        this.difficultyAI = difficultyAI;
    }

    /**
     * Check if currently is in debug mode
     * @return true if you are in debug mode
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Set boolean of debug mode
     * @param debugMode
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Toggle fullscreen
     */
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
