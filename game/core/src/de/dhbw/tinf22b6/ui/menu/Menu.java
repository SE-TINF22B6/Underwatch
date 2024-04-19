package de.dhbw.tinf22b6.ui.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Menu extends Stage {
    public Menu(StageManager stageManager, Music music) {
        super();
        Skin skin = Assets.instance.getSkin();
        Game game = stageManager.getGame();

        Table table = new Table(skin);
        table.setFillParent(true);
        this.addActor(table);

        Label dadJoke = new Label(getDadJoke(), skin);
        dadJoke.setFontScale(0.5f);
        table.add(dadJoke).row();

        Button btnMute = new Button(skin);
        btnMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.setVolume(music.getVolume() != 0 ? 0 : 1);
                super.clicked(event, x, y);
            }
        });
        btnMute.setSize(50, 50);
        btnMute.setPosition(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 60);
        this.addActor(btnMute);

        Button btnStart = new Button(skin);
        btnStart.add(new Label("Start Game", skin));
        btnStart.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new GameScreen(game, WorldType.LEVEL2.getMap()));
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

                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Gdx.audio.newSound(Gdx.files.internal("sfx/button_hover.mp3")).play();
                        super.enter(event, x, y, pointer, fromActor);
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
        btnQuit.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.audio.newSound(Gdx.files.internal("sfx/button_hover.mp3")).play();
                super.enter(event, x, y, pointer, fromActor);
            }
        });
        table.add(btnQuit).pad(20);
        table.row();
    }

    private String getDadJoke() {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://icanhazdadjoke.com/"))
                .header("Accept", "text/plain")
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(stringBuilder::append)
                .join();
        return stringBuilder.toString();
    }
}
