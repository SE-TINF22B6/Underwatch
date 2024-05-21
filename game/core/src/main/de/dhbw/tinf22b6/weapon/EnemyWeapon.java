package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.bullet.Bullet;
import de.dhbw.tinf22b6.gameobject.enemy.Enemy;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class EnemyWeapon extends Weapon {
    private final Enemy enemy;

    public EnemyWeapon(Enemy enemy) {
        super("ak", 25, 1);
        this.enemy = enemy;
        this.shootingAnimation = new Animation<>(0.01f, Assets.instance.getAnimationAtlasRegion("ak"));
    }

    @Override
    public boolean shoot() {
        if (super.shoot()) {
            new Thread(() -> {
                        try {
                            Thread.sleep((long) (shootingAnimation.getAnimationDuration() * 1000));
                            float angle = enemy.getPosition()
                                            .sub(EntitySystem.instance
                                                    .getPlayer()
                                                    .getPos())
                                            .angleDeg()
                                    + 180;
                            Vector2 pos = enemy.getPos();
                            EntitySystem.instance.add(new Bullet(
                                    new Vector2(pos.x + 15 / 2f, pos.y + 5), angle, Constants.WEAPON_ENEMY_BIT));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .start();
        }
        return true;
    }
}
