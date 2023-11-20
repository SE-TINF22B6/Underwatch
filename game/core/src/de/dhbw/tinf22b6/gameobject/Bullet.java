package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Constants;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;
import static de.dhbw.tinf22b6.world.WorldParser.getDynamicBodyDef;

public class Bullet extends GameObject {
    protected boolean active;
    protected float distMoved;
    protected Vector2 vector;
    protected float range = 150;

    public Bullet(Vector2 position, World world, Vector2 direction) {
        super("coin", new Vector2(position.x / TILE_SIZE, position.y / TILE_SIZE), world, Constants.WEAPON_BIT);

        body = world.createBody(getDynamicBodyDef(pos.x + TILE_SIZE / 2f, pos.y + TILE_SIZE / 2f));
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(5, 5);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = collisionMask;

        body.createFixture(fixtureDef).setUserData(this);
        polygonShape.dispose();
        this.vector = direction;
        active = true;
        speed = 100;
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
            body.setTransform(pos.x + width / 2, pos.y + height / 2, 0);

            if (distMoved > range) {
                // Remove Bullet
                remove = true;
                active = false;
            }
        }
    }
}
