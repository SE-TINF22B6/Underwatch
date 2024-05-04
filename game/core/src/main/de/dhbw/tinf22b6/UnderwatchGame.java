package de.dhbw.tinf22b6;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import de.dhbw.tinf22b6.screen.MenuScreen;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Controls;

public class UnderwatchGame extends Game {
    @Override
    public void create() {
        // Set Libgdx log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        // Write Controls to File if they don't exist
        Controls.setupControls();

        // Load assets
        Assets.instance.init(new AssetManager());

        // Start game at menu screen
        setScreen(new MenuScreen(this));
    }
}
