package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Assets;

public abstract class Weapon {
    private static final String TAG = Weapon.class.getName();
    protected TextureAtlas.AtlasRegion idleAnimation;
    protected Animation<TextureAtlas.AtlasRegion> shootingAnimation;
    protected int ammo;
    protected float remainingWeaponCooldown;
    protected float weaponCooldown;
    protected float weaponStateTime;
    protected boolean isShooting;
    protected Sound sound;
    protected World world;

    public Weapon(String regionName, int ammo, float weaponCooldown, World world) {
        this.idleAnimation = Assets.instance.getSprite("idle" + regionName);
        this.shootingAnimation = new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(regionName));
        this.ammo = ammo;
        this.weaponCooldown = weaponCooldown;
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sfx/" + regionName + ".mp3"));
        this.world = world;
    }

    public boolean shoot() {
        if (this.ammo <= 0 || isShooting || remainingWeaponCooldown > 0) {
            Gdx.app.debug(TAG, "Can't shoot right now:" + "{ ammo: " + ammo + ", isShooting: " + isShooting + ", weaponCooldown: " + remainingWeaponCooldown + "}");
            // TODO play empty magazine sound
            return false;
        }
        this.isShooting = true;
        new Thread(() -> {
            try {
                Thread.sleep((long) (shootingAnimation.getAnimationDuration() * 1000));
                sound.play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
                this.remainingWeaponCooldown = this.weaponCooldown;
                this.weaponStateTime = 0;
                this.isShooting = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        this.ammo--;
        return true;
    }

    public void updateRemainingCoolDown(float delta) {
        if (isShooting) {
            this.weaponStateTime += delta;
            return;
        }
        this.remainingWeaponCooldown -= delta;
        if (this.remainingWeaponCooldown < 0) {
            this.remainingWeaponCooldown = 0;
            this.weaponStateTime = 0;
        }
    }

    public TextureAtlas.AtlasRegion getRegion() {
        if (!isShooting) {
            return idleAnimation;
        }
        return shootingAnimation.getKeyFrame(weaponStateTime, true);
    }
}