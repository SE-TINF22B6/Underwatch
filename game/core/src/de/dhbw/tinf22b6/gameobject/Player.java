package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.weapon.Bow;
import de.dhbw.tinf22b6.weapon.Weapon;

import static de.dhbw.tinf22b6.util.Constants.PLAYER_BIT;
import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class Player extends AnimatedCollisionObject {
    private static final int SPEED = 50;
    private Weapon weapon;

    public Player(World world, Vector2 position) {
        super("priest1_v1", position, world, Constants.PLAYER_BIT);
        // destroy the body created by the super class
        world.destroyBody(body);
        // equip weapon
        this.weapon = new Bow();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x, pos.y);
        bodyDef.angle = 0;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float) TILE_SIZE / 3, (float) TILE_SIZE / 4);

        fixtureDef.shape = boxShape;
        fixtureDef.filter.categoryBits = PLAYER_BIT;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);
        boxShape.dispose();
    }

    public void applyForce(Vector2 motionVector) {
        body.setLinearVelocity(motionVector.x * SPEED, motionVector.y * SPEED);
        pos.x = body.getPosition().x - (float) TILE_SIZE / 2;
        pos.y = body.getPosition().y - (float) TILE_SIZE / 4;
    }
}
