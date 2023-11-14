package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public abstract class AnimatedCollisionObject extends AnimatedGameObject {
    protected Body body;

    protected boolean dead;

    public AnimatedCollisionObject(String region, Vector2 position, World world, short categoryBit) {
        super(region, position);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + TILE_SIZE / 2f, pos.y + TILE_SIZE / 2f);
        bodyDef.angle = 0;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float) TILE_SIZE / 4, (float) TILE_SIZE / 4);

        fixtureDef.shape = boxShape;
        fixtureDef.filter.categoryBits = categoryBit;
        fixtureDef.isSensor = true;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef).setUserData(this);
        boxShape.dispose();
    }

    public void setDead(boolean b) {
        this.dead = b;
    }

    public boolean isDead() {
        return dead;
    }

    public Body getBody() {
        return body;
    }
}
