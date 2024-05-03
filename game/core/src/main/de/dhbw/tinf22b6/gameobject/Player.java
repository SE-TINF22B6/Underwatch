package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.PlayerStatistics;
import de.dhbw.tinf22b6.weapon.Bow;
import de.dhbw.tinf22b6.weapon.Weapon;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static de.dhbw.tinf22b6.util.Constants.PLAYER_BIT;
import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class Player extends MobGameObject {
    private final Animation<TextureAtlas.AtlasRegion> dodgeAnimation;
    private final Camera camera;
    private Weapon weapon;
    private boolean dodging;
    private float dodgeStateTime;
    private boolean movedDuringDash;
    private final Vector2 motionVector;

    public Player(World world, Vector2 position, Camera camera) {
        super("c1", position, world, Constants.PLAYER_BIT);
        this.camera = camera;
        this.dodgeAnimation = new Animation<>(0.1f, Assets.instance.getAnimationAtlasRegion("priest1_dash"));
        this.speed = 50;
        this.motionVector = new Vector2();

        // create Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + TILE_SIZE / 2f, pos.y + TILE_SIZE / 4f);
        bodyDef.angle = 0;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float) 15 / 2, (float) 20 / 4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.filter.categoryBits = PLAYER_BIT;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef).setUserData(this);
        boxShape.dispose();

        // equip weapon
        this.weapon = new Bow(world);
    }

    public float getAngle() {
        Vector3 unproject = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector2 unprojectMinusLoc = new Vector2(unproject.x - pos.x - 17 / 2f, unproject.y - pos.y - 25 / 2f);
        return unprojectMinusLoc.angleDeg();
    }

    @Override
    public void render(Batch batch) {
        if (!dodging) {
            float angle = getAngle();
            int r = 5;
            if (angle > 20 && angle < 160) {
                batch.draw(weapon.getRegion(),
                        (pos.x + 4) + r * cosDeg(angle),
                        (pos.y + 4) + r * sinDeg(angle),
                        8, 8,
                        weapon.getRegion().originalWidth,
                        weapon.getRegion().originalHeight,
                        1, 1,
                        angle - 45);
                super.render(batch);
            } else {
                super.render(batch);
                batch.draw(weapon.getRegion(),
                        (pos.x + 4) + r * cosDeg(angle),
                        (pos.y + 4) + r * sinDeg(angle),
                        8, 8,
                        weapon.getRegion().originalWidth,
                        weapon.getRegion().originalHeight,
                        1, 1,
                        angle - 45);
            }
            return;
        }
        batch.draw(currentAnimation.getKeyFrame(dodgeStateTime, true), pos.x, pos.y);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        weapon.updateRemainingCoolDown(delta);
        applyForce(motionVector.setLength(1));

        if (motionVector.len() == 0) {
            setIdle();
        } else {
            setWalking();
        }

        float angle = getAngle();
        if (angle > 360 - 45 || angle < 45) {
            setDirection(Direction.RIGHT);
        } else if (angle > 45 && angle < 135) {
            setDirection(Direction.UP);
        } else if (angle > 135 && angle < 225) {
            setDirection(Direction.LEFT);
        } else {
            setDirection(Direction.DOWN);
        }

        dodgeStateTime += delta;
        if (!dodging) {
            pos.x = body.getPosition().x - (float) TILE_SIZE / 2;
            pos.y = body.getPosition().y - (float) TILE_SIZE / 4;
        }
    }

    public void applyForce(Vector2 motionVector) {
        if (dodging) {
            if (movedDuringDash) return;
            movedDuringDash = true;
            new Thread(() -> {
                try {
                    Thread.sleep(200);
                    body.setLinearVelocity(motionVector.x * 3000, motionVector.y * 3000);
                    pos.x = body.getPosition().x - (float) TILE_SIZE / 2;
                    pos.y = body.getPosition().y - (float) TILE_SIZE / 4;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } else {
            body.setLinearVelocity(motionVector.x * speed, motionVector.y * speed);
            pos.x = body.getPosition().x - (float) TILE_SIZE / 2;
            pos.y = body.getPosition().y - (float) TILE_SIZE / 4;
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void hit() {
        PlayerStatistics.instance.hitHP();
        Gdx.audio.newSound(Gdx.files.internal("sfx/player_hit.mp3")).play(1);
    }

    public boolean isDodging() {
        return dodging;
    }

    public void dodge() {
        this.applyForce(new Vector2(0, 0));
        this.dodging = true;
        dodgeStateTime = 0;
        this.currentAnimation = dodgeAnimation;
        new Thread(() -> {
            try {
                Thread.sleep((long) (dodgeAnimation.getAnimationDuration() * 1000));
                setIdle();
                setDirection(Direction.UP);
                this.dodging = false;
                this.movedDuringDash = false;
                this.applyForce(new Vector2(0, 0));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void shoot() {
        weapon.shoot();
    }

    public Vector2 getMotionVector() {
        return motionVector;
    }
}
