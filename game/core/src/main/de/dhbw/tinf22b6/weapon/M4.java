package de.dhbw.tinf22b6.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.bullet.Bullet;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class M4 extends Weapon {
  public M4() {
    super("m4", 30, 0.05f, 40);
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
          EntitySystem.instance.add(
              new Bullet(new Vector2(pos.x + 15 / 2f, pos.y + 5), angle, this.damage, Constants.WEAPON_BIT));
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      })
          .start();
    }
    return true;
  }
}
