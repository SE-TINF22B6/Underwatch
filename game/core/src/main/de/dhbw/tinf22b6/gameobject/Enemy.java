package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath.LinePathParam;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.dhbw.tinf22b6.ai.Box2DLocation;
import de.dhbw.tinf22b6.ai.EnemyStateMachine;
import de.dhbw.tinf22b6.ai.EnemySteeringBehaviour;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;
import de.dhbw.tinf22b6.util.SteeringUtils;
import de.dhbw.tinf22b6.world.tiled.FlatTiledGraph;
import de.dhbw.tinf22b6.world.tiled.FlatTiledNode;
import de.dhbw.tinf22b6.world.tiled.TiledMetricHeuristic;
import de.dhbw.tinf22b6.world.tiled.TiledSmoothableGraphPath;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class Enemy extends GameObject implements Steerable<Vector2> {
    private static final String TAG = Enemy.class.getName();
    private static SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
    ;
    protected SteeringBehavior<Vector2> steeringBehavior;
    private FollowPath<Vector2, LinePathParam> followp;
    private EnemyStateMachine stateMachine;
    private IndexedAStarPathFinder<FlatTiledNode> finder;
    private Heuristic<FlatTiledNode> heuristic;
    private FlatTiledGraph worldGraph;
    private TiledSmoothableGraphPath<FlatTiledNode> path;
    // private Player player;
    private int health;
    private float boundingRadius;
    private boolean tagged;
    private float maxLinearSpeed;
    private float maxLinearAcceleration;

    public Enemy(
            Vector2 position,
            World world, // Player player,
            int[][] rawMap) {
        super("skeleton_v2", position, world, Constants.ENEMY_BIT);
        this.health = 3;
        this.stateMachine = new EnemyStateMachine(this, world, rawMap);
        this.steeringBehavior = new EnemySteeringBehaviour(this);
        this.maxLinearSpeed = 10;
        this.boundingRadius = 5;
        this.maxLinearAcceleration = 100;
        this.worldGraph = new FlatTiledGraph(rawMap);
        this.finder = new IndexedAStarPathFinder<>(worldGraph, true);
        this.path = new TiledSmoothableGraphPath<>();
        this.heuristic = new TiledMetricHeuristic<>();

        // this.player = player;

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
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        //stateMachine.tick(delta);
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
        // This now lets the steering use the gaph. The pathfinding is still fucked
        Player player = EntitySystem.instance.getPlayer();
        FlatTiledNode startNode = worldGraph.getNode((int) this.getBody().getPosition().x / TILE_SIZE, (int) this.getBody().getPosition().y / TILE_SIZE);
        FlatTiledNode endNode = worldGraph.getNode((int) player.getPos().x / TILE_SIZE, (int) player.getPos().y / TILE_SIZE);
        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        finder.searchNodePath(startNode, endNode, heuristic, path);
        Array<Vector2> vecArray = new Array<>();
        for (int i = 1; i < path.nodes.size; i++) {
            vecArray.add(new Vector2(path.nodes.get(i).x * TILE_SIZE + TILE_SIZE / 2f, path.nodes.get(i).y * TILE_SIZE + TILE_SIZE / 2f));
        }
        // Exception in thread "main" java.lang.IllegalArgumentException: waypoints
        // cannot be null and must contain at least two (2) waypoints
        // at
        // com.badlogic.gdx.ai.steer.utils.paths.LinePath.createPath(LinePath.java:185)
        // at com.badlogic.gdx.ai.steer.utils.paths.LinePath.<init>(LinePath.java:55)
        //
        // Fix for the error above
        if (vecArray.size < 2) {
            vecArray.add(this.getPos());
            vecArray.add(player.getPos());
        }

        LinePath<Vector2> linePath = new LinePath<>(vecArray, true);
        followp = new FollowPath<>(this, linePath);
        steeringBehavior = followp;
        // Once arrived at an extremity of the path we want to go the other way around
        Vector2 extremity = followp.getPathOffset() >= 0 ? linePath.getEndPoint() : linePath.getStartPoint();
        float tolerance = followp.getArrivalTolerance();
        if (getPosition().dst2(extremity) < tolerance * tolerance) {
            followp.setPathOffset(-followp.getPathOffset());
        }
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
