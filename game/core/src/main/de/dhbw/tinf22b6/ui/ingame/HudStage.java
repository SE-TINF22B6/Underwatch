package de.dhbw.tinf22b6.ui.ingame;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.PlayerStatistics;
import java.util.concurrent.TimeUnit;

public class HudStage extends Stage {
    private final Label labelCoins;
    private final Label labelAmmo;
    private final Label labelWeapon;
    private final Label labelElapsedTime;
    private final Texture[] hpBar;
    private final Texture[] staminaBar;

    public HudStage() {
        Skin skin = Assets.instance.getSkin();
        labelCoins = new Label("Coins:", skin);
        labelAmmo = new Label("00", skin);
        labelWeapon = new Label("Ak", skin);
        labelElapsedTime = new Label("00:00:00", skin);
        hpBar = new Texture[2];
        staminaBar = new Texture[2];

        // Generate PixMap
        Pixmap pixmap = new Pixmap((int) (getWidth() / 10), 10, Pixmap.Format.RGBA8888);

        // HP Bar
        pixmap.setColor(1, 0, 0, 1);
        pixmap.fill();
        hpBar[0] = new Texture(pixmap);

        // Stamina Bar
        pixmap.setColor(0, 1, 0, 1);
        pixmap.fill();
        staminaBar[0] = new Texture(pixmap);

        // Bar Background
        pixmap.setColor(0.2f, 0.2f, 0.2f, 1);
        pixmap.fill();
        hpBar[1] = new Texture(pixmap);
        staminaBar[1] = new Texture(pixmap);

        this.addActor(labelCoins);
        this.addActor(labelAmmo);
        this.addActor(labelWeapon);
        this.addActor(labelElapsedTime);

        labelWeapon.setPosition(
                getWidth() - labelAmmo.getWidth() - labelWeapon.getWidth() - labelAmmo.getWidth() / 2, 0);
        labelAmmo.setPosition(getWidth() - labelAmmo.getWidth(), 0);
        labelCoins.setPosition(0, getHeight() - labelCoins.getHeight());
        labelElapsedTime.setPosition(
                getWidth() - labelElapsedTime.getWidth(), getHeight() - labelElapsedTime.getHeight());
    }

    // TODO: fix hardcoded max values when drawing ressource bars
    @Override
    public void draw() {
        Batch batch = getBatch();
        batch.begin();
        // draw hp bar
        batch.draw(hpBar[1], 20, 40);
        batch.draw(
                hpBar[0],
                20,
                40,
                (float) (PlayerStatistics.instance.hp() * hpBar[0].getWidth()) / 200,
                hpBar[0].getHeight());

        // draw stamina bar
        batch.draw(staminaBar[1], 20, 20);
        batch.draw(
                staminaBar[0],
                20,
                20,
                PlayerStatistics.instance.getStamina() * staminaBar[0].getWidth() / 100,
                staminaBar[0].getHeight());
        batch.end();
        labelCoins.setText("Coins: " + PlayerStatistics.instance.coins());
        labelAmmo.setText(PlayerStatistics.instance.getCurrentWeapon().getAmmo());
        labelWeapon.setText(
                PlayerStatistics.instance.getCurrentWeapon().getClass().getSimpleName());
        long time = (long) PlayerStatistics.instance.getGameTime();
        labelElapsedTime.setText(String.format(
                "%2d:%2d:%2d", TimeUnit.SECONDS.toHours(time), TimeUnit.SECONDS.toMinutes(time), time % 60));
        super.draw();
    }
}
