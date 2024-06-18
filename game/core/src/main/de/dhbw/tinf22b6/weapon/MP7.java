package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.bullet.MPBullet;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class MP7 extends Weapon {
    public MP7() {
        super("mp7", 40, 0.05f, 40);
        this.shootingAnimation = new Animation<>(0.005f, Assets.instance.getAnimationAtlasRegion("mp7"));
    }

    @Override
    public boolean shoot() {
        if (super.shoot()) {
            new Thread(() -> {
                        try {
                            Thread.sleep((long) (shootingAnimation.getAnimationDuration() * 1000));
                            float angle = EntitySystem.instance.getPlayer().getAngle();
                            Vector2 pos = EntitySystem.instance.getPlayer().getPos();
                            EntitySystem.instance.add(new MPBullet(
                                    new Vector2(pos.x + 15 / 2f, pos.y + 5),
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
