package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldParserTest {
    private TiledMap map;

    @BeforeEach
    void setUp() {
        this.map = new TiledMap();
        MapLayer background = new MapLayer();
        background.setName("floor");
        map.getLayers().add(background);
        MapLayer walls = new MapLayer();
        walls.setName("walls");
        MapObject wall0 = new MapObject();
        walls.getObjects().add(wall0);
        map.getLayers().add(walls);
    }

    @Test
    @DisplayName("Setup of test map is correct")
    void setupCorrect() {
        assertEquals("floor", map.getLayers().get(0).getName());
        assertEquals("walls", map.getLayers().get(1).getName());
        assertEquals(1, map.getLayers().get(1).getObjects().getCount());
    }

    @Test
    void parseStaticObjects() {
    }

    @Test
    void parseGameObjects() {
    }

    @Test
    void parseTorches() {
    }

    @Test
    void getStaticBodyDef() {
    }

    @Test
    void getDynamicBodyDef() {
    }

    @Test
    void testGetDynamicBodyDef() {
    }

    @Test
    void parseNavigationMap() {
    }
}