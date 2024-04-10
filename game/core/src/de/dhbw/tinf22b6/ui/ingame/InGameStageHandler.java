package de.dhbw.tinf22b6.ui.ingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.screen.GameScreen;

import java.util.HashMap;
import java.util.Map;

public class InGameStageHandler {
    private final Player player;
    private final GameScreen gameScreen;
    private final Map<String, Stage> stages;
    private Stage currentStage;

    public InGameStageHandler(Game game, GameScreen gameScreen, Player player) {
        this.stages = new HashMap<>();
        stages.put("HUD", new HudStage(player));
        stages.put("Pause", new PauseStage(gameScreen, game));
        stages.put("GameOver", new GameOverStage(player, game));
        this.gameScreen = gameScreen;
        this.player = player;
    }

    public void drawAndAct() {
        currentStage.act(Gdx.graphics.getDeltaTime());
        currentStage.draw();
    }

    public void update() {
        if (player.getHealth() == 0) {
            gameScreen.setPaused();
            changeStage("GameOver");
            Gdx.input.setInputProcessor(currentStage);
            return;
        }
        if (gameScreen.isPaused()) {
            changeStage("Pause");
            Gdx.input.setInputProcessor(currentStage);
            return;
        }
        changeStage("HUD");
    }

    private void changeStage(String stageName) {
        switch (stageName) {
            case "HUD" -> currentStage = stages.get("HUD");
            case "Pause" -> currentStage = stages.get("Pause");
            case "GameOver" -> {
                currentStage = stages.get("GameOver");
                ((GameOverStage) currentStage).update();
            }
        }
    }
}
