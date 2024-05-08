package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldParserTest {

    private World world;
    private TiledMap map;

    @BeforeEach
    void setUp() {
        this.world = new World(new Vector2(0, 0), true);
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

    @AfterEach
    void tearDown() {
    }

    @Test
    void parseStaticObjects() {
        assertEquals(map.getLayers().get(0).getName(), "floor");
        assertEquals(map.getLayers().get(1).getName(), "walls");
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