package de.dhbw.tinf22b6.gameobject.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.world.Box2dWorld;
import de.dhbw.tinf22b6.world.WorldParser;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

public abstract class Bullet extends GameObject {
  protected final int damage;
  protected float speed;
  protected boolean active;
  protected float angle;
  protected float r;
  // be careful when adjusting this parameter, as this is not the range in tiles but rather a
  // counting of delta times
  // speed until the "range" is reached
  // TODO: this needs to be refactored to actually represent a value in tiles
  protected float range = 2.3f;

  public Bullet(String region, Vector2 position, float angle, int damage, short mask) {
    super(region, new Vector2(position.x / Constants.TILE_SIZE, position.y / Constants.TILE_SIZE), mask);
    this.angle = angle;
    this.damage = damage;
    active = true;
    speed = 3;
    width = 3;
    height = 6;
    // This is arcane wizardry! It might fix the crash which happened at random when
    // the player gets into the range of multiple enemies at the same time, and they start shooting
    // in the same tick - effectively locking up the world (inserting body) and causing a data race
    // this "fix" works for me and needs throughout testing as I suspect it breaking something further
    // when the #render() call is done and the body is not created yet.
    new Thread(() -> {
      World world = Box2dWorld.instance.getWorld();
      boolean isLocked;
      do {
        isLocked = world.isLocked();
      } while (isLocked);
      body = world.createBody(WorldParser.getDynamicBodyDef(pos.x + width / 2, pos.y + height / 2));
      PolygonShape polygonShape = new PolygonShape();
      polygonShape.setAsBox(3 - 2, 3 - 2);

      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.shape = polygonShape;
      fixtureDef.filter.categoryBits = collisionMask;
      fixtureDef.isSensor = true;

      body.createFixture(fixtureDef).setUserData(this);
      body.setBullet(true);
      polygonShape.dispose();
    }).start();
  }

  @Override
  public void render(Batch batch) {
    float rotation = 90 + angle + 180;
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
  }

  @Override
  public void tick(float delta) {
    super.tick(delta); // play the animation
    r += delta;

    if (active) {
      Vector2 tmp = new Vector2(pos);
      tmp.y = r * sinDeg(angle);
      tmp.x = r * cosDeg(angle);
      tmp.setLength(speed);

      pos.add(tmp);
      body.setTransform(pos.x + width / 2, pos.y + height / 2, 0);

      if (r * speed > range) {
        // Remove Bullet
        remove = true;
        active = false;
      }
    }
  }

  public int getDamage() {
    return damage;
  }
}
