package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

public class Bow extends Weapon {
    private static final String TAG = Bow.class.getName();

    public Bow(World world) {
        super("bow", 20, 1, world);
    }

    @Override
    public boolean shoot() {
        if (super.shoot()) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (shootingAnimation.getAnimationDuration() * 1000));
                    for (int i = -5; i <= 5; i+=2) {
                        int angle = EntitySystem.instance.getPlayer().getAngle();
                        angle += i;
                        Vector2 pos = EntitySystem.instance.getPlayer().getPos();
                        Vector2 dir = new Vector2(
                                (pos.x) * cosDeg(angle),
                                (pos.y) * sinDeg(angle));
                        Gdx.app.debug(TAG, "Angle: " + angle);
                        EntitySystem.instance.add(new Bullet(new Vector2(pos.x + 15 / 2f, pos.y + 5), world, angle, Constants.WEAPON_BIT));
                    }

                    Thread.sleep(1000);
                    int angle = EntitySystem.instance.getPlayer().getAngle();
                    Vector2 pos = EntitySystem.instance.getPlayer().getPos();
                    Gdx.app.debug(TAG, "Direct angle: " + angle);
                    EntitySystem.instance.add(new Bullet(new Vector2(pos.x + 15 / 2f, pos.y + 5), world, angle, Constants.WEAPON_BIT));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        return true;
    }
}
