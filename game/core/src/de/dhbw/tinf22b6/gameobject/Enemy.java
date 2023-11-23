package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.dhbw.tinf22b6.ai.EnemyStateMachine;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.weapon.Weapon;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class Enemy extends GameObject {
    private static final String TAG = Enemy.class.getName();
    private Weapon weapon;
    private int health;
    private final EnemyStateMachine enemyStateMachine;

    public Enemy(Vector2 position, World world) {
        super("skeleton_v2", position, world, Constants.ENEMY_BIT);
        // equip weapon
        //this.weapon = new HandGun();
        this.speed = 50;
        this.enemyStateMachine = new EnemyStateMachine(this, world);
        this.health = 3;
        // create Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + TILE_SIZE / 2f, pos.y + TILE_SIZE / 4f);
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float) TILE_SIZE / 2, (float) TILE_SIZE / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.filter.categoryBits = collisionMask;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef).setUserData(this);
        boxShape.dispose();
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        applyForce(new Vector2(-1,0));
        enemyStateMachine.tick(delta);
    }

    public void applyForce(Vector2 motionVector) {
        body.setLinearVelocity(motionVector.x * speed, motionVector.y * speed);
        pos.x = body.getPosition().x - (float) TILE_SIZE / 2;
        pos.y = body.getPosition().y - (float) TILE_SIZE / 4;
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
}
