package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.TestApplication;
import de.dhbw.tinf22b6.UnderwatchGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WorldParserTest {

    private HeadlessApplication headlessApplication;
    private World world;
    private TiledMap map;

    /**
     * Helper method for {@link Files#internal(String) Gdx.files.internal(String)}.
     * Returns a {@link FileHandle}.
     */
    public FileHandle internal(String path) {
        initApplication();
        FileHandle handle = Gdx.files.internal(path);
        if (handle == null) {
            throw new NullPointerException(String.format("internal(%s) returned null: %s", path, handle));
        }
        return handle;
    }

    /**
     * Initialise a test headless application.
     */
    public void initApplication() {
        if (headlessApplication == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            headlessApplication = new HeadlessApplication(new UnderwatchGame(), config);
            assertNotNull(Gdx.files);
        }
    }

    @BeforeEach
    void setUp() {
        initApplication();
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