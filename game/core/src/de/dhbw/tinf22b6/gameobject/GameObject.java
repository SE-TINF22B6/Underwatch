package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Assets;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public abstract class GameObject {
    protected final Animation<TextureAtlas.AtlasRegion> currentAnimation;
    protected Body body;
    protected World world;
    protected float width, height;
    protected Sound sound;
    protected Vector2 pos;
    protected float stateTime;
    protected boolean remove;
    protected float speed;
    protected short collisionMask;

    public GameObject(String region, Vector2 position, World world, short collisionMask) {
        this.currentAnimation = new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region));
        pos = new Vector2(position.x * TILE_SIZE, position.y * TILE_SIZE);
        this.collisionMask = collisionMask;
        this.world = world;
    }

    public void render(Batch batch) {
        batch.draw(currentAnimation.getKeyFrame(stateTime, true), pos.x, pos.y);
    }

    public void tick(float delta) {
        stateTime += delta;
    }

    public Body getBody() {
        return body;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public Vector2 getPos() {
        return pos;
    }
}
