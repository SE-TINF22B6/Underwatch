package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public enum WorldType {
    LEVEL1("level/2.0Map.tmx"),
    LEVEL2("level/2.0Map.tmx"),
    LEVEL3("level/2.0Map.tmx");

    private final String path;

    WorldType(String path) {
        this.path = path;
    }

    public TiledMap getMap() {
        return new TmxMapLoader().load(path);
    }
}
