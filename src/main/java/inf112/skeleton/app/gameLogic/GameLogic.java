package inf112.skeleton.app.gameLogic;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.robot.Player;
import inf112.skeleton.app.robot.IRobot;
import inf112.skeleton.app.screen.GameScreen;

import java.util.ArrayList;

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

    public boolean isRobotOrWallInLaser(IRobot robot) {
        int robotLaserX = robot.getXPos();
        int robotLaserY = robot.getYPos();
        TiledMapTileLayer playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Player");
        TiledMapTileLayer boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");

        if (robot.getDirection() == EAST) {
            robotLaserX++;
            while (boardLayer.getCell(robotLaserX, robotLaserY) == null) {
                if (playerLayer.getCell(robotLaserX, robotLaserY) != null) {
                    IRobot victim = getRobotOnPos(robotLaserX, robotLaserY);
                    victim.takeDamage();
                    return true;
                }
                if (canGo(robot, false)) {
                    robotLaserX++;
                    continue;
                }
                return true;
            }
        }
        else if (robot.getDirection() == WEST) {
            robotLaserX--;
            while (boardLayer.getCell(robotLaserX, robotLaserY) == null) {
                if (playerLayer.getCell(robotLaserX, robotLaserY) != null) {
                    IRobot victim = getRobotOnPos(robotLaserX, robotLaserY);
                    victim.takeDamage();
                    return true;
                }
                if (canGo(robot, false)) {
                    robotLaserX--;
                    continue;
                }
                return true;
            }
        }
        else if (robot.getDirection() == SOUTH) {
            robotLaserY--;
            while (boardLayer.getCell(robotLaserX, robotLaserY) == null) {
                if (playerLayer.getCell(robotLaserX, robotLaserY) != null) {
                    IRobot victim = getRobotOnPos(robotLaserX, robotLaserY);
                    victim.takeDamage();
                    return true;
                }
                if (canGo(robot, false)) {
                    robotLaserY--;
                    continue;
                }
                return true;
            }
        }
        else if (robot.getDirection() == NORTH) {
            robotLaserY++;
            while (boardLayer.getCell(robotLaserX, robotLaserY) == null) {
                if (playerLayer.getCell(robotLaserX, robotLaserY) != null) {
                    IRobot victim = getRobotOnPos(robotLaserX, robotLaserY);
                    victim.takeDamage();
                    return true;
                }
                if (canGo(robot, false)) {
                    robotLaserY--;
                    continue;
                }
                return true;
            }
        }
        return false;
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
    public void changeDirOnGear(IRobot player) {
        String objectName = getObjectNameOnXandY(tiledMap,player.getXPos(), player.getYPos());
        switch (objectName) {
            case "clockwise":
                player.rotateClockwise();
                break;
            case "counterclockwise":
                player.rotateCounterClockwise();
                break;
            default:
                break;
        }
    }

    public ArrayList<Integer> getXandYposofRespawnPoint() {
        TiledMapTileLayer respawnObjects = (TiledMapTileLayer) tiledMap.getLayers().get("SpawnPoint");
        ArrayList<Integer> gamerlist = new ArrayList<>();

        for (int x = 0; x < respawnObjects.getWidth(); x++)
            for (int y = 0; y < respawnObjects.getHeight(); y++)
                if (respawnObjects.getCell(x, y) != null) {
                    gamerlist.add(x);
                    gamerlist.add(y);
                }
        return gamerlist;
    }

    public void conveyorBelt(IRobot robot) {
        String objectName = getObjectNameOnXandY(tiledMap, robot.getXPos(), robot.getYPos());
        switch (objectName) {
            case "SingleEast":
                robot.moveOne(EAST);
                break;
            case "SingleEtoS":
                robot.rotateClockwise();
                robot.moveOne(SOUTH);
                break;
            case "SingleSouth":
                robot.moveOne(SOUTH);
                break;
            case "DoubleNorth":
                robot.move(2,NORTH);
                break;
            case "DoubleNtoE":
                robot.rotateClockwise();
                robot.move(2,EAST);
                break;
            case "DoubleEast":
                robot.move(2,EAST);
                break;
            default:
                break;
        }
    }

    public void endOfPhaseCheck(IRobot robot) {
        if (robot instanceof Player) {
            GameScreen.getInstance().unrenderRobot(robot);
            Player player = (Player) robot;
            if (!player.getIsTestPlayer()) {
                pickUpFlag(player);
                changeDirOnGear(player);
                conveyorBelt(player);
            }
        } else {
            GameScreen.getInstance().unrenderRobot(robot);
            pickUpFlag(robot);
            changeDirOnGear(robot);
            conveyorBelt(robot);
        }
        GameScreen.getInstance().renderRobot(robot);
    }

    public void pushIfPossible(int x, int y, Direction dir){
        Vector2 vector = Direction.getPosInDirection(new Vector2(x, y), dir);
        IRobot otherRobot = getRobotOnPos((int)vector.x, (int)vector.y);
        System.out.println("Player: " + x + ", " + y);

        if(otherRobot != null){
            System.out.println(otherRobot.getXPos() + ", " + otherRobot.getYPos());
            otherRobot.moveOne(dir);
            GameScreen.getInstance().renderRobot(otherRobot);
            System.out.println(otherRobot.getXPos() + ", " + otherRobot.getYPos());
        }
    }

    private IRobot getRobotOnPos(int x, int y){
        for(IRobot robot:GameScreen.getInstance().getRobots()){
            if(robot.getXPos() == x && robot.getYPos() == y){
                return robot;
            }
        }
        return null;
    }

}
