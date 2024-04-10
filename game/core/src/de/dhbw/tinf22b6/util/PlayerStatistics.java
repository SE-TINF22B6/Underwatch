package de.dhbw.tinf22b6.util;

public class PlayerStatistics {
    private int hp;
    private int coins;
    private int enemies_kills;

    public PlayerStatistics(int hp) {
        this.hp = hp;
    }

    public int hp() {
        return this.hp;
    }

    public void hitHP() {
        this.hp = this.hp -1;
    }

    public int coins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int enemies_killed() {
        return enemies_kills;
    }

    public void setEnemies_kills() {
        this.enemies_kills++;
    }
}
