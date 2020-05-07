package inf112.skeleton.app.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.RoboRally;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RulesScreen extends InputAdapter implements Screen {
    private Skin skin;
    private RoboRally roboRally;
    private Stage stage;
    private ScrollPane scrollPane;

    public RulesScreen(){
        this.roboRally = RoboRally.getInstance();

        this.skin = new Skin(Gdx.files.classpath("skin/star-soldier-ui.json"));

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        createInputMultiplexer();

        makeRulesText();
        makeMenuButton();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i,i1, true);
        scrollPane.setPosition(200,200);
        scrollPane.setSize(stage.getWidth()-400,stage.getHeight()-400);
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

    private void makeRulesText(){
        StringBuilder text = new StringBuilder();

        File file = new File("assets/rules.txt");
        Scanner sc = null;

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Rules file not found!");
            e.printStackTrace();
        }

        while (sc != null && sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            text.append("\n");

            text.append(nextLine);
        }

        BitmapFont font = new BitmapFont();
        font.getData().markupEnabled = true;
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);

        Label rulesText = new Label(text.toString(), style);
        rulesText.setFontScale(1.5f);

        scrollPane = new ScrollPane(rulesText, skin);

        scrollPane.setPosition(200,200);
        scrollPane.setSize(stage.getWidth()-400,stage.getHeight()-400);
        scrollPane.setScrollbarsVisible(true);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);

        rulesText.setWrap(true);
        rulesText.setAlignment(Align.left);

        stage.addActor(scrollPane);
    }

    private void makeMenuButton(){
        TextButton backToMenu = new TextButton("Main menu", skin);
        stage.addActor(backToMenu);

        backToMenu.setPosition(100,100);

        backToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                roboRally.setMenuScreen();
            }
        });
    }

    private void createInputMultiplexer() {
        // creates input processors for both menu-buttons with mouse and keyboard-presses
        InputProcessor inputProcessorOne = stage;
        InputProcessor inputProcessorTwo = this;
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public boolean keyUp(int code) {
        float flingTime = 1;
        float flingVelocity = 300;
        if (Input.Keys.DOWN == code) {
            scrollPane.fling(flingTime,0,-flingVelocity);
        } else if (Input.Keys.UP == code) {
            scrollPane.fling(flingTime,0,flingVelocity);
        } else if (Input.Keys.F == code) {
            roboRally.toggleFullscreen();
        }else if (Input.Keys.Q == code) {
            roboRally.setMenuScreen();
        }

        return false;
    }
}
