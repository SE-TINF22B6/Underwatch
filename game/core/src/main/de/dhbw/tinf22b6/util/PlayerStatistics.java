package de.dhbw.tinf22b6.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.weapon.Ak;
import de.dhbw.tinf22b6.weapon.M4;
import de.dhbw.tinf22b6.weapon.Shotgun;
import de.dhbw.tinf22b6.weapon.Sniper;
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
    private int initialHP;
    private boolean won;
    private Vector2 startLocation;
    private boolean canSwitchWeapon;
    private float damageModifier;
    // singleton: prevent instantiation from other classes
    private PlayerStatistics() {}

    public void init() {
        this.weapons = new ArrayList<>();

        weapons.add(new Ak());
        this.canSwitchWeapon = true;
        this.hp = 200;
        this.initialHP = hp;
        this.won = false;
        this.currentWeaponIndex = 0;
        this.gameTime = 0;
        this.coins = 0;
        this.enemies_kills = 0;
        this.damageModifier = 1f;
        Gdx.app.debug(TAG, "PlayerStatistics initialized " + instance.toString());
    }

    public int hp() {
        return this.hp;
    }

    public void hitHP(int damage) {
        this.hp -= damage;
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
        return Math.max(coins * 5 + enemies_kills * 10 + (hasWon() ? 100 : 0) - (int) (gameTime / 60), 0);
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
        if (this.weapons.size() == 1) { // Player only has the AK
            this.weapons.add(new M4());
            this.currentWeaponIndex = weapons.size() - 1;
        } else if (this.weapons.size() == 2) { // Player has AK and M4
            this.weapons.add(new Shotgun());
            this.currentWeaponIndex = weapons.size() - 1;
        } else if (this.weapons.size() == 3) { // Player has AK and M4 and Shotgun
            this.weapons.add(new Sniper());
            this.currentWeaponIndex = weapons.size() - 1;
        } else {
            reloadWeapons();
        }
    }

    public void reloadWeapons() {
        for (Weapon weapon : weapons) {
            weapon.reload();
        }
    }

    public void hpBox(boolean big) {
        this.hp = big ? this.initialHP : Math.max(this.hp + (int) (this.initialHP * 0.20), this.initialHP);
    }

    public void setWon() {
        this.won = true;
    }

    public boolean hasWon() {
        return won;
    }

    public Vector2 getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Vector2 startLocation) {
        this.startLocation = startLocation;
    }
    public void addDamageModifier(float damageModifier) {
        this.damageModifier += damageModifier;
    }
    public float getDamageModifier() {
        return damageModifier;
    }
}
