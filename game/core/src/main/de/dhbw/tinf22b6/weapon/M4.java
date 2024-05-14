package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class M4 extends Weapon {
    public M4(World world) {
        super("m4", 30, 0.05f, world);
        this.shootingAnimation = new Animation<>(0.001f, Assets.instance.getAnimationAtlasRegion("m4"));
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
                            new Vector2(pos.x + 15 / 2f, pos.y + 5), world, angle, Constants.WEAPON_BIT));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            })
                    .start();
        }
        return true;
    }
}
