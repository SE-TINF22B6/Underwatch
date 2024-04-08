package de.dhbw.tinf22b6.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import de.dhbw.tinf22b6.ui.menu.StageManager;

public class MenuScreen extends AbstractGameScreen {
    private static final String TAG = MenuScreen.class.getName();

    public MenuScreen(Game game) {
        super(game);
    }

    private StageManager stageManager;

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stageManager.drawAndAct();
    }

    @Override
    public void show() {
        stageManager = new StageManager(game);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {
        stageManager.resize(width, height);
    }

    public void dispose() {
        stageManager.dispose();
    }

}
