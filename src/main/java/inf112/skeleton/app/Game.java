package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.concurrent.TimeUnit;

public class Game implements ApplicationListener {
    // Game-class contains Board-object and controls rounds and phases

    private Board gameBoard;
    private Robot playerRobot;

    public Game(){
        this.gameBoard = new Board(10, 10);
        this.playerRobot = new Robot();
    }

    private ShapeRenderer gridSquare;
    private ShapeRenderer render;

    int tileWidth;
    int tileHeight;

    @Override
    public void create() {
        gridSquare = new ShapeRenderer();
        render = new ShapeRenderer();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tileWidth = Gdx.graphics.getWidth() / this.gameBoard.getWidth();
        tileHeight = Gdx.graphics.getHeight() / this.gameBoard.getHeight();

        for(int x = 0; x < this.gameBoard.getWidth(); x++){
            for(int y = 0; y < this.gameBoard.getHeight(); y++){
                if(!gameBoard.isFloor(x,y)){
                    renderRectAt(x,y,gameBoard.elementAt(x,y).getTileColor());
                }
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            
        }

        renderRobot();

        System.out.println(playerRobot.getX());

        playerRobot.moveTo(playerRobot.getX() + 1, 8);

        renderGrid();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private void renderGrid(){
        gridSquare.begin(ShapeRenderer.ShapeType.Line);
        gridSquare.setColor(Color.PURPLE);

        for(int x = 0; x <= Gdx.graphics.getWidth(); x += tileWidth){
            for(int y = 0; y <= Gdx.graphics.getHeight(); y += tileHeight) {
                gridSquare.rect(x, y, tileWidth, tileHeight);
            }
        }

        gridSquare.end();
    }

    private void renderRobot(){
        renderRectAt(playerRobot.getX(), playerRobot.getY(), Color.RED);
    }

    /*
    * Renders rectangle at gridpos x,y
     */
    private void renderRectAt(int x, int y, Color color){
        render.begin(ShapeRenderer.ShapeType.Filled);

        render.setColor(color);
        render.rect(x*tileWidth,y*tileHeight, tileWidth, tileHeight);

        render.end();
    }


}
