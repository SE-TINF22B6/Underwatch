package de.dhbw.tinf22b6.util;

import com.badlogic.gdx.Gdx;
import de.dhbw.tinf22b6.weapon.Ak;
import de.dhbw.tinf22b6.weapon.M4;
import de.dhbw.tinf22b6.weapon.Weapon;
import java.util.ArrayList;
import java.util.List;

public class PlayerStatistics {
    public static final String TAG = PlayerStatistics.class.getName();
    public static final PlayerStatistics instance = new PlayerStatistics();
    private int hp;
    private int coins;
    private int enemies_kills;
    private float gameTime;
    private List<Weapon> weapons;
    private int currentWeaponIndex;

    // singleton: prevent instantiation from other classes
    private PlayerStatistics() {}

    public void init() {
        this.weapons = new ArrayList<>();
        weapons.add(new Ak());
        this.canSwitchWeapon = true;
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

    public void enemyKilled() {
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

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public Weapon getCurrentWeapon() {
        return weapons.get(currentWeaponIndex);
    }

    private boolean canSwitchWeapon;

    public void cycleWeapon(boolean direction) {
        List<Weapon> inventory = PlayerStatistics.instance.getWeapons();
        if (canSwitchWeapon && inventory.size() > 1) {
            canSwitchWeapon = false;
            currentWeaponIndex =
                    Math.floorMod(direction ? currentWeaponIndex - 1 : currentWeaponIndex + 1, inventory.size());
            Gdx.audio
                    .newSound(Gdx.files.internal("sfx/change_weapon.mp3"))
                    .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
        }
        new Thread(() -> {
                    try {
                        Thread.sleep(500);
                        canSwitchWeapon = true;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .start();
    }

    public void pickupWeapon() {
        this.weapons.add(new M4());
        this.currentWeaponIndex = weapons.size() - 1;
    }
}
