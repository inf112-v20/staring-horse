package inf112.skeleton.app.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.RoboRally;

import java.util.ArrayList;

public class MainMenuScreen implements Screen {
    private RoboRally roboRally;
    private Stage stage;

    private ArrayList<String> mapList;
    private int currentMapNumber;
    private int aiNumber;
    private int maxAiNumber;
    private Skin skin;
    private Table table;
    private Image mapPreview;

    public MainMenuScreen(){
        this.roboRally = RoboRally.getInstance();
        this.maxAiNumber = 5;
        this.aiNumber = maxAiNumber;

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.classpath("skin/star-soldier-ui.json"));

        this.mapList = new ArrayList<>();
        this.mapList.add("High_Octane.tmx");
        this.mapList.add("backgroundTest.tmx");
        this.mapList.add("Chop_Shop.tmx");
        this.mapList.add("Eazy_Breezy.tmx");
        this.mapList.add("Conveyor_Chaos.tmx");
        this.mapList.add("Laser_Trap.tmx");
    }

    @Override
    public void show() {
        table = new Table();
        table.setDebug(true);
        table.setHeight(stage.getHeight()-200);
        table.setPosition( stage.getWidth() / 2f - table.getWidth() / 2f, stage.getHeight() / 2f - table.getHeight() / 2f );
        stage.addActor(table);

        // makes settings same as before when going back to main menu
        if(roboRally.getGameMap() != null){
            currentMapNumber = mapList.indexOf(roboRally.getGameMap());
            aiNumber = roboRally.getAiNumber();
        }

        makeMapSelector();
        makeAiNumberSelector();
        makeNewGameButton();
        makeRulesButton();
        makeExitButton();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.setPosition( stage.getWidth() / 2f - table.getWidth() / 2f, stage.getHeight() / 2f - table.getHeight() / 2f );
        table.setHeight(stage.getHeight()-200);
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

    private void setMapPreviewToCurrentMap(){
        Texture texture;

        try{
             texture = new Texture("MapImages/" + mapList.get(currentMapNumber).substring(0,mapList.get(currentMapNumber).length()-3) + "png");
        } catch (GdxRuntimeException e){
            System.out.println(mapList.get(currentMapNumber) + " does not have a MapPreview image");
            // set mapPreview to first mapImage if not
            String placeHolderPreview = mapList.get(0);
            texture = new Texture("MapImages/" + placeHolderPreview.substring(0, placeHolderPreview.length()-3) + "png");
        }

        mapPreview.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    private void makeMapSelector(){
        mapPreview = new Image();
        table.add(mapPreview).colspan(4).fillX();

        table.row().pad(5, 0, 5, 0);

        final Label mapName = new Label(mapList.get(currentMapNumber), skin);
        mapName.setAlignment(Align.center);
        table.add(mapName).fillX().uniformX().colspan(4).minHeight(50);

        setMapPreviewToCurrentMap();

        TextButton mapSelectionPrev = new TextButton("Prev map", skin);
        TextButton mapSelectionNext = new TextButton("Next map", skin);

        table.row().pad(5, 0, 5, 0);
        table.add(mapSelectionPrev).uniformX().fillX().colspan(2);
        table.add(mapSelectionNext).uniformX().fillX().colspan(2);

        mapSelectionNext.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(currentMapNumber >= mapList.size()-1){
                    currentMapNumber = 0;
                } else {
                    currentMapNumber++;
                }
                mapName.setText(mapList.get(currentMapNumber));
                setMapPreviewToCurrentMap();
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
                mapName.setText(mapList.get(currentMapNumber));
                setMapPreviewToCurrentMap();
            }
        });
    }

    private void makeAiNumberSelector(){
        final Label numberOfAILabel = new Label("Number of AI: \n" + aiNumber, skin);
        numberOfAILabel.setAlignment(Align.center);

        TextButton moreAI = new TextButton("+", skin);
        TextButton fewerAI = new TextButton("-", skin);

        table.row().pad(5, 0, 5, 0);
        table.add(fewerAI).fillX().uniformX();
        table.add(numberOfAILabel).fillX().minWidth(250).colspan(2).fillY();
        table.add(moreAI).fillX().uniformX();

        fewerAI.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(aiNumber <= 0){
                    aiNumber = maxAiNumber;
                } else {
                    aiNumber--;
                }
                numberOfAILabel.setText("Number of AI: \n" + aiNumber);
            }
        });

        moreAI.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(aiNumber >= maxAiNumber){
                    aiNumber = 0;
                } else {
                    aiNumber++;
                }
                numberOfAILabel.setText("Number of AI: \n" + aiNumber);
            }
        });
    }

    private void makeNewGameButton(){
        TextButton newGame = new TextButton("New Game", skin);

        table.row().pad(50, 0, 5, 0);
        table.add(newGame).fillX().uniformX().colspan(4);

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                roboRally.setGameMap(mapList.get(currentMapNumber));
                roboRally.setAiNumber(aiNumber);
                roboRally.setGameScreen();
            }
        });
    }

    private void makeRulesButton(){
        TextButton rules = new TextButton("Rules", skin);

        table.row().pad(5, 0, 5, 0);
        table.add(rules).fillX().uniformX().colspan(4);

        rules.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                roboRally.setRulesScreen();
            }
        });
    }

    private void makeExitButton(){
        TextButton exit = new TextButton("Exit", skin);

        table.row().pad(5, 0, 0, 0);
        table.add(exit).fillX().uniformX().colspan(4);

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

}