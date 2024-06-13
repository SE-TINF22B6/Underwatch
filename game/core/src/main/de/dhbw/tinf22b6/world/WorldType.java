package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import de.dhbw.tinf22b6.util.MusicPlayer;

public enum WorldType {
    MAIN_MENU("level/Demo.tmx", "music/main_menu.mp3"),
    LEVEL1("level/startMap.tmx", "music/downfall.mp3"),
    LEVEL2("level/Dungeon1.tmx", "music/Heroic Demise.mp3"),
    LEVEL3("level/Dungeon3.tmx", "music/Heroic Demise.mp3"),
    LEVEL4("level/level5.tmx", "music/song17.mp3"),
    LEVEL5("level/Dungeon6.tmx", "music/battleThemeA.mp3");

    private final String path;
    private final String musicPath;

    WorldType(String path, String musicPath) {
        this.path = path;
        this.musicPath = musicPath;
    }

    public Music getMusic() {
        return Gdx.audio.newMusic(Gdx.files.internal(musicPath));
    }

    public TiledMap getMap() {
        return new TmxMapLoader().load(path);
    }
}
