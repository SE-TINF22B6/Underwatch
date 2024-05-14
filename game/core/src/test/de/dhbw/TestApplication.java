 package de.dhbw;

 import static com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application.*;
 import static org.lwjgl.glfw.GLFW.glfwTerminate;

 import com.badlogic.gdx.Application;
 import com.badlogic.gdx.ApplicationListener;
 import com.badlogic.gdx.ApplicationLogger;
 import com.badlogic.gdx.Audio;
 import com.badlogic.gdx.Files;
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.Graphics;
 import com.badlogic.gdx.Input;
 import com.badlogic.gdx.LifecycleListener;
 import com.badlogic.gdx.Net;
 import com.badlogic.gdx.Preferences;
 import com.badlogic.gdx.assets.AssetManager;
 import com.badlogic.gdx.backends.headless.HeadlessApplication;
 import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
 import com.badlogic.gdx.backends.headless.HeadlessApplicationLogger;
 import com.badlogic.gdx.backends.headless.HeadlessFiles;
 import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
 import com.badlogic.gdx.backends.headless.HeadlessNet;
 import com.badlogic.gdx.backends.headless.HeadlessPreferences;
 import com.badlogic.gdx.backends.headless.mock.audio.MockAudio;
 import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
 import com.badlogic.gdx.backends.lwjgl3.Lwjgl3NativesLoader;
 import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.utils.Array;
 import com.badlogic.gdx.utils.Clipboard;
 import com.badlogic.gdx.utils.ObjectMap;

 import de.dhbw.tinf22b6.UnderwatchGame;
 import de.dhbw.tinf22b6.screen.MenuScreen;
 import de.dhbw.tinf22b6.util.Assets;
 import de.dhbw.tinf22b6.util.Controls;
 import org.junit.jupiter.api.extension.Extension;
 import org.lwjgl.glfw.GLFW;
 import org.lwjgl.glfw.GLFWErrorCallback;

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
