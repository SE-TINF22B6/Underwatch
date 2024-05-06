package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class CrossBow extends Weapon {
    public CrossBow(World world) {
        super("bow", 20, 1, world);
    }

    @Override
    public boolean shoot() {
        if (super.shoot()) {
            new Thread(() -> {
                        try {
                            Thread.sleep((long) (shootingAnimation.getAnimationDuration() * 1000));
                            for (int i = -5; i <= 5; i += 2) {
                                float angle = EntitySystem.instance.getPlayer().getAngle();
                                angle += i;
                                Vector2 pos = EntitySystem.instance.getPlayer().getPos();
                                EntitySystem.instance.add(new Bullet(
                                        new Vector2(pos.x + 15 / 2f, pos.y + 5), world, angle, Constants.WEAPON_BIT));
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .start();
        }
        return true;
    }
}
