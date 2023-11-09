package de.dhbw.tinf22b6.world.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class Player extends AnimatedGameObject {
    private static final String TAG = Player.class.getName();
    private final Body body;
    private int speed = 50;

    public Player(World world, Vector2 position) {
        super("priest1_v1", position);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + (float) TILE_SIZE / 2, pos.y + (float) TILE_SIZE / 4);
        bodyDef.angle = 0;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float) TILE_SIZE / 3, (float) TILE_SIZE / 4);

        fixtureDef.shape = boxShape;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);
        boxShape.dispose();
    }

    public void applyForce(Vector2 motionVector) {
        body.setLinearVelocity(motionVector.x * speed, motionVector.y * speed);
        pos.x = body.getPosition().x - (float) TILE_SIZE /2;
        pos.y = body.getPosition().y - (float) TILE_SIZE /4;
    }
}
