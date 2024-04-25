package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public enum WorldType {
    MAIN_MENU("level/startMap.tmx"),
    LEVEL1("level/startMap.tmx"),
    LEVEL2("level/TeleportTestFixed.tmx"),
    LEVEL3("level/2.0Map.tmx");

    private final String path;

    WorldType(String path) {
        this.path = path;
    }

    public TiledMap getMap() {
        return new TmxMapLoader().load(path);
    }
}
