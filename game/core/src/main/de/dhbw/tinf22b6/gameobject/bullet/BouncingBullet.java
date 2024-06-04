package de.dhbw.tinf22b6.gameobject.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class BouncingBullet extends Bullet {
  private int bounces;
  private boolean inWall;

  public BouncingBullet(Vector2 position, float angle, int damage, short mask) {
    super("bullet7x13", position, angle, damage, mask);
    this.bounces = 3;
    this.inWall = false;
  }

  public void bounce() {
    if (!inWall) {
      inWall = true;
      Gdx.app.debug("BouncingBullet", "BouncingBullet bounce");
      if (bounces <= 0) {
        this.setRemove(true);
      }

      bounces--;
    }
  }

  public void leaveWall() {
    this.inWall = false;
    Gdx.app.debug("BouncingBullet", "BouncingBullet leaveWall");
  }
}
