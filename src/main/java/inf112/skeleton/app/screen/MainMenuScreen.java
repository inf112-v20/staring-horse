package inf112.skeleton.app.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.RoboRally;

import java.util.ArrayList;

public class MainMenuScreen implements Screen {
    private RoboRally roboRally;
    private Stage stage;

    private ArrayList<String> mapList;
    private int currentMapNumber;

    private SpriteBatch batch;
    private BitmapFont font;

    public MainMenuScreen(){
        this.roboRally = RoboRally.getInstance();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.mapList = new ArrayList<>();
        this.mapList.add("High_Octane.tmx");
        this.mapList.add("backgroundTest.tmx");
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale((float) 1.2);
        font.setColor(Color.WHITE);

        Skin skin = new Skin(Gdx.files.classpath("skin/star-soldier-ui.json"));

        TextButton newGame = new TextButton("New Game", skin);
        TextButton mapSelectionPrev = new TextButton("Prev map", skin);
        TextButton mapSelectionNext = new TextButton("Next map", skin);
        TextButton exit = new TextButton("Exit", skin);

        table.add(mapSelectionPrev, mapSelectionNext);

        table.row().pad(50, 0, 10, 0);

        table.add(newGame).fillX().uniformX().colspan(2);

        table.row().pad(10, 0, 10, 0);

        table.add(exit).fillX().uniformX().colspan(2);

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        mapSelectionNext.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(currentMapNumber >= mapList.size()-1){
                    currentMapNumber = 0;
                } else {
                    currentMapNumber++;
                }
            }
        });
        mapSelectionPrev.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(currentMapNumber <= 0){
                    currentMapNumber = mapList.size()-1;
                } else {
                    currentMapNumber--;
                }
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                roboRally.setGameMap(mapList.get(currentMapNumber));
                roboRally.setGameScreen();
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, mapList.get(currentMapNumber), stage.getWidth()/2-75, stage.getHeight()-300);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}