package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.dhbw.tinf22b6.ai.Box2DLocation;
import de.dhbw.tinf22b6.ai.EnemySteeringBehaviour;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;
import de.dhbw.tinf22b6.util.SteeringUtils;
import de.dhbw.tinf22b6.world.tiled.FlatTiledGraph;
import de.dhbw.tinf22b6.world.tiled.FlatTiledNode;
import de.dhbw.tinf22b6.world.tiled.TiledMetricHeuristic;
import de.dhbw.tinf22b6.world.tiled.TiledSmoothableGraphPath;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public abstract class Enemy extends MobGameObject implements Steerable<Vector2> {
    private static final String TAG = Enemy.class.getName();
    private static SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
    private final IndexedAStarPathFinder<FlatTiledNode> finder;
    private final Heuristic<FlatTiledNode> heuristic;
    private final FlatTiledGraph worldGraph;
    protected SteeringBehavior<Vector2> steeringBehavior;
    private int health;
    private boolean tagged;
    private float maxLinearSpeed;
    private float maxLinearAcceleration;

    public Enemy(String region, Vector2 position, World world, int[][] rawMap) {
        super(region, position, world, Constants.ENEMY_BIT);
        // equip weapon
        // this.weapon = new HandGun();
        this.health = 3;
        this.steeringBehavior = new EnemySteeringBehaviour(this);
        this.maxLinearSpeed = 20;
        this.maxLinearAcceleration = 100;
        this.worldGraph = new FlatTiledGraph(rawMap);
        this.finder = new IndexedAStarPathFinder<>(worldGraph, true);
        this.heuristic = new TiledMetricHeuristic<>();

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
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        update();
    }

    public void hit() {
        this.health--;
        if (health == 0) {
            this.remove = true;
        }
        Gdx.audio.newSound(Gdx.files.internal("sfx/hitSound.mp3")).play(1);
    }

    public void update() {
        Player player = EntitySystem.instance.getPlayer();
        FlatTiledNode startNode = worldGraph.getNode(
                (int) this.getBody().getPosition().x / TILE_SIZE,
                (int) this.getBody().getPosition().y / TILE_SIZE);
        FlatTiledNode endNode =
                worldGraph.getNode((int) player.getPos().x / TILE_SIZE, (int) player.getPos().y / TILE_SIZE);
        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        finder.searchNodePath(startNode, endNode, heuristic, path);
        Array<Vector2> vecArray = new Array<>();
        for (int i = 1; i < path.nodes.size; i++) {
            vecArray.add(new Vector2(
                    path.nodes.get(i).x * TILE_SIZE + TILE_SIZE / 2f,
                    path.nodes.get(i).y * TILE_SIZE + TILE_SIZE / 2f));
        }

        // A Path always contains at least two waypoints
        if (vecArray.size < 2) {
            vecArray.add(this.getPos());
            vecArray.add(player.getPos());
        }

        LinePath<Vector2> linePath = new LinePath<>(vecArray, true);
        FollowPath<Vector2, LinePath.LinePathParam> followPath = new FollowPath<>(this, linePath);
        steeringBehavior = followPath;
        // Once arrived at an extremity of the path we want to go the other way around
        Vector2 extremity = followPath.getPathOffset() >= 0 ? linePath.getEndPoint() : linePath.getStartPoint();
        float tolerance = followPath.getArrivalTolerance();
        if (getPosition().dst2(extremity) < tolerance * tolerance) {
            followPath.setPathOffset(-followPath.getPathOffset());
        }
        if (steeringBehavior != null) {
            // Calculate steering acceleration
            steeringOutput = steeringBehavior.calculateSteering(steeringOutput);
            // Apply steering acceleration
            applySteering();
            super.setWalking();
        } else {
            super.setIdle();
        }
    }

    protected void applySteering() {
        boolean anyAccelerations = false;
        // Update position and linear velocity.
        if (!steeringOutput.linear.isZero()) {
            // this method internally scales the force by deltaTime
            body.applyForceToCenter(steeringOutput.linear, true);
            float angle = steeringOutput.linear.angleDeg();
            if (angle > 360 - 45 || angle < 45) {
                setDirection(Direction.RIGHT);
            } else if (angle > 45 && angle < 135) {
                setDirection(Direction.UP);
            } else if (angle > 135 && angle < 225) {
                setDirection(Direction.LEFT);
            } else {
                setDirection(Direction.DOWN);
            }
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
        return 0;
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
