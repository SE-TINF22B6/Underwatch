package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.weapon.Weapon;

import static de.dhbw.tinf22b6.util.Constants.PLAYER_BIT;
import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class Player extends GameObject {
    private static final String TAG = Player.class.getName();
    private Weapon weapon;
    private boolean dodging;
    private final Animation<TextureAtlas.AtlasRegion> dodgeAnimation;

    public Player(World world, Vector2 position) {
        super("priest1_v1", position, world, Constants.PLAYER_BIT);
        // equip weapon
        //this.weapon = new HandGun();
        this.dodgeAnimation = new Animation<>(0.3f, Assets.instance.getAnimationAtlasRegion("priest1_dash"));
        this.speed = 50;
        // create Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + TILE_SIZE / 2f, pos.y + TILE_SIZE / 4f);
        bodyDef.angle = 0;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float) TILE_SIZE / 3, (float) TILE_SIZE / 4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.filter.categoryBits = PLAYER_BIT;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);
        boxShape.dispose();
    }

    @Override
    public void render(Batch batch) {
        if (!dodging) {
            super.render(batch);
            return;
        }
        batch.draw(currentAnimation.getKeyFrame(stateTime), pos.x, pos.y);
    }

    public void applyForce(Vector2 motionVector) {
        body.setLinearVelocity(motionVector.x * speed, motionVector.y * speed);
        pos.x = body.getPosition().x - (float) TILE_SIZE / 2;
        pos.y = body.getPosition().y - (float) TILE_SIZE / 4;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public boolean isDodging() {
        return dodging;
    }

    public void dodge() {
        this.dodging = true;
        this.currentAnimation = dodgeAnimation;
        new Thread(() -> {
            try {
                Thread.sleep((long) currentAnimation.getAnimationDuration() * 1000);
                currentAnimation = idleAnimation;
                this.dodging = false;
                Gdx.app.debug(TAG, "ending dodge");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
