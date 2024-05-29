package de.dhbw.tinf22b6.gameobject;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

import de.dhbw.tinf22b6.util.Assets;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameObject {
    protected Animation<TextureAtlas.AtlasRegion> currentAnimation;
    protected Body body;
    protected float width, height;
    protected Sound sound;
    protected Vector2 pos;
    protected float stateTime;
    protected boolean remove;
    protected short collisionMask;

    public GameObject(String region, Vector2 position, short collisionMask) {
        this.currentAnimation = new Animation<>(0.5f, Assets.instance.getAnimationAtlasRegion(region));
        pos = new Vector2(position.x * TILE_SIZE, position.y * TILE_SIZE);
        this.collisionMask = collisionMask;
    }

    public void render(Batch batch) {
        batch.draw(
                   currentAnimation.getKeyFrame(stateTime, true),
                   body.getPosition().x - currentAnimation.getKeyFrame(0).originalWidth / 2f,
                   body.getPosition().y - currentAnimation.getKeyFrame(0).originalHeight / 2f);
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

    public void interact(Player player) {
        this.setRemove(true);
    }
}
