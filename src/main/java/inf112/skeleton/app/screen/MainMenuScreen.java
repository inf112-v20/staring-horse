package inf112.skeleton.app.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.RoboRally;

import java.util.ArrayList;

public class MainMenuScreen extends InputAdapter implements Screen {
    private RoboRally roboRally;
    private Stage stage;

    private ArrayList<String> mapList;
    private int currentMapNumber;
    private int aiNumber;
    private int maxAiNumber;
    private String difficultyAI;

    private Skin skin;
    private Table table;
    private Image mapPreview;
    private CheckBox debugModeButton;

    public MainMenuScreen(){
        this.roboRally = RoboRally.getInstance();
        this.maxAiNumber = 5;
        this.aiNumber = maxAiNumber;

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.classpath("skin/star-soldier-ui.json"));

        this.mapList = new ArrayList<>();
        this.mapList.add("High_Octane.tmx");
        this.mapList.add("Chop_Shop.tmx");
        this.mapList.add("Eazy_Breezy.tmx");
        this.mapList.add("Conveyor_Chaos.tmx");
        this.mapList.add("Laser_Trap.tmx");
        this.mapList.add("Test_Map.tmx");
    }

    @Override
    public void show() {
        table = new Table();
        //table.setDebug(true);
        table.setHeight(stage.getHeight()-100);
        table.setWidth(600);
        table.setPosition( stage.getWidth() / 2f - table.getWidth() / 2f, stage.getHeight() / 2f - table.getHeight() / 2f );
        stage.addActor(table);
        table.setBackground( new TextureRegionDrawable(new TextureRegion(new Texture("Maps/Background.png"))));

        // makes settings same as before when going back to main menu
        if(roboRally.getGameMap() != null){
            currentMapNumber = mapList.indexOf(roboRally.getGameMap());
            aiNumber = roboRally.getAiNumber();
        }

        createInputMultiplexer();

        makeMapSelector();
        makeAiNumberSelector();
        makeAiDifficultySelector();
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
        table.setHeight(stage.getHeight()-100);
        table.setPosition( stage.getWidth() / 2f - table.getWidth() / 2f, stage.getHeight() / 2f - table.getHeight() / 2f );
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
        stage.dispose();
    }

    /**
     * Set MapPreview-image to fit current map
     */
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

    /**
     * Make selector of map with next map-button, previous map-button, map preview and label with current map-name
     */
    private void makeMapSelector(){
        mapPreview = new Image();
        table.add(mapPreview).colspan(4).fillX();

        table.row();

        final Label mapName = new Label(mapList.get(currentMapNumber), skin);
        mapName.setAlignment(Align.center);
        table.add(mapName).fillX().uniformX().colspan(4).minHeight(50);

        setMapPreviewToCurrentMap();

        TextButton mapSelectionPrev = new TextButton("Prev map", skin);
        TextButton mapSelectionNext = new TextButton("Next map", skin);

        table.row().pad(0, 0, 5, 0);
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

    /**
     * Make selector of number of AIs with more AI-button, less AI-button and label with current number of AIs
     */
    private void makeAiNumberSelector(){
        final Label numberOfAILabel = new Label("Number of AI: \n" + aiNumber, skin);
        numberOfAILabel.setAlignment(Align.center);

        TextButton moreAI = new TextButton("+", skin);
        TextButton fewerAI = new TextButton("-", skin);

        table.row().pad(5, 0, 0, 0);
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

    /**
     * Make selector for difficulty of AI with checkboxes
     * (Also add Debug-checkbox)
     */
    private void makeAiDifficultySelector() {
        final CheckBox easyButton = new CheckBox("EASY", skin);
        final CheckBox mediumButton = new CheckBox("MEDIUM", skin);
        final CheckBox hardButton = new CheckBox("HARD", skin);

        debugModeButton = new CheckBox("DEBUG", skin);

        table.row().pad(0, 0, 5, 0).height(50);
        table.add(easyButton).colspan(1).uniformX().fillX();
        table.add(mediumButton).colspan(1).uniformX().fillX();
        table.add(hardButton).colspan(1).uniformX().fillX();

        table.add(debugModeButton).colspan(1).uniformX().fillX();

        easyButton.setChecked(true);
        difficultyAI = "EASY";

        easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mediumButton.setChecked(false);
                hardButton.setChecked(false);

                difficultyAI = "EASY";
            }
        });

        mediumButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                easyButton.setChecked(false);
                hardButton.setChecked(false);

                difficultyAI = "MEDIUM";
            }

        });

        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                easyButton.setChecked(false);
                mediumButton.setChecked(false);

                difficultyAI = "HARD";
            }
        });
    }

    /**
     * Make button to start new game with the chosen settings
     */
    private void makeNewGameButton(){
        TextButton newGame = new TextButton("New Game", skin);

        table.row().pad(50, 0, 5, 0);
        table.add(newGame).fillX().uniformX().colspan(4);

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Starting " + mapList.get(currentMapNumber) + " with " + aiNumber + " " + difficultyAI + " AI.");
                roboRally.setGameMap(mapList.get(currentMapNumber));
                roboRally.setAiNumber(aiNumber);
                roboRally.setDifficultyAI(difficultyAI);
                roboRally.setDebugMode(debugModeButton.isChecked());
                roboRally.setGameScreen();
            }
        });
    }

    /**
     * Make button for going to RulesScreen
     */
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

    /**
     * Make button for exiting and closing game
     */
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

    /**
     * Create input processors for both clicking on buttons and keyboard
     */
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
        if (Input.Keys.F == code) {
            roboRally.toggleFullscreen();
        }

        return false;
    }
}