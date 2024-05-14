package de.dhbw.tinf22b6.gameobject;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.world.WorldParser;

public class Bullet extends GameObject {
    protected boolean active;
    protected float angle;
    protected float r;

    // be careful when adjusting this parameter, as this is not the range in tiles but rather a
    // counting of delta times
    // speed until the "range" is reached
    // TODO: this needs to be refactored to actually represent a value in tiles
    protected float range = 2.3f;

    public Bullet(Vector2 position, World world, float angle, short mask) {
        super(
                "bullet7x13",
                new Vector2(position.x / Constants.TILE_SIZE, position.y / Constants.TILE_SIZE),
                world,
                mask);
        this.angle = angle;
        active = true;
        speed = 3;
        width = 3;
        height = 6;
        // wait until world is unlocked before adding body
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
    }

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
}
