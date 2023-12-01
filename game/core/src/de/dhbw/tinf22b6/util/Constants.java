package de.dhbw.tinf22b6.util;

public class Constants {
    public static final int TILE_SIZE = 16;
    public static final float VIEWPORT_WIDTH = TILE_SIZE * 15;
    public static final float VIEWPORT_HEIGHT = TILE_SIZE * 12;
    public static final String ATLAS_PATH = "textureAtlas/Underwatch.atlas";
    public static final String SKIN_PATH = "skin/craftacular-ui.json";
    public static final short NOTHING_BIT = 0;
    public static final short ENEMY_BIT = 1;
    public static final short COIN_BIT = 2;
    public static final short WEAPON_BIT = 4;
    public static final short BOX_COLLISION_BIT = 8;
    public static final short BOX_INTERACTION_BIT = 16;
    public static final short DOOR_COLLISION_BIT = 32;
    public static final short DOOR_INTERACTION_BIT = 64;
    public static final short POTION_BIT = 128;
    public static final short ELEVATOR_BIT = 256;
    public static final short PLAYER_BIT = 512;
    public static final short WALL_BIT = 1024;
    public static final short WEAPON_ENEMY_BIT = 2048;
    public static final short ENEMY_SIGHT_BIT = 4096;
}
