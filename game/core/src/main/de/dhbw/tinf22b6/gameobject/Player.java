package de.dhbw.tinf22b6.gameobject;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static de.dhbw.tinf22b6.util.Constants.PLAYER_BIT;
import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

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
import de.dhbw.tinf22b6.weapon.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends MobGameObject {
    private final Animation<TextureAtlas.AtlasRegion> dodgeAnimation;
    private final Camera camera;
    private Weapon weapon;
    private boolean dodging;
    private float dodgeStateTime;
    private boolean movedDuringDash;
    private final Vector2 motionVector;
    private final ArrayList<Weapon> inventory;
    private boolean canSwitchWeapon = true;
    private GameObject interactionTarget;

    public Player(World world, Vector2 position, Camera camera) {
        super("c1", position, world, Constants.PLAYER_BIT);
        this.camera = camera;
        this.dodgeAnimation = new Animation<>(0.1f, Assets.instance.getAnimationAtlasRegion("priest1_dash"));
        this.speed = 50;
        this.motionVector = new Vector2();
        this.inventory = new ArrayList<>();

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

        // add starter weapon to inventory
        inventory.add(new Ak(world));

        // equip weapon
        this.weapon = inventory.get(0);
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
                batch.draw(
                        weapon.getRegion(),
                        (pos.x + 4) + r * cosDeg(angle),
                        (pos.y + 4) + r * sinDeg(angle),
                        8,
                        8,
                        weapon.getRegion().originalWidth,
                        weapon.getRegion().originalHeight,
                        1,
                        1,
                        angle);
                super.render(batch);
            } else {
                super.render(batch);
                batch.draw(
                        weapon.getRegion(),
                        (pos.x + 4) + r * cosDeg(angle),
                        (pos.y + 4) + r * sinDeg(angle),
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
                    })
                    .start();
        } else {
            body.setLinearVelocity(motionVector.x * speed, motionVector.y * speed);
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
        weapon.shoot();
    }

    public Vector2 getMotionVector() {
        return motionVector;
    }

    public void cycleWeapon(boolean direction) {
        if (canSwitchWeapon && inventory.size() > 1) {
            canSwitchWeapon = false;
            for (int i = 0; i < inventory.toArray().length; i++) {
                if (inventory.get(i) == weapon) {
                    if (direction) {
                        this.weapon = inventory.get(Math.floorMod(i - 1, inventory.size()));
                    } else {
                        this.weapon = inventory.get(Math.floorMod(i + 1, inventory.size()));
                    }
                    Gdx.audio
                            .newSound(Gdx.files.internal("sfx/Grunt.mp3"))
                            .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
                    break;
                }
            }
            new Thread(() -> {
                        try {
                            Thread.sleep(500);
                            canSwitchWeapon = true;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .start();
        }
    }

    // TODO: here we should roll for a new weapon, which the player does not have yet
    public void pickupWeapon() {
        this.inventory.add(new M4(world));
    }

    public String getInventoryInfo() {
        return Arrays.toString(inventory.toArray());
    }
}
