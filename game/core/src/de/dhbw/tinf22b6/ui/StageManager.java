package de.dhbw.tinf22b6.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public class StageManager implements Disposable {
    private Stage currentStage;
    private final Game game;

    public StageManager(Game game) {
        this.game = game;
        this.setStage(new Menu(this));
    }

    public void drawAndAct() {
        currentStage.act(Gdx.graphics.getDeltaTime());
        currentStage.draw();
    }

    public void setStage(Stage stage) {
        this.currentStage = stage;
        Gdx.input.setInputProcessor(currentStage);
    }

    public void resize(int width, int height) {
        currentStage.getViewport().update(width, height, true);
    }

    public Game getGame(){
        return game;
    }

    @Override
    public void dispose() {
        this.currentStage.dispose();
    }
}


