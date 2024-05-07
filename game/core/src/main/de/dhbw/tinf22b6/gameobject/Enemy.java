package de.dhbw.tinf22b6.gameobject;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.Path.PathParam;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.ai.Box2DLocation;
import de.dhbw.tinf22b6.ai.EnemyFSMState;
import de.dhbw.tinf22b6.ai.EnemyStateMachine;
import de.dhbw.tinf22b6.ai.EnemySteeringBehaviour;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.SteeringUtils;

public class Enemy extends GameObject implements Steerable<Vector2> {
    private static final String TAG = Enemy.class.getName();
    private static SteeringAcceleration<Vector2> steeringOutput;
    protected SteeringBehavior<Vector2> steeringBehavior;
    private FollowPath<Vector2, PathParam> followPath;
    private EnemyStateMachine stateMachine;
    private DefaultStateMachine<Enemy, EnemyFSMState> defaultStateMachine;
    private int health;
    private float boundingRadius;
    private boolean tagged;
    private float maxLinearSpeed;
    private float maxLinearAcceleration;

    public Enemy(Vector2 position, World world, int[][] rawMap) {
        super("skeleton_v2", position, world, Constants.ENEMY_BIT);
        this.health = 3;
        this.stateMachine = new EnemyStateMachine(this, world, rawMap);
        this.steeringBehavior = new EnemySteeringBehaviour(this);
        this.maxLinearSpeed = 10;
        this.boundingRadius = 5;
        this.maxLinearAcceleration = 100;

        // create Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + TILE_SIZE / 2f, pos.y + TILE_SIZE / 4f);
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float) TILE_SIZE / 3, (float) TILE_SIZE / 3);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.filter.categoryBits = collisionMask;
        // fixtureDef.isSensor = true;
        fixtureDef.restitution = 0.0f;

        PolygonShape sightShape = new PolygonShape();
        sightShape.setAsBox(TILE_SIZE * 5, TILE_SIZE * 5);
        FixtureDef sightDef = new FixtureDef();
        sightDef.shape = sightShape;
        sightDef.isSensor = true;
        sightDef.filter.categoryBits = Constants.ENEMY_SIGHT_BIT;

        body.createFixture(fixtureDef).setUserData(this);
        body.createFixture(sightDef).setUserData(this);
        boxShape.dispose();
        body.setUserData(this);
        this.defaultStateMachine = new DefaultStateMachine<Enemy, EnemyFSMState>(this, EnemyFSMState.DO_NOTHING);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        stateMachine.tick(delta);
        update(delta);
    }

    public void hit() {
        this.health--;
        if (health == 0) {
            this.remove = true;
        }
        Gdx.audio.newSound(Gdx.files.internal("sfx/hitSound.mp3")).play(1);
    }

    public void update(float deltaTime) {
        if (steeringBehavior != null) {
            // Calculate steering acceleration
            steeringOutput = steeringBehavior.calculateSteering(steeringOutput);
            // Apply steering acceleration
            // Ja hier wird er nach dem applySteering behaviour geupdated. Da müssen wir das
            // mit der Path generation rein kriegen, dann brauchen wir auch die
            // EnemyStateMachine nicht mehr.
            applySteering(deltaTime);
        }
    }

    protected void applySteering(float delta) {
        boolean anyAccelerations = false;

        // Update position and linear velocity.
        if (!steeringOutput.linear.isZero()) {
            // this method internally scales the force by deltaTime
            body.applyForceToCenter(steeringOutput.linear, true);
            anyAccelerations = true;
        }

        if (anyAccelerations) {
            // Cap the linear speed
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            float maxLinearSpeed = getMaxLinearSpeed();
            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }
        }
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return 0;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
    }

    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        body.setTransform(getPosition(), orientation);
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public Location<Vector2> newLocation() {
        return new Box2DLocation();
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }
}
