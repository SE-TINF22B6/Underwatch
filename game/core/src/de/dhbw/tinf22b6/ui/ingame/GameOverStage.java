package de.dhbw.tinf22b6.ui.ingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.screen.MenuScreen;
import de.dhbw.tinf22b6.util.Assets;

public class GameOverStage extends Stage {
    private final Label lblScore;
    private final Player player;

    public GameOverStage(Player player, Game game) {
        Skin skin = Assets.instance.getSkin();
        this.player = player;

        Label lblGameOver = new Label("Game Over", skin);
        lblScore = new Label("Score: " + player.getScore(), skin);

        Button btnQuit = new Button(new Label("Quit", skin), skin);
        btnQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MenuScreen(game));
            }
        });

        Table table = new Table(skin);
        table.setFillParent(true);
        table.add(lblGameOver);
        table.row();
        table.add(lblScore);
        table.row();
        table.add(btnQuit);
        table.pad(8);
        this.addActor(table);
    }

    public void update() {
        lblScore.setText("Score: " + player.getScore());
    }
}
