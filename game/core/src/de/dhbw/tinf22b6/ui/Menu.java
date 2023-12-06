package de.dhbw.tinf22b6.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    public Menu(StageManager stageManager) {
        super();
        Skin skin = Assets.instance.getSkin();
        Game game = stageManager.getGame();

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
                        Gdx.audio.newSound(Gdx.files.internal("sfx/LevelUp.mp3")).play();

                    }

                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Gdx.audio.newSound(Gdx.files.internal("sfx/button_hover.mp3")).play();
                        super.enter(event, x, y, pointer, fromActor);
                    }
                }
        );
        table.add(btnStart).pad(20);
        table.row();

        Button btnSettings = new Button(skin);
        btnSettings.add(new Label("Settings", skin));
        btnSettings.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        stageManager.setStage(new Settings(stageManager));
                    }
                }
        );
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
        btnQuit.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.audio.newSound(Gdx.files.internal("sfx/button_hover.mp3")).play();
                super.enter(event, x, y, pointer, fromActor);
            }
        });
        table.add(btnQuit).pad(20);
        table.row();
    }
}
