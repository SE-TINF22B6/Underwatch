package de.dhbw.tinf22b6.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.dhbw.tinf22b6.screen.GameScreen;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.world.WorldType;

public class Menu extends Stage {
    public Menu(Game game) {
        super();
        Skin skin = Assets.instance.getSkin();

        Table table = new Table(skin);
        table.setFillParent(true);
        this.addActor(table);

        Button btnStart = new Button(skin);
        btnStart.add(new Label("Start Game", skin));
        btnStart.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new GameScreen(game, WorldType.LEVEL1.getMap()));
                    }
                }
        );
        table.add(btnStart).pad(20);
        table.row();

        Button btnSettings = new Button(skin);
        btnSettings.add(new Label("Settings", skin));
        table.add(btnSettings).pad(20);
        table.row();

        Button btnQuit = new Button(skin);
        btnQuit.add(new Label("Quit Game", skin));
        btnQuit.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Gdx.app.exit();
                    }
                }
        );
        table.add(btnQuit).pad(20);
        table.row();
    }
}
