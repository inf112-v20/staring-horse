package inf112.skeleton.app.gamelogic;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.screen.GameScreen;

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

    public boolean canGo(Player player, boolean backwards) {
        String objectName = getObjectNameOnXandY(tiledMap, player.getXPos(), player.getYPos());
        int step = 1;
        if (canGoCurrentTile(player, objectName, backwards)) {
            switch (player.getDirection()) {
                case SOUTH:
                    if (backwards) {
                        step = -1;
                        objectName = getObjectNameOnXandY(tiledMap, player.getXPos(), player.getYPos() - step);
                        if (objectName.equals("SouthWall")) {
                            return false;
                        }
                    }
                    else {
                        objectName = getObjectNameOnXandY(tiledMap, player.getXPos(), player.getYPos() - step);
                        if (objectName.equals("NorthWall")) {
                            return false;
                        }
                    }
                    break;
                case WEST:
                    if (backwards) {
                        step = -1;
                        objectName = getObjectNameOnXandY(tiledMap, player.getXPos() - step, player.getYPos());
                        if (objectName.equals("WestWall")) {
                            return false;
                        }
                    }
                    else {
                        objectName = getObjectNameOnXandY(tiledMap, player.getXPos() - step, player.getYPos());
                        if (objectName.equals("EastWall")) {
                            return false;
                        }
                    }
                    break;
                case EAST:
                    if (backwards) {
                        step = -1;
                        objectName = getObjectNameOnXandY(tiledMap, player.getXPos() + step, player.getYPos());
                        if (objectName.equals("EastWall")) {
                            return false;
                        }
                    }
                    else {
                        objectName = getObjectNameOnXandY(tiledMap, player.getXPos() + step, player.getYPos());
                        if (objectName.equals("WestWall")) {
                            return false;
                        }
                    }
                    break;
                case NORTH:
                    if (backwards) {
                        step = -1;
                        objectName = getObjectNameOnXandY(tiledMap, player.getXPos(), player.getYPos() + step);
                        if (objectName.equals("NorthWall")) {
                            return false;
                        }
                    }
                    else {
                        objectName = getObjectNameOnXandY(tiledMap, player.getXPos(), player.getYPos() + step);
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

    public boolean canGoCurrentTile(Player player, String currentObjectName, boolean backwards) {
        Direction wallDirection;
        switch (currentObjectName) {
            case "SouthWall":
                if (backwards) {
                    wallDirection = Direction.NORTH;
                }
                else {
                    wallDirection = Direction.SOUTH;
                }
                if (player.getDirection() == wallDirection) {
                    return false;
                }
                break;
            case "WestWall":
                if (backwards) {
                    wallDirection = Direction.EAST;
                }
                else {
                    wallDirection = Direction.WEST;
                }
                if (player.getDirection() == wallDirection) {
                    return false;
                }
                break;
            case "EastWall":
                if (backwards) {
                    wallDirection = Direction.WEST;
                }
                else {
                    wallDirection = Direction.EAST;
                }
                if (player.getDirection() == wallDirection) {
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
                if (player.getDirection() == wallDirection) {
                    return false;
                }
                break;
        }
        return true;
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

    public void pickUpFlag(Player player) {
        String objectname = getObjectNameOnXandY(tiledMap, player.getXPos(), player.getYPos());
        switch(objectname) {
            case "flag1":
                player.addFlag("flag1");
                break;
            case "flag2":
                player.addFlag("flag2");
                break;
            case "flag3":
                player.addFlag("flag3");
                break;
            case "flag4":
                player.addFlag("flag4");
                break;
        }
    }


}
