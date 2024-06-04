package de.dhbw.tinf22b6.gameobject.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class FireBall extends Bullet {
  private Vector2 perpendicular;

  public FireBall(Vector2 position, float angle, int damage, short mask) {
    super("fireAnimation3", position, angle, damage, mask);
    this.currentAnimation.setFrameDuration(0.1f);
    this.speed = 0.5f;
    this.perpendicular = new Vector2(MathUtils.cosDeg(angle), -MathUtils.sinDeg(angle));
  }

  @Override
  public void render(Batch batch) {
    float rotation = angle + 180;
    batch.draw(
        currentAnimation.getKeyFrame(stateTime, true),
        pos.x,
        pos.y,
        width / 2,
        height / 2,
        width,
        height,
        1,
        1,
        rotation);
    for (int i = 0; i < 360; i += 5) {
      batch.draw(
          currentAnimation.getKeyFrame(stateTime, true),
          pos.x + perpendicular.x * 5 * MathUtils.sinDeg((stateTime + i)*100),
          pos.y + perpendicular.y * 5 * MathUtils.cosDeg((stateTime + i)*100),
          width / 2,
          height / 2,
          width,
          height,
          1,
          1,
          rotation);
      batch.draw(
          currentAnimation.getKeyFrame(stateTime, true),
          pos.x + perpendicular.x * 5 * -MathUtils.sinDeg((stateTime + i)*50),
          pos.y + perpendicular.y * 5 * MathUtils.cosDeg((stateTime + i)*50),
          width / 2,
          height / 2,
          width,
          height,
          1,
          1,
          rotation);
    }
  }
}
