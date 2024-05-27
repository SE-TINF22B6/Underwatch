package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.bullet.Bullet;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class Shotgun extends Weapon {
    public Shotgun() {
        super("shotgun", 25, 0.2f, 50);
        this.shootingAnimation = new Animation<>(0.06f, Assets.instance.getAnimationAtlasRegion("shotgun"));
    }

    @Override
    public boolean shoot() {
        if (super.shoot()) {
            new Thread(() -> {
                        for (int i = -5; i <= 5; i += 2) {
                            float angle = EntitySystem.instance.getPlayer().getAngle();
                            angle += i;
                            Vector2 pos = EntitySystem.instance.getPlayer().getPos();
                            EntitySystem.instance.add(new Bullet(
                                    new Vector2(pos.x + 15 / 2f, pos.y + 5), angle, this.damage, Constants.WEAPON_BIT));
                        }
                    })
                    .start();
        }
        return true;
    }
}
