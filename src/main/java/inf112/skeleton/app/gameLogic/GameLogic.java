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

    private HashMap<String, Vector2> flagPosHashMap;
    private ArrayList<String> flags;

    public GameLogic() {
        tiledMap = GameScreen.getInstance().getTiledMap();
    }

    /**
     * Checks each layer on the given pos and returns the first objectName it finds.
     * Maybe change it so it collects all objectNames on the map and returns an list of them.
     * @param tiledMap Tiledmap
     * @param pos Vector2
     * @return the objectName on the pos
     */
    public String getObjectNameOnPos(TiledMap tiledMap, Vector2 pos) {
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

    public void activateWallLasers(ArrayList<Vector2> wallLaserList) {
        for (Vector2 pos : wallLaserList) {
            String wallName = getObjectNameOnPos(tiledMap, pos);
            if (wallName.contains("West")) {
                pos.sub(1,0);
                activateLasersFromPos(pos, Direction.oppositeOf(WEST));
            }
            else if (wallName.contains("South")) {
                pos.add(0,1);
                activateLasersFromPos(pos, Direction.oppositeOf(NORTH));
            }
        }
    }

    /**
     * Generate a horisontal or vertical laser on the board
     * @param pos - position on screen
     * @param dir - direction of lasers
     */
    public void activateLasersFromPos(Vector2 pos, Direction dir) {
        Vector2 nextPos = getPosInDirection(pos, dir);

        TiledMapTileLayer boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board");

        while (boardLayer.getCell((int) nextPos.x, (int) nextPos.y) != null) {
            IRobot victim = getRobotOnPos(nextPos);
            if(victim != null && victim.isAlive()) {
                System.out.println(victim.getName() + " has taken damage!");
                victim.takeDamage();
                break;
            }

            if (canGo(pos, dir) && canGo(nextPos, dir)) {
                nextPos = getPosInDirection(nextPos, dir);
            } else {
                break;
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

    // TODO fix bug where player can move onto a robot when pushed against a wall.
    public boolean canGo(Vector2 pos, Direction dir) {
        String objectName = getObjectNameOnPos(tiledMap, pos);
        if (canGoCurrentTile(objectName, dir)) {
            Vector2 nextPos = Direction.getPosInDirection(pos,dir);
            objectName = getObjectNameOnPos(tiledMap, nextPos);

            if (objectName.contains("Wall")) {
                return !objectName.toUpperCase().contains(Direction.oppositeOf(dir).toString().toUpperCase());
            }
        }
        else {
            return false;
        }
        return true;
    }

    public boolean canGoCurrentTile(String currentObjectName, Direction dir) {
        if (currentObjectName.contains("Wall")) {
            return !currentObjectName.toUpperCase().contains(dir.toString().toUpperCase());
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
        String objectName = getObjectNameOnPos(tiledMap, robot.getPos());
        if(objectName != null && objectName.contains("Flag")){
            robot.addFlag(objectName);
        }
    }

    /**
     * Changes the robots direction when on a gear.
     * @param robot robot
     */
    public void changeDirOnGear(IRobot robot) {
        String objectName = getObjectNameOnPos(tiledMap, robot.getPos());
        if (objectName.equals("Counter_Clockwise")) {
            robot.rotateCounterClockwise();
        } else if (objectName.equals("Clockwise")) {
            robot.rotateClockwise();
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
                    String flagName = getObjectNameOnPos(tiledMap,pos);
                    flagPosHashMap.put(flagName,pos);
                    flags.add(flagName);
                }
        return flagPosHashMap;
    }

    /**
     * @return an ArrayList<Vector2> containing all SpawnPoints.
     */
    public ArrayList<Vector2> getAllPositionsFromObjectName(String layerName) {
        TiledMapTileLayer layers = (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
        ArrayList<Vector2> posList = new ArrayList<>();

        for (int x = 0; x < layers.getWidth(); x++) {
            for (int y = 0; y < layers.getHeight(); y++) {
                if (layers.getCell(x, y) != null) {
                    posList.add(new Vector2(x, y));
                }
            }
        }
        return posList;
    }

    /**
     * Moves and rotates the input robot if it's on a conveyor.
     * @param robot IRobot
     */
    public void conveyorBelts(IRobot robot) {
        String conveyor = getObjectNameOnPos(tiledMap, robot.getPos());
        if (conveyor.contains("Conveyor")) {
            Direction dir = getConveyorDir(conveyor);
            moveConveyor(robot,conveyor,dir);
            robot.setCameFromConveyor(true);

            String endConveyor = getObjectNameOnPos(tiledMap, robot.getPos());
            // check if the tile you end up on is a corner conveyor.
            if (endConveyor.contains("Rotate") && getConveyorDir(endConveyor) != dir) {
                rotateConveyor(robot,endConveyor);
            }
        } else {
            robot.setCameFromConveyor(false);
        }
    }

    /**
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
     * @param robot IRobot
     * @param conveyor name of object tile
     * @param dir Direction
     */
    public void moveConveyor(IRobot robot, String conveyor, Direction dir) {
        if (conveyor.contains("Express_Conveyor")) {
            robot.moveOne(dir);
            String currentExpressConveyor = getObjectNameOnPos(tiledMap, robot.getPos());
            if (currentExpressConveyor.contains("Conveyor")) {
                robot.setCameFromConveyor(true); // should probably be replaced with something more robust.
                // check if the next tile is a corner.
                if (currentExpressConveyor.contains("Rotate") && getConveyorDir(currentExpressConveyor) != dir) {
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

    /**
     * Executes the events that happens at the end of a phase
     * for the given robot.
     * Should probably just do it for all robots from the get go
     * @param robot IRobot
     */
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
