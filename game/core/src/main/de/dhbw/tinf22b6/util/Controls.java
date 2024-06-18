package de.dhbw.tinf22b6.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

public class Controls {
    public static void setupControls() {
        Preferences preferences = Gdx.app.getPreferences("Controls");
        if (preferences.contains("run")) return;
        preferences.putInteger("left", Input.Keys.A);
        preferences.putInteger("right", Input.Keys.D);
        preferences.putInteger("up", Input.Keys.W);
        preferences.putInteger("down", Input.Keys.S);
        preferences.putInteger("dodge", Input.Keys.SPACE);
        preferences.putInteger("inventory", Input.Keys.I);
        preferences.putInteger("interact", Input.Keys.E);
        preferences.putFloat("music", 0.5f);
        preferences.putFloat("sfx", 0.5f);
        preferences.putBoolean("muteMusic", false);
        preferences.putInteger("run", Input.Keys.SHIFT_LEFT);
        preferences.flush();
    }
}
