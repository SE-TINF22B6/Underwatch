package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.bullet.Bullet;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class Bow extends Weapon {
    public Bow() {
        super("bow", 20, 1, 100);
    }

    @Override
    public boolean shoot() {
        if (super.shoot()) {
            new Thread(() -> {
                        try {
                            Thread.sleep((long) (shootingAnimation.getAnimationDuration() * 1000));
                            float angle = EntitySystem.instance.getPlayer().getAngle();
                            Vector2 pos = EntitySystem.instance.getPlayer().getPos();
                            EntitySystem.instance.add(new Bullet(
                                    new Vector2(pos.x + 15 / 2f, pos.y + 5), angle, this.damage, Constants.WEAPON_BIT));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .start();
        }
        return true;
    }
}
