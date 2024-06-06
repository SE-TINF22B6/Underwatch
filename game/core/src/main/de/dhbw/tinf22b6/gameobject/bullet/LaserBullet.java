package de.dhbw.tinf22b6.gameobject.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.util.Assets;

public class LaserBullet extends Bullet {
  private ParticleEffect effect;

  public LaserBullet(Vector2 position, float angle, int damage, short mask) {
    super("fireAnimation3", position, angle, damage, mask);
    this.currentAnimation.setFrameDuration(1f);
    this.speed = 0.5f;
    effect = new ParticleEffect();
    effect.load(Gdx.files.internal("particles/trail.p"), Assets.instance.getAtlas());

    effect.start();
    effect.scaleEffect(0.2f);
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
    effect.setPosition(pos.x + width / 2, pos.y + height / 2);
    effect.draw(batch, Gdx.graphics.getDeltaTime());
  }

  @Override
  public void setRemove(boolean remove) {
    super.setRemove(remove);
    effect.dispose();
  }
}
