package de.dhbw.tinf22b6.util;

import com.badlogic.gdx.Gdx;

public class PlayerStatistics {
    public static final String TAG = PlayerStatistics.class.getName();
    public static final PlayerStatistics instance = new PlayerStatistics();
    private int hp;
    private int coins;
    private int enemies_kills;
    private float gameTime;

    // singleton: prevent instantiation from other classes
    private PlayerStatistics() {
    }

    public void init() {
        this.hp = 5;
        this.coins = 0;
        this.enemies_kills = 0;
        Gdx.app.debug(TAG, "PlayerStatistics initialized " + instance.toString());
    }

    public int hp() {
        return this.hp;
    }

    public void hitHP() {
        this.hp = this.hp - 1;
    }

    public int coins() {
        return coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void removeCoins(int coins) {
        this.coins -= coins;
    }

    public int enemies_killed() {
        return enemies_kills;
    }

    public void setEnemies_kills() {
        this.enemies_kills++;
    }

    public int getScore() {
        return coins * 2 + enemies_kills * 5;
    }

    public float getGameTime() {
        return gameTime;
    }

    public void incrementGameTime(float deltaTime) {
        this.gameTime += deltaTime;
    }
}
