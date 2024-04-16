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
import de.dhbw.tinf22b6.util.PlayerStatistics;
import de.dhbw.tinf22b6.weapon.Weapon;

import static de.dhbw.tinf22b6.util.Constants.PLAYER_BIT;
import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class Player extends GameObject {
    private static final String TAG = Player.class.getName();
    private Weapon weapon;
    private boolean dodging;
    private final PlayerStatistics playerStatistics;

    private final Animation<TextureAtlas.AtlasRegion> dodgeAnimation;
    private float dodgeStateTime;

    public Player(World world, Vector2 position, PlayerStatistics statistics) {
        super("priest1_v1", position, world, Constants.PLAYER_BIT);
        this.playerStatistics = statistics;
        // equip weapon
        //this.weapon = new HandGun();
        this.dodgeAnimation = new Animation<>(0.1f, Assets.instance.getAnimationAtlasRegion("priest1_dash"));
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

        body.createFixture(fixtureDef).setUserData(this);
        boxShape.dispose();
    }

    @Override
    public void render(Batch batch) {
        if (!dodging) {
            super.render(batch);
            return;
        }
        batch.draw(currentAnimation.getKeyFrame(dodgeStateTime, true), pos.x, pos.y);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        dodgeStateTime += delta;
        if (!dodging) {
            pos.x = body.getPosition().x - (float) TILE_SIZE / 2;
            pos.y = body.getPosition().y - (float) TILE_SIZE / 4;
        }
    }

    private boolean movedDuringDash;

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

    public void hit() {
        playerStatistics.hitHP(); ;
        Gdx.audio.newSound(Gdx.files.internal("sfx/player_hit.mp3")).play(1);
    }

    public int getHealth() {
        return playerStatistics.hp();
    }

    public int getCoins() {
        return playerStatistics.coins();
    }

    public void collectCoin() {
        playerStatistics.setCoins(getCoins() + 1);
        Gdx.app.debug(TAG, "Player picked up Coin: " + getCoins());
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
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
                currentAnimation = idleAnimation;
                this.dodging = false;
                this.movedDuringDash = false;
                this.applyForce(new Vector2(0, 0));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public int getScore() {
        return playerStatistics.coins() + playerStatistics.enemies_killed();
    }
}
