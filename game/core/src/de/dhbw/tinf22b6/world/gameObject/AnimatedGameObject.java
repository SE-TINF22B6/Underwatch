package de.dhbw.tinf22b6.world.gameObject;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import de.dhbw.tinf22b6.util.Assets;

public class AnimatedGameObject {
    protected final Animation<TextureAtlas.AtlasRegion> currentAnimation;
    protected float x, y;

    public AnimatedGameObject(String region) {
        this.currentAnimation = new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region));
        System.out.println(currentAnimation.getKeyFrames().length);
        this.x = 16 * 10;
        this.y = 16 * 10;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Animation<TextureAtlas.AtlasRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
