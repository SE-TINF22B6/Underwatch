package de.dhbw.tinf22b6.ui.ingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.dhbw.tinf22b6.screen.GameScreen;
import de.dhbw.tinf22b6.screen.MenuScreen;
import de.dhbw.tinf22b6.util.Assets;

public class PauseStage extends Stage {
    public PauseStage(GameScreen gameScreen, Game game) {
        Skin skin = Assets.instance.getSkin();

        Button btnContinue = new Button(new Label("Continue", skin), skin);
        btnContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                gameScreen.unpause();
            }
        });

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
        table.add(btnContinue);
        table.row();
        table.add(btnQuit);
        table.pad(8);
        this.addActor(table);
    }
}
