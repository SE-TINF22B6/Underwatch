package de.dhbw.tinf22b6.gameobject;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static de.dhbw.tinf22b6.util.Constants.PLAYER_BIT;
import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import de.dhbw.tinf22b6.gameobject.enemy.MobGameObject;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.PlayerStatistics;
import de.dhbw.tinf22b6.weapon.Weapon;
import de.dhbw.tinf22b6.world.Box2dWorld;

public class Player extends MobGameObject {
    private final Animation<TextureAtlas.AtlasRegion> dodgeAnimation;
    private final Camera camera;
    private final Vector2 motionVector;
    private float speed;
    private boolean dodging;
    private float dodgeStateTime;
    private boolean movedDuringDash;
    private GameObject interactionTarget;
    private boolean isSprinting;
    private float timeToRegenStamina;

    public Player(Vector2 position, Camera camera) {
        super("c1", position, Constants.PLAYER_BIT);
        this.camera = camera;
        this.dodgeAnimation = new Animation<>(0.1f, Assets.instance.getAnimationAtlasRegion("priest1_dash"));
        this.speed = 75;
        this.motionVector = new Vector2();
        this.timeToRegenStamina = 3;

        // create Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + TILE_SIZE / 2f, pos.y + TILE_SIZE / 4f);
        bodyDef.angle = 0;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = Box2dWorld.instance.getWorld().createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(
                currentAnimation.getKeyFrame(0).originalWidth / 4f,
                currentAnimation.getKeyFrame(0).originalHeight / 12f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.filter.categoryBits = PLAYER_BIT;
        fixtureDef.restitution = 0.0f;

        PolygonShape collisionShape = new PolygonShape();
        collisionShape.setAsBox(
                currentAnimation.getKeyFrame(0).originalWidth / 3.5f,
                currentAnimation.getKeyFrame(0).originalHeight / 2.5f,
                new Vector2(0, currentAnimation.getKeyFrame(0).originalHeight / 3f),
                0);

        FixtureDef collisionFixtureDef = new FixtureDef();
        collisionFixtureDef.shape = collisionShape;
        collisionFixtureDef.filter.categoryBits = PLAYER_BIT;
        collisionFixtureDef.isSensor = true;

        body.createFixture(collisionFixtureDef).setUserData(this);
        body.createFixture(fixtureDef).setUserData(this);
        boxShape.dispose();
        collisionShape.dispose();
    }

    public float getAngle() {
        Vector3 unproject = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector2 unprojectMinusLoc = new Vector2(unproject.x - pos.x - 17 / 2f, unproject.y - pos.y - 25 / 2f);
        return unprojectMinusLoc.angleDeg();
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
        Weapon weapon = PlayerStatistics.instance.getCurrentWeapon();
        if (!dodging) {
            float angle = getAngle();
            int r = 5;
            if (angle > 20 && angle < 160) {
                batch.draw(
                        weapon.getRegion(),
                        (pos.x) + r * cosDeg(angle),
                        (pos.y) + 5 + r * sinDeg(angle),
                        8,
                        8,
                        weapon.getRegion().originalWidth,
                        weapon.getRegion().originalHeight,
                        1,
                        1,
                        angle);
                batch.draw(
                        currentAnimation.getKeyFrame(stateTime, true),
                        body.getPosition().x - currentAnimation.getKeyFrame(0).originalWidth / 2f + 0.5f,
                        body.getPosition().y - 2);
            } else {
                batch.draw(
                        currentAnimation.getKeyFrame(stateTime, true),
                        body.getPosition().x - currentAnimation.getKeyFrame(0).originalWidth / 2f + 0.5f,
                        body.getPosition().y - 2);
                batch.draw(
                        weapon.getRegion(),
                        (pos.x) + r * cosDeg(angle),
                        (pos.y) + 5 + r * sinDeg(angle),
                        8,
                        8,
                        weapon.getRegion().originalWidth,
                        weapon.getRegion().originalHeight,
                        1,
                        1,
                        angle);
            }
            return;
        }
        batch.draw(currentAnimation.getKeyFrame(dodgeStateTime, true), pos.x, pos.y);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);

        PlayerStatistics.instance.getCurrentWeapon().updateRemainingCoolDown(delta);
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

        if (!isSprinting) timeToRegenStamina -= delta;

        if (!isSprinting && timeToRegenStamina <= 0) {
            PlayerStatistics.instance.regenerateStamina(delta);
        }

        dodgeStateTime += delta;
        if (!dodging) {
            pos.x = body.getPosition().x - (float) TILE_SIZE / 2;
            pos.y = body.getPosition().y - (float) TILE_SIZE / 4;
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            shoot();
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
                    })
                    .start();
        } else {
            body.setLinearVelocity(motionVector.x * getSpeed(), motionVector.y * getSpeed());
            pos.x = body.getPosition().x - (float) TILE_SIZE / 2;
            pos.y = body.getPosition().y - (float) TILE_SIZE / 4;
        }
    }

    public void canPickUp(GameObject interactionTarget) {
        this.interactionTarget = interactionTarget;
    }

    @Override
    public void interact(Player player) {
        if (interactionTarget != null) {
            Gdx.audio
                    .newSound(Gdx.files.internal("sfx/game_over.mp3"))
                    .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
            interactionTarget.interact(this);
        }
    }

    public void speedBoost(boolean big) {
        this.speed = big ? speed + speed * 0.10f : speed + speed * 0.02f;
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
                })
                .start();
    }

    public void shoot() {
        PlayerStatistics.instance.getCurrentWeapon().shoot();
    }

    public Vector2 getMotionVector() {
        return motionVector;
    }

    public void pickupWeapon() {
        PlayerStatistics.instance.pickupWeapon();
    }

    public void setSprinting() {
        isSprinting = true;
    }

    public void stopSprinting() {
        isSprinting = false;
        timeToRegenStamina = 3;
    }

    public float getSpeed() {
        return isSprinting ? speed * 2 : speed;
    }
}
