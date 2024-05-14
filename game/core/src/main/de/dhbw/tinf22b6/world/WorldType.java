package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public enum WorldType {
    MAIN_MENU("level/Demo.tmx"),
    LEVEL1("level/startMap.tmx"),
    LEVEL2("level/Dungeon1.tmx"),
    LEVEL3("level/2.0Map.tmx"),
    PATHFINDING("level/pathfinding.tmx");

    private final String path;

    WorldType(String path) {
        this.path = path;
    }

    public TiledMap getMap() {
        return new TmxMapLoader().load(path);
    }
}
