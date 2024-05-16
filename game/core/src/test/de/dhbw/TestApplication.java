package de.dhbw;

import static com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.dhbw.tinf22b6.UnderwatchGame;
import org.junit.jupiter.api.extension.Extension;

/**
 * Similar to {@link HeadlessApplication}, except that it provides a real OpenGL
 * context using LWJGL3. This makes it
 * possible to test things that use features that depend on OpenGL, like a
 * {@link SpriteBatch}.
 */
public class TestApplication extends HeadlessApplication implements Extension {
    public TestApplication(ApplicationListener listener, HeadlessApplicationConfiguration config) {
        super(listener, config);
        new UnderwatchGame();
    }
}
