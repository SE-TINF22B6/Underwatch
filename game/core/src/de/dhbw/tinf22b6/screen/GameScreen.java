package de.dhbw.tinf22b6.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.overlay.ScreenOverlay;
import de.dhbw.tinf22b6.world.WorldController;
import de.dhbw.tinf22b6.world.WorldListener;
import de.dhbw.tinf22b6.world.WorldParser;
import de.dhbw.tinf22b6.world.WorldRenderer;

public class GameScreen extends AbstractGameScreen {
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private ScreenOverlay overlay;

    private boolean paused;
    Music m = Gdx.audio.newMusic(Gdx.files.internal("music/downfall.mp3"));
    private final TiledMap map;
    private World world;

    public GameScreen(Game game, TiledMap map) {
        super(game);
        this.map = map;
    }

    @Override
    public void render(float deltaTime) {
        // Do not update game world when paused.
        if (!paused) {
            // Update game world by the time that has passed
            // since last rendered frame.
            worldController.update(deltaTime);
        }
        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f,
                0x95 / 255.0f,
                0xed / 255.0f,
                0xff / 255.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Render game world to screen
        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0,0), false);
        WorldParser.parseWalls(map, world);
        world.setContactListener(new WorldListener());
        worldController = new WorldController(game, world, WorldParser.parseGameObjects(map, world));
        worldRenderer = new WorldRenderer(worldController, world, map);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        // Only called on Android!
        paused = false;
    }
}
