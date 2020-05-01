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
import java.util.HashMap;

import static inf112.skeleton.app.enums.Direction.*;

public class GameLogic {

    private TiledMap tiledMap;

    private static GameLogic SINGLE_INSTANCE = null;
    private HashMap<String, Vector2> flagPosHashMap;
    private ArrayList<String> flags;

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

    public void activateLasersFromPos(Vector2 pos, Direction dir) {
        Vector2 nextPos = getPosInDirection(pos, dir);

        TiledMapTileLayer boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");

        while (boardLayer.getCell((int) nextPos.x, (int) nextPos.y) != null) {
            IRobot victim = getRobotOnPos(nextPos);
            if(victim != null) {
                System.out.println(victim.getName() + " has taken damage!");
                victim.takeDamage();
            }
            if (canGo(nextPos, dir)) {
                nextPos = getPosInDirection(nextPos, dir);
            }
        }
    }

    /**
     * @param pos - position on screen
     * @return true if position is a hole or is off the map
     */
    public boolean isHole(Vector2 pos) {
        TiledMapTileLayer holeLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Hole");
        TiledMapTileLayer boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");
        int x = (int) pos.x;
        int y = (int) pos.y;
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
        String objectName = getObjectNameOnXandY(tiledMap, robot.getPos());
        if(objectName != null && objectName.contains("flag")){
            robot.addFlag(objectName);
        }
    }

    /**
     * Changes the robots direction when on a gear.
     * @param robot robot
     */
    public void changeDirOnGear(IRobot robot) {
        String objectName = getObjectNameOnXandY(tiledMap, robot.getPos());
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
        //loads flags if not loaded
        if(flags == null) getFlagPosHashMap();
        return flags;
    }

    public HashMap<String,Vector2> getFlagPosHashMap() {
        if(flagPosHashMap != null)
            return flagPosHashMap;

        TiledMapTileLayer flagLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Flag");
        flags = new ArrayList<>();
        flagPosHashMap = new HashMap<>();

        for (int x = 0; x < flagLayer.getWidth(); x++)
            for (int y = 0; y < flagLayer.getHeight(); y++)
                if (flagLayer.getCell(x, y) != null) {
                    Vector2 pos = new Vector2(x,y);
                    String flagName = getObjectNameOnXandY(tiledMap,pos);
                    flagPosHashMap.put(flagName,pos);
                    flags.add(flagName);
                }
        return flagPosHashMap;
    }

    public ArrayList<Vector2> getAllRespawnPointPositions() {
        TiledMapTileLayer respawnObjects = (TiledMapTileLayer) tiledMap.getLayers().get("SpawnPoint");
        ArrayList<Vector2> spawnPointList = new ArrayList<>();

        for (int x = 0; x < respawnObjects.getWidth(); x++)
            for (int y = 0; y < respawnObjects.getHeight(); y++)
                if (respawnObjects.getCell(x, y) != null) {
                    spawnPointList.add(new Vector2(x,y));
                }
        return spawnPointList;
    }

    public void conveyorBelts(IRobot robot) {
        String conveyor = getObjectNameOnXandY(tiledMap, robot.getPos());
        if (conveyor.contains("Conveyor")) {
            Direction dir = getConveyorDir(conveyor);
            moveConveyor(robot,conveyor,dir);
            robot.setCameFromConveyor(true);

            String endConveyor = getObjectNameOnXandY(tiledMap, robot.getPos());
            // check if the tile you end up on is a corner conveyor.
            if (endConveyor.contains("Rotate") || getConveyorDir(endConveyor) != dir) {
                rotateConveyor(robot,endConveyor);
            }
        } else {
            robot.setCameFromConveyor(false);
        }
    }

    /**
     *
     * @param conveyor name of object tile.
     * @return direction of the conveyor.
     */
    public Direction getConveyorDir(String conveyor) {
        Direction dir = null;
        // get direction from conveyor.
        if (conveyor.contains("To_North")){
            dir = NORTH;
        } else if (conveyor.contains("To_West")) {
            dir = WEST;
        } else if (conveyor.contains("To_South")) {
            dir = SOUTH;
        } else if (conveyor.contains("To_East")) {
            dir = EAST;
        }
        return dir;
    }

    /**
     * Moves the robot in the direction of the conveyor the robot is standing on.
     * OBS!
     * If a conveyor belt would move a
     * robot into a non-conveyor belt space where another robot sits,
     * the robot in motion must stop on the last space of the conveyor belt.
     * It does not push the robot in its way.
     * OBS!!
     * Both robots would end their move on the same conveyor belt space.
     * In this rare instance, both robots stay where they are.
     * @param robot IRobot
     * @param conveyor name of object tile
     * @param dir Direction
     */
    public void moveConveyor(IRobot robot, String conveyor, Direction dir) {
        if (conveyor.contains("Express_Conveyor")) {
            robot.moveOne(dir);
            String currentExpressConveyor = getObjectNameOnXandY(tiledMap, robot.getPos());
            if (currentExpressConveyor.contains("Conveyor")) {
                robot.setCameFromConveyor(true); // should probably be replaced with something more robust.
                // check if the next tile is a corner.
                if (currentExpressConveyor.contains("Rotate")) {
                    rotateConveyor(robot,currentExpressConveyor);
                    robot.moveOne(getConveyorDir(currentExpressConveyor));
                } else {
                    robot.moveOne(dir);
                }
            }
            // technically not needed since only conveyors get sent to this method.
        } else if (conveyor.contains("Conveyor")) {
            robot.moveOne(dir);
        }
    }

    /**
     * when a robot lands on a rotate conveyor and should rotate. it rotates before it does anything else.
     * so at the end of a phase after a conveyor has pushed the robot onto the rotate conveyor it should
     * rotate the robot. --|
     * Rotates the robot clockwise or counterclockwise depending on the
     * corresponding conveyor.
     * @param robot IRobot
     * @param conveyor name of object tile
     */
    public void rotateConveyor(IRobot robot, String conveyor) {
        // robot should only rotate when coming from a conveyor.
        if (robot.getCameFromConveyor()) {
            if (conveyor.contains("CounterClockwise")) {
                robot.rotateCounterClockwise();
            } else if (conveyor.contains("Clockwise")) {
                robot.rotateClockwise();
            }
        } else {
            System.out.println("Cannot rotate on conveyor!");
        }
    }

    public void endOfPhaseCheck(IRobot robot) {
        if (robot instanceof Player) {
            GameScreen.getInstance().unrenderRobot(robot);
            Player player = (Player) robot;
            if (!player.getIsTestPlayer()) {
                pickUpFlag(player);
                changeDirOnGear(player);
                conveyorBelts(player);
                activateLasersFromPos(robot.getPos(), robot.getDirection());
            }
        } else {
            GameScreen.getInstance().unrenderRobot(robot);
            pickUpFlag(robot);
            changeDirOnGear(robot);
            conveyorBelts(robot);
            activateLasersFromPos(robot.getPos(), robot.getDirection());
        }
        GameScreen.getInstance().renderRobot(robot);
    }

    /**
     * Check position in direction and push robot in direction if there is a robot there
     * @param pos - pusher-robots original Vector2 position
     * @param dir - push direction
     */
    public void pushIfPossible(Vector2 pos, Direction dir){
        Vector2 vector = Direction.getPosInDirection(pos, dir);
        IRobot otherRobot = getRobotOnPos(vector);

        if(otherRobot != null){
            GameScreen.getInstance().unrenderRobot(otherRobot);
            otherRobot.moveOne(dir);
            GameScreen.getInstance().renderRobot(otherRobot);
        }
    }

    /**
     * @param pos - Vector2 position
     * @return IRobot-object on position or null if there is no robot on position
     */
    private IRobot getRobotOnPos(Vector2 pos){
        for(IRobot robot:GameScreen.getInstance().getRobots()){
            if(robot.getPos().equals(pos)){
                return robot;
            }
        }
        return null;
    }

}
