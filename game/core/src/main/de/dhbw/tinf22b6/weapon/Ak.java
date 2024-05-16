package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.bullet.Bullet;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class Ak extends Weapon {
    public Ak(World world) {
        super("ak", 25, 0.2f, world);
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
