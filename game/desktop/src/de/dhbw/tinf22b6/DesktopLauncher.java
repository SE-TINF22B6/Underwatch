package de.dhbw.tinf22b6;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setWindowedMode(800, 600);
        //config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        config.setTitle("Underwatch");
        new Lwjgl3Application(new UnderwatchGame(), config);
    }
}
