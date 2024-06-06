package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public enum WorldType {
    MAIN_MENU("level/Demo.tmx"),
    LEVEL1("level/startMap.tmx"),
    LEVEL2("level/Dungeon1.tmx"),
    LEVEL3("level/Dungeon3.tmx"),
    LEVEL4("level/level5.tmx"),
    LEVEL5("level/Dungeon6.tmx");

    private final String path;

    WorldType(String path) {
        this.path = path;
    }

    public TiledMap getMap() {
        return new TmxMapLoader().load(path);
    }
}
