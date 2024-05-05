package de.dhbw.tinf22b6.gameobject;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.ai.EnemyStateMachine;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.weapon.Weapon;

public class Enemy extends GameObject {
    private static final String TAG = Enemy.class.getName();
    private Weapon weapon;
    private EnemyStateMachine stateMachine;
    private int health;

    public Enemy(Vector2 position, World world, int[][] rawMap) {
        super("skeleton_v2", position, world, Constants.ENEMY_BIT);
        // equip weapon
        // this.weapon = new HandGun();
        this.speed = 20;
        this.health = 3;
        this.stateMachine = new EnemyStateMachine(this, world, rawMap);
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
        fixtureDef.isSensor = true;
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
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        stateMachine.tick(delta);
        pos.x = body.getPosition().x - (float) TILE_SIZE / 2;
        pos.y = body.getPosition().y - (float) TILE_SIZE / 4;
    }

    public void applyForce(Vector2 motionVector) {
        body.setLinearVelocity(motionVector.x * speed, motionVector.y * speed);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void hit() {
        this.health--;
        if (health == 0) {
            this.remove = true;
        }
        Gdx.audio.newSound(Gdx.files.internal("sfx/hitSound.mp3")).play(1);
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public Vector2 getPos() {
        return new Vector2(pos.x + (float) TILE_SIZE / 2, pos.y + (float) TILE_SIZE / 2);
    }
}
