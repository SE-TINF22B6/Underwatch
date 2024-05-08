package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        // check position
        assertEquals(new Vector2(0, 0), WorldParser.getStaticBodyDef(0, 0).position);
        assertEquals(new Vector2(0, 1), WorldParser.getStaticBodyDef(0, 1).position);
        assertEquals(new Vector2(1, 0), WorldParser.getStaticBodyDef(1, 0).position);
        // check body type
        assertEquals(BodyDef.BodyType.StaticBody, WorldParser.getStaticBodyDef(0, 0).type);
        assertNotEquals(BodyDef.BodyType.DynamicBody, WorldParser.getStaticBodyDef(0, 0).type);
        assertNotEquals(BodyDef.BodyType.KinematicBody, WorldParser.getStaticBodyDef(0, 0).type);
    }

    @Test
    void getDynamicBodyDef() {
        // check position
        assertEquals(new Vector2(0, 0), WorldParser.getDynamicBodyDef(0, 0).position);
        assertEquals(new Vector2(0, 1), WorldParser.getDynamicBodyDef(0, 1).position);
        assertEquals(new Vector2(1, 0), WorldParser.getDynamicBodyDef(1, 0).position);
        // check wrapper call
        assertEquals(new Vector2(0, 0), WorldParser.getDynamicBodyDef(new Vector2(0, 0)).position);
        assertEquals(new Vector2(0, 1), WorldParser.getDynamicBodyDef(new Vector2(0, 1)).position);
        assertEquals(new Vector2(1, 0), WorldParser.getDynamicBodyDef(new Vector2(1, 0)).position);
        // check body type
        assertEquals(BodyDef.BodyType.DynamicBody, WorldParser.getDynamicBodyDef(0, 0).type);
        assertNotEquals(BodyDef.BodyType.StaticBody, WorldParser.getDynamicBodyDef(0, 0).type);
        assertNotEquals(BodyDef.BodyType.KinematicBody, WorldParser.getDynamicBodyDef(0, 0).type);
    }

    @Test
    void parseNavigationMap() {

    }
}