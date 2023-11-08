package de.dhbw.tinf22b6.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import de.dhbw.tinf22b6.world.WorldController;
import de.dhbw.tinf22b6.world.WorldRenderer;

public class GameScreen extends AbstractGameScreen {
    private static final String TAG = GameScreen.class.getName();
    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private boolean paused;
    Music m = Gdx.audio.newMusic(Gdx.files.internal("music/downfall.mp3"));

    public GameScreen (Game game) {
        super(game);
        m.setLooping(true);
        m.setVolume(0.5f);
        //m.play();
    }

    @Override
    public void render (float deltaTime) {
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
    public void resize (int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void show () {
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
    }

    @Override
    public void hide () {
        m.dispose();
    }

    @Override
    public void pause () {
        paused = true;
    }

    @Override
    public void resume () {
        super.resume();
        // Only called on Android!
        paused = false;
    }
}
