package inf112.skeleton.app.gameLogic;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.robot.Player;
import inf112.skeleton.app.robot.IRobot;
import inf112.skeleton.app.screen.GameScreen;

import java.util.Objects;

import static inf112.skeleton.app.enums.Direction.*;

public class GameLogic {

    private TiledMap tiledMap;

    private static GameLogic SINGLE_INSTANCE = null;

    public GameLogic() {
        tiledMap = GameScreen.getInstance().getTiledMap();
    }

    public static GameLogic getInstance() {
        if (SINGLE_INSTANCE == null)
            SINGLE_INSTANCE = new GameLogic();

        return SINGLE_INSTANCE;
    }

    public String getObjectNameOnXandY(TiledMap tiledMap, int x, int y) {
        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject obj : tiledMap.getLayers().get(layer.getName()).getObjects()) {
                Rectangle rect = ((RectangleMapObject) obj).getRectangle();
                int layer_x = (int) rect.x / GameScreen.TILE_AREA;
                int layer_y = (int) rect.y / GameScreen.TILE_AREA;

                if (x == layer_x && y == layer_y) {
                    return obj.getName();
                }
            }
        }
        return "";
    }

    /**
     * @param x - x-position on screen
     * @param y - y-position on screen
     * @return true if position is a hole or is off the map
     */
    public boolean isHole(int x, int y) {
        TiledMapTileLayer holeLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Hole");
        TiledMapTileLayer boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");
        return holeLayer.getCell(x,y) != null || boardLayer.getCell(x,y) == null;
    }

    public boolean canGo(IRobot robot, boolean backwards) {
        String objectName = getObjectNameOnXandY(tiledMap, robot.getXPos(), robot.getYPos());
        int step = 1;
        if (canGoCurrentTile(robot, objectName, backwards)) {
            switch (robot.getDirection()) {
                case SOUTH:
                    if (backwards) {
                        step = -1;
                        objectName = getObjectNameOnXandY(tiledMap, robot.getXPos(), robot.getYPos() - step);
                        if (objectName.equals("SouthWall")) {
                            return false;
                        }
                    }
                    else {
                        objectName = getObjectNameOnXandY(tiledMap, robot.getXPos(), robot.getYPos() - step);
                        if (objectName.equals("NorthWall")) {
                            return false;
                        }
                    }
                    break;
                case WEST:
                    if (backwards) {
                        step = -1;
                        objectName = getObjectNameOnXandY(tiledMap, robot.getXPos() - step, robot.getYPos());
                        if (objectName.equals("WestWall")) {
                            return false;
                        }
                    }
                    else {
                        objectName = getObjectNameOnXandY(tiledMap, robot.getXPos() - step, robot.getYPos());
                        if (objectName.equals("EastWall")) {
                            return false;
                        }
                    }
                    break;
                case EAST:
                    if (backwards) {
                        step = -1;
                        objectName = getObjectNameOnXandY(tiledMap, robot.getXPos() + step, robot.getYPos());
                        if (objectName.equals("EastWall")) {
                            return false;
                        }
                    }
                    else {
                        objectName = getObjectNameOnXandY(tiledMap, robot.getXPos() + step, robot.getYPos());
                        if (objectName.equals("WestWall")) {
                            return false;
                        }
                    }
                    break;
                case NORTH:
                    if (backwards) {
                        step = -1;
                        objectName = getObjectNameOnXandY(tiledMap, robot.getXPos(), robot.getYPos() + step);
                        if (objectName.equals("NorthWall")) {
                            return false;
                        }
                    }
                    else {
                        objectName = getObjectNameOnXandY(tiledMap, robot.getXPos(), robot.getYPos() + step);
                        if (objectName.equals("SouthWall")) {
                            return false;
                        }
                    }
                    break;
            }
        }
        else {
            return false;
        }
        return true;
    }

    public boolean canGoCurrentTile(IRobot robot, String currentObjectName, boolean backwards) {
        Direction wallDirection;
        switch (currentObjectName) {
            case "SouthWall":
                if (backwards) {
                    wallDirection = Direction.NORTH;
                }
                else {
                    wallDirection = Direction.SOUTH;
                }
                if (robot.getDirection() == wallDirection) {
                    return false;
                }
                break;
            case "WestWall":
                if (backwards) {
                    wallDirection = EAST;
                }
                else {
                    wallDirection = Direction.WEST;
                }
                if (robot.getDirection() == wallDirection) {
                    return false;
                }
                break;
            case "EastWall":
                if (backwards) {
                    wallDirection = Direction.WEST;
                }
                else {
                    wallDirection = EAST;
                }
                if (robot.getDirection() == wallDirection) {
                    return false;
                }
                break;
            case "NorthWall":
                if (backwards) {
                    wallDirection = Direction.SOUTH;
                }
                else {
                    wallDirection = Direction.NORTH;
                }
                System.out.println(wallDirection);
                if (robot.getDirection() == wallDirection) {
                    return false;
                }
                break;
        }
        return true;
    }


    /**
     * Calls robot.addFlag("the current flag the robot is standing on")
     * This method does not add the flag to the robots list of flags, but
     * identifies the current flag the robot is standing on and calling addFlag.
     * @param robot robot
     */

    public void pickUpFlag(IRobot robot) {
        String objectname = getObjectNameOnXandY(tiledMap, robot.getXPos(), robot.getYPos());
        switch(objectname) {
            case "flag1":
                robot.addFlag("flag1");
                break;
            case "flag2":
                robot.addFlag("flag2");
                break;
            case "flag3":
                robot.addFlag("flag3");
                break;
            case "flag4":
                robot.addFlag("flag4");
                break;
            default:
                break;
        }
    }

    /**
     * Changes the robots direction when on a gear.
     * @param player robot
     */
    // TODO Add to execute programcard action in pla
    public void changeDirOnGear(Player player) {
        String objectName = getObjectNameOnXandY(tiledMap,player.getXPos(), player.getYPos());
        switch (player.getDirection()) {
            case NORTH:
                if (Objects.equals(objectName, "clockwise")) player.setDirection(EAST);
                if (Objects.equals(objectName, "counterclockwise")) player.setDirection(WEST);
                break;
            case EAST:
                if (Objects.equals(objectName, "clockwise")) player.setDirection(SOUTH);
                if (Objects.equals(objectName, "counterclockwise")) player.setDirection(NORTH);
                break;
            case SOUTH:
                if (Objects.equals(objectName, "clockwise")) player.setDirection(WEST);
                if (Objects.equals(objectName, "counterclockwise")) player.setDirection(EAST);
                break;
            case WEST:
                if (Objects.equals(objectName, "clockwise")) player.setDirection(NORTH);
                if (Objects.equals(objectName, "counterclockwise")) player.setDirection(SOUTH);
                break;
            default:
                break;
        }
    }

}
