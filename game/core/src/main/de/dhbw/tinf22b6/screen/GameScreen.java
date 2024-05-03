package de.dhbw.tinf22b6.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.ui.ingame.InGameStageHandler;
import de.dhbw.tinf22b6.util.EntitySystem;
import de.dhbw.tinf22b6.util.PlayerStatistics;
import de.dhbw.tinf22b6.world.*;

import static de.dhbw.tinf22b6.util.Constants.VIEWPORT_HEIGHT;
import static de.dhbw.tinf22b6.util.Constants.VIEWPORT_WIDTH;

public class GameScreen extends AbstractGameScreen {
    private TiledMap map;
    Music m = Gdx.audio.newMusic(Gdx.files.internal("music/downfall.mp3"));
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean paused;
    private InGameStageHandler stageHandler;

    public GameScreen(Game game, TiledMap map) {
        super(game);
        this.map = map;
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public void render(float deltaTime) {
        // Do not update game world when paused.
        if (!paused) {
            // Update game world by the time that has passed
            // since last rendered frame.
            worldController.update(deltaTime);
            PlayerStatistics.instance.incrementGameTime(deltaTime);
        }
        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0, 0, 0, 0);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Render game world to screen
        worldRenderer.render();
        stageHandler.update();
        stageHandler.drawAndAct();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void show() {
        m.setVolume(Gdx.app.getPreferences("Controls").getBoolean("muteMusic") ? 0 : Gdx.app.getPreferences("Controls").getFloat("music"));
        m.play();
        World world = new World(new Vector2(0, 0), false);
        WorldParser.parseStaticObjects(map, world);
        world.setContactListener(new WorldListener(this));
        EntitySystem.instance.init(WorldParser.parseGameObjects(map, world));
        OrthographicCamera camera =
                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        worldController = new WorldController(game, world, camera, this);
        worldRenderer = new WorldRenderer(worldController, world, map, camera);
        stageHandler = new InGameStageHandler(game, this);
    }

    @Override
    public void hide() {
        m.stop();
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

    public void unpause() {
        this.paused = false;
        Gdx.input.setInputProcessor(worldController);
    }

    public void setPaused() {
        this.paused = true;
    }

    public void changeMap(WorldType worldType) {
        this.map = worldType.getMap();
        this.show();
    }
}
