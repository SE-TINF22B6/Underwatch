package de.dhbw.tinf22b6.weapon;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.bullet.Bullet;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class Ak extends Weapon {
    public Ak() {
        super("ak", 25, 0.2f);
        this.shootingAnimation = new Animation<>(0.01f, Assets.instance.getAnimationAtlasRegion("ak"));
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
                            EntitySystem.instance.add(new Bullet(
                                    new Vector2(pos.x + r * cosDeg(angle), pos.y + 5 + r * sinDeg(angle)),
                                    angle,
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
