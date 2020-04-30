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
    ArrayList<String> flags;

    private static GameLogic SINGLE_INSTANCE = null;

    public GameLogic() {
        tiledMap = GameScreen.getInstance().getTiledMap();
    }

    public static GameLogic getInstance() {
        if (SINGLE_INSTANCE == null)
            SINGLE_INSTANCE = new GameLogic();

        return SINGLE_INSTANCE;
    }

    public String getObjectNameOnXandY(TiledMap tiledMap, Vector2 pos) {
        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject obj : tiledMap.getLayers().get(layer.getName()).getObjects()) {
                Rectangle rect = ((RectangleMapObject) obj).getRectangle();
                int layer_x = (int) rect.x / GameScreen.TILE_AREA;
                int layer_y = (int) rect.y / GameScreen.TILE_AREA;

                if (pos.x == layer_x && pos.y == layer_y) {
                    return obj.getName();
                }
            }
        }
        return "";
    }

    public void activateLasers(IRobot robot) {
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
                }
                if (canGo(new Vector2(robot.getXPos(), robot.getYPos()), robot.getDirection())) {
                    robotLaserX++;
                    continue;
                }
            }
        }
        else if (robot.getDirection() == WEST) {
            robotLaserX--;
            while (boardLayer.getCell(robotLaserX, robotLaserY) == null) {
                if (playerLayer.getCell(robotLaserX, robotLaserY) != null) {
                    IRobot victim = getRobotOnPos(robotLaserX, robotLaserY);
                    victim.takeDamage();
                }
                if (canGo(new Vector2(robot.getXPos(), robot.getYPos()), robot.getDirection())) {
                    robotLaserX--;
                    continue;
                }
            }
        }
        else if (robot.getDirection() == SOUTH) {
            robotLaserY--;
            while (boardLayer.getCell(robotLaserX, robotLaserY) == null) {
                if (playerLayer.getCell(robotLaserX, robotLaserY) != null) {
                    IRobot victim = getRobotOnPos(robotLaserX, robotLaserY);
                    victim.takeDamage();
                }
                if (canGo(new Vector2(robot.getXPos(), robot.getYPos()), robot.getDirection())) {
                    robotLaserY--;
                    continue;
                }
            }
        }
        else if (robot.getDirection() == NORTH) {
            robotLaserY++;
            while (boardLayer.getCell(robotLaserX, robotLaserY) == null) {
                if (playerLayer.getCell(robotLaserX, robotLaserY) != null) {
                    IRobot victim = getRobotOnPos(robotLaserX, robotLaserY);
                    victim.takeDamage();
                }
                if (canGo(new Vector2(robot.getXPos(), robot.getYPos()), robot.getDirection())) {
                    robotLaserY--;
                    continue;
                }
            }
        }
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

    public boolean canGo(Vector2 pos, Direction dir) {
        String objectName = getObjectNameOnXandY(tiledMap, pos);
        if (canGoCurrentTile(pos, objectName, dir)) {
            Vector2 nextPos = Direction.getPosInDirection(pos,dir);
            objectName = getObjectNameOnXandY(tiledMap, nextPos);
            switch (dir) {
                case SOUTH:
                    if (objectName.equals("NorthWall")) {
                        return false;
                    }
                    break;
                case WEST:
                    if (objectName.equals("EastWall")) {
                        return false;
                    }
                    break;
                case EAST:
                    if (objectName.equals("WestWall")) {
                        return false;
                    }
                    break;
                case NORTH:
                    if (objectName.equals("SouthWall")) {
                        return false;
                    }
                    break;
            }
        }
        else {
            return false;
        }
        return true;
    }

    public boolean canGoCurrentTile(Vector2 pos, String currentObjectName, Direction dir) {
        Direction wallDirection;
        switch (currentObjectName) {
            case "SouthWall":
                if (dir == SOUTH) {
                    return false;
                }
                break;
            case "WestWall":
                if (dir == WEST) {
                    return false;
                }
                break;
            case "EastWall":
                if (dir == EAST) {
                    return false;
                }
                break;
            case "NorthWall":
                if (dir == NORTH) {
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
        String objectname = getObjectNameOnXandY(tiledMap, new Vector2(robot.getXPos(),robot.getYPos()));
        if(objectname != null && objectname.contains("flag")){
            robot.addFlag(objectname);
        }
    }

    /**
     * Changes the robots direction when on a gear.
     * @param robot robot
     */
    public void changeDirOnGear(IRobot robot) {
        String objectName = getObjectNameOnXandY(tiledMap, new Vector2(robot.getXPos(),robot.getYPos()));
        switch (objectName) {
            case "clockwise":
                robot.rotateClockwise();
                break;
            case "counterclockwise":
                robot.rotateCounterClockwise();
                break;
            default:
                break;
        }
    }

    public ArrayList<String> getFlags() {
        if(flags != null)
            return flags;

        TiledMapTileLayer flagLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Flag");
        flags = new ArrayList<>();

        for (int x = 0; x < flagLayer.getWidth(); x++)
            for (int y = 0; y < flagLayer.getHeight(); y++)
                if (flagLayer.getCell(x, y) != null) {
                    flags.add(getObjectNameOnXandY(tiledMap,new Vector2(x,y)));
                }
        return flags;
    }

    public ArrayList<Integer> getXandYposofRespawnPoint() {
        TiledMapTileLayer respawnObjects = (TiledMapTileLayer) tiledMap.getLayers().get("SpawnPoint");
        ArrayList<Integer> spawnpointList = new ArrayList<>();

        for (int x = 0; x < respawnObjects.getWidth(); x++)
            for (int y = 0; y < respawnObjects.getHeight(); y++)
                if (respawnObjects.getCell(x, y) != null) {
                    spawnpointList.add(x);
                    spawnpointList.add(y);
                }
        return spawnpointList;
    }

    public void conveyorBelt(IRobot robot) {
        String objectName = getObjectNameOnXandY(tiledMap, new Vector2(robot.getXPos(),robot.getYPos()));
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

    /**
     * Check position in direction and push robot in direction if there is a robot there
     * @param x - pusher-robots original x position
     * @param y - pusher-robots original y position
     * @param dir - push direction
     */
    public void pushIfPossible(int x, int y, Direction dir){
        Vector2 vector = Direction.getPosInDirection(new Vector2(x, y), dir);
        IRobot otherRobot = getRobotOnPos((int)vector.x, (int)vector.y);

        if(otherRobot != null){
            GameScreen.getInstance().unrenderRobot(otherRobot);
            otherRobot.moveOne(dir);
            GameScreen.getInstance().renderRobot(otherRobot);
        }
    }

    /**
     * @param x - x position
     * @param y - y position
     * @return IRobot-object on position or null if there is no robot on position
     */
    private IRobot getRobotOnPos(int x, int y){
        for(IRobot robot:GameScreen.getInstance().getRobots()){
            if(robot.getXPos() == x && robot.getYPos() == y){
                return robot;
            }
        }
        return null;
    }

}
