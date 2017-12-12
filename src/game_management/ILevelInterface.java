package game_management;

import game_object.general.GameObjectHandler;
import game_object.general.ObjectID;
import game_object.map.TileMap;

import java.awt.*;

public interface ILevelInterface {

    int TOTAL_LEVEL_COUNT = 3;
    String getLevelTileMap();
    String getName();
    ObjectID getEnemyType();
    ObjectID getCharacterType();

    int getEnemySize();

    Point [] getCharacterPositions();
    Point [] getEnemyPositions();

}
