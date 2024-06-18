package de.dhbw.tinf22b6.gameobject.bullet;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

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
        EntitySystem.instance.addBulletToQueue(this);
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
        if (body != null) {
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
    }

    public int getDamage() {
        return damage;
    }
}
