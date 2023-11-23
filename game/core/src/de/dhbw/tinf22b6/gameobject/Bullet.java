package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;
import static de.dhbw.tinf22b6.world.WorldParser.getDynamicBodyDef;

public class Bullet extends GameObject {
    protected boolean active;
    protected float distMoved;
    protected Vector2 vector;
    protected float range = 150;

    public Bullet(Vector2 position, World world, Vector2 direction, short mask) {
        super("bullet7x13", new Vector2(position.x / TILE_SIZE, position.y / TILE_SIZE), world, mask);
        active = true;
        this.vector = direction;
        speed = 100;
        width = 3;
        height = 6;
        body = world.createBody(getDynamicBodyDef(pos.x + width / 2, pos.y + height / 2));
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
        float rotation = 90 + vector.angleDeg() + 180;
        batch.draw(currentAnimation.getKeyFrame(stateTime, true), pos.x, pos.y, width / 2, height / 2, width, height, 1, 1, rotation);
    }

    public void tick(float delta) {
        super.tick(delta); // play the animation
        if (active) {
            float dx = (delta * vector.x) * speed;
            float dy = (delta * vector.y) * speed;
            float dx2 = pos.x + dx;
            float dy2 = pos.y + dy;

            distMoved += Vector2.dst(pos.x, pos.y, dx2, dy2);
            pos.set(dx2, dy2);
            body.setTransform(pos.x + width / 2, pos.y + height / 2, (float) ((vector.angleDeg() + 90) * (Math.PI / 180)));

            if (distMoved > range) {
                // Remove Bullet
                remove = true;
                active = false;
            }
        }
    }
}
