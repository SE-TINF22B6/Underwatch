package de.dhbw.tinf22b6.ui.ingame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.PlayerStatistics;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class HudStage extends Stage {
    private final Label labelHP;
    private final Label labelCoins;
    private final Label labelAmmo;
    private final Label labelWeapon;
    private final Label labelElapsedTime;

    public HudStage() {
        Skin skin = Assets.instance.getSkin();
        labelHP = new Label("HP:", skin);
        labelCoins = new Label("Coins:", skin);
        labelAmmo = new Label("00", skin);
        labelWeapon = new Label("Ak", skin);
        labelElapsedTime = new Label("00:00:0", skin);

        this.addActor(labelHP);
        this.addActor(labelCoins);
        this.addActor(labelAmmo);
        this.addActor(labelWeapon);
        this.addActor(labelElapsedTime);

        labelWeapon.setPosition(getWidth() - labelAmmo.getWidth() - labelWeapon.getWidth() - labelAmmo.getWidth()/2, 0);
        labelAmmo.setPosition(getWidth() - labelAmmo.getWidth(), 0);
        labelCoins.setPosition(0, getHeight() - labelCoins.getHeight());
        labelElapsedTime.setPosition(getWidth() - labelElapsedTime.getWidth(), getHeight() - labelElapsedTime.getHeight());
    }

    @Override
    public void draw() {
        labelHP.setText("HP: " + PlayerStatistics.instance.hp());
        labelCoins.setText("Coins: " + PlayerStatistics.instance.coins());
        labelAmmo.setText(PlayerStatistics.instance.getCurrentWeapon().getAmmo());
        labelWeapon.setText(PlayerStatistics.instance.getCurrentWeapon().getClass().getSimpleName());
        long time = (long) PlayerStatistics.instance.getGameTime();
        labelElapsedTime.setText(String.format("%2d:%2d:%2d", TimeUnit.SECONDS.toHours(time), TimeUnit.SECONDS.toMinutes(time), time % 60));
        super.draw();
    }
}
