package de.dhbw.tinf22b6.ui.ingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.screen.MenuScreen;
import de.dhbw.tinf22b6.util.Assets;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GameOverStage extends Stage {
    private static final String TAG = GameOverStage.class.getName();
    private final Label lblScore;
    private final Player player;

    public GameOverStage(Player player, Game game) {
        Skin skin = Assets.instance.getSkin();
        this.player = player;

        Label lblGameOver = new Label("Game Over", skin);
        lblScore = new Label("Score: " + player.getScore(), skin);
        Label lblName = new Label("Enter Name:", skin);
        TextArea textArea = new TextArea("UnderwatchGrinder", skin);

        Button btnQuit = new Button(new Label("Send Score and Quit", skin), skin);
        btnQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                uploadScore(textArea.getText(), player);
                game.setScreen(new MenuScreen(game));
            }
        });

        Table table = new Table(skin);
        table.setFillParent(true);
        table.add(lblGameOver);
        table.row();
        table.add(lblScore);
        table.row();
        table.add(lblName);
        table.row().expandX().fill().pad(0, 50, 0, 50);
        table.add(textArea);
        table.row();
        table.add(btnQuit);
        table.pad(8);
        this.addActor(table);
    }

    public void update() {
        lblScore.setText("Score: " + player.getScore());
    }

    private void uploadScore(String name, Player player) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://underwatch.freemine.de/api/scores"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                          "playerName": "%s",
                          "score": %d,
                          "coins": %d,
                          "kills": %d,
                          "damageDealt": %d,
                          "dps": %d,
                          "game_time": %d
                        }
                        """.formatted(name, player.getScore(), player.getCoins(), player.getKills(), 0, 0, 0)))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(s -> Gdx.app.debug(TAG, s.toString())).join();
    }
}
