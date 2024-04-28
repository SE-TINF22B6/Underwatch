package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class Weapon {
    private static final String TAG = Weapon.class.getName();
    protected TextureAtlas.AtlasRegion idleAnimation;
    protected Animation<TextureAtlas.AtlasRegion> shootingAnimation;
    protected int ammo;
    protected float remainingWeaponCooldown;
    protected float weaponCooldown;
    protected float weaponStateTime;
    protected boolean isShooting;

    public Weapon(String regionName, int ammo, float weaponCooldown) {
        this.idleAnimation = Assets.instance.getSprite("Just_arrow");
        this.shootingAnimation = new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion("arrow"));
        this.ammo = ammo;
        this.weaponCooldown = weaponCooldown;
    }

    public void shoot() {
        //EntitySystem.instance.add(new Bullet(new Vector2(player.getPos().x + TILE_SIZE / 2f, player.getPos().y), world, reducedDimension.setLength(1), Constants.WEAPON_BIT));
        if (this.ammo <= 0 || isShooting || remainingWeaponCooldown > 0) {
            Gdx.app.debug(TAG, "Can't shoot right now:" + "{ ammo: " + ammo + ", isShooting: " + isShooting + ", weaponCooldown: " + remainingWeaponCooldown + "}");
            // TODO play empty magazine sound
            return;
        }
        this.isShooting = true;
        Gdx.audio.newSound(Gdx.files.internal("sfx/gun-shot.mp3")).play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
        new Thread(() -> {
            try {
                Thread.sleep((long) (shootingAnimation.getAnimationDuration() * 1000));
                this.remainingWeaponCooldown = this.weaponCooldown;
                this.weaponStateTime = 0;
                this.isShooting = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        this.ammo--;
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
