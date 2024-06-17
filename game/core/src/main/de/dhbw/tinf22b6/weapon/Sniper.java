package de.dhbw.tinf22b6.weapon;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.bullet.PlayerBullet;
import de.dhbw.tinf22b6.gameobject.bullet.SniperBullet;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class Sniper extends Weapon {
    public Sniper() {
        super("sniper", 10, 2.0f, 200);
        this.shootingAnimation = new Animation<>(0.001f, Assets.instance.getAnimationAtlasRegion("sniper"));
    }

    @Override
    public boolean shoot() {
        if (super.shoot()) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (shootingAnimation.getAnimationDuration() * 1000));
                    float angle = EntitySystem.instance.getPlayer().getAngle();
                    Vector2 pos = EntitySystem.instance.getPlayer().getPos();
                    int r = 30;
                    EntitySystem.instance.add(new SniperBullet(
                            new Vector2(pos.x + r * cosDeg(angle), pos.y + 5 + r * sinDeg(angle)),
                            angle,
                            this.getDamage(),
                            Constants.WEAPON_BIT));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            })
                    .start();
        }
        return true;
    }
}
