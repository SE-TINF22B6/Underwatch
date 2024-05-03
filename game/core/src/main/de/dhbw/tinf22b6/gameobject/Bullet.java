package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.world.WorldParser;

import static com.badlogic.gdx.math.MathUtils.*;
import static de.dhbw.tinf22b6.world.WorldParser.getDynamicBodyDef;

public class Bullet extends GameObject {
    protected boolean active;
    protected float distMoved;
    protected float angle;
    protected float range = 150;

    public Bullet(Vector2 position, World world, float angle, short mask) {
        super("bullet7x13", new Vector2(position.x / Constants.TILE_SIZE, position.y / Constants.TILE_SIZE), world, mask);
        this.angle = angle;
        active = true;
        speed = 100;
        width = 3;
        height = 6;
        // wait until world is unlocked before adding body
        boolean isLocked;
        do {
            isLocked = world.isLocked();
        } while (isLocked);
        body = world.createBody(WorldParser.getDynamicBodyDef(pos.x + width / 2, pos.y + height / 2));
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width - 2, height - 2);

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
        batch.draw(currentAnimation.getKeyFrame(stateTime, true), pos.x, pos.y, width / 2, height / 2, width, height, 1, 1, rotation);
    }

    float r;

    public void tick(float delta) {
        super.tick(delta); // play the animation
        r += delta * speed;

        if (active) {
            Vector2 tmp = new Vector2(pos);
            tmp.y = r * sinDeg(angle);
            tmp.x = r * cosDeg(angle);

            //distMoved += Vector2.dst(pos.x, pos.y, dx2, dy2);
            pos.set(tmp);
            body.setTransform(pos.x + width / 2, pos.y + height / 2, (float) (angle + 90 * (Math.PI / 180)));

            if (distMoved > range) {
                // Remove Bullet
                remove = true;
                active = false;
            }
        }
    }
}
