package de.dhbw.tinf22b6.ui.ingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.dhbw.tinf22b6.screen.MenuScreen;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.PlayerStatistics;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GameWonStage extends Stage {
    private static final String TAG = GameWonStage.class.getName();
    private final Label lblScore;

    public GameWonStage(Game game) {
        Skin skin = Assets.instance.getSkin();

        Label lblGameOver = new Label("You Won!", skin);
        lblScore = new Label("Score: " + PlayerStatistics.instance.getScore(), skin);
        Label lblName = new Label("Enter Name:", skin);

        TextField textField = new TextField("UnderwatchGrinder", skin);
        textField.setTextFieldListener((tf, c) -> {
            if (c == '\n') {
                if (textField.getText().trim().isEmpty()) Gdx.app.debug(TAG, "Empty Name, won't submit any Score!");
                else uploadScore(tf.getText().trim());
                game.setScreen(new MenuScreen(game));
            }
        });

        Button btnSaveQuit = new Button(new Label("Send Score and Quit", skin), skin);
        btnSaveQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (textField.getText().trim().isEmpty()) Gdx.app.debug(TAG, "Empty Name, won't submit any Score!");
                else uploadScore(textField.getText());
                game.setScreen(new MenuScreen(game));
            }
        });

        Button btnQuit = new Button(new Label("Quit without submitting!", skin), skin);
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
        table.add(lblScore).padTop(50);
        table.row();
        table.add(lblName).padTop(50);
        table.row().expandX().fill().pad(0, 50, 0, 50);
        table.add(textField);
        table.row();
        table.add(btnSaveQuit).padTop(20);
        table.row();
        table.add(btnQuit).padTop(20);
        table.pad(8);
        this.addActor(table);
    }

    public void update() {
        lblScore.setText("Score: " + PlayerStatistics.instance.getScore());
    }

    private void uploadScore(String name) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://underwatch.freemine.de/api/scores"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        """
                                {
                                  "playerName": "%s",
                                  "score": %d,
                                  "coins": %d,
                                  "kills": %d,
                                  "damageDealt": %d,
                                  "dps": %d,
                                  "game_time": %d,
                                  "timestamp": "%s"
                                }
                                """
                                .formatted(
                                        name,
                                        PlayerStatistics.instance.getScore(),
                                        PlayerStatistics.instance.coins(),
                                        PlayerStatistics.instance.enemies_killed(),
                                        0,
                                        0,
                                        (int) PlayerStatistics.instance.getGameTime(),
                                        ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT))))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(s -> Gdx.app.debug(TAG, s.toString()))
                .join();
    }
}
