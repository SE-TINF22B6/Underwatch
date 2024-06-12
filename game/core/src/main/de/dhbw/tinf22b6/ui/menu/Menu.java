package de.dhbw.tinf22b6.ui.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import de.dhbw.tinf22b6.screen.GameScreen;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.MusicPlayer;
import de.dhbw.tinf22b6.util.PlayerStatistics;
import de.dhbw.tinf22b6.world.WorldType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Menu extends Stage {
    public Menu(StageManager stageManager) {
        super();
        Skin skin = Assets.instance.getSkin();
        Game game = stageManager.getGame();

        Table table = new Table(skin);
        table.setFillParent(true);
        this.addActor(table);

        Label dadJoke = new Label(getDadJoke(), skin);
        dadJoke.setFontScale(0.5f);
        table.add(dadJoke).row();

        Button soundOn = new Button(new SpriteDrawable(new Sprite(Assets.instance.getSprite("volume_on"))));
        Button soundOff = new Button(new SpriteDrawable(new Sprite(Assets.instance.getSprite("volume_mute"))));

        Button btnMute = new Button(skin);
        btnMute.add(Gdx.app.getPreferences("Controls").getBoolean("muteMusic") ? soundOff : soundOn);
        btnMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Preferences preferences = Gdx.app.getPreferences("Controls");
                preferences.putBoolean("muteMusic", !preferences.getBoolean("muteMusic"));
                preferences.flush();
                MusicPlayer.instance.setVolume(preferences.getBoolean("muteMusic") ? 0 : preferences.getFloat("music"));
                btnMute.clearChildren();
                btnMute.add(Gdx.app.getPreferences("Controls").getBoolean("muteMusic") ? soundOff : soundOn);
                super.clicked(event, x, y);
            }
        });
        btnMute.setSize(50, 50);
        btnMute.setPosition(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 60);
        this.addActor(btnMute);

        Button btnStart = new Button(skin);
        btnStart.add(new Label("Start Game", skin));
        btnStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // initialize player statistics
                PlayerStatistics.instance.init();
                game.setScreen(new GameScreen(game, WorldType.LEVEL4.getMap()));
                Gdx.audio
                        .newSound(Gdx.files.internal("sfx/LevelUp.mp3"))
                        .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.audio
                        .newSound(Gdx.files.internal("sfx/button_hover.mp3"))
                        .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
                super.enter(event, x, y, pointer, fromActor);
            }
        });
        table.add(btnStart).pad(20);
        table.row();

        Button btnSettings = new Button(skin);
        btnSettings.add(new Label("Settings", skin));
        btnSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.setStage(new Settings(stageManager));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.audio
                        .newSound(Gdx.files.internal("sfx/button_hover.mp3"))
                        .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
                super.enter(event, x, y, pointer, fromActor);
            }
        });
        table.add(btnSettings).pad(20);
        table.row();

        Button btnQuit = new Button(skin);
        btnQuit.add(new Label("Quit Game", skin));
        btnQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        btnQuit.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.audio
                        .newSound(Gdx.files.internal("sfx/button_hover.mp3"))
                        .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
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
