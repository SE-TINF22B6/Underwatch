package de.dhbw.tinf22b6.world.gameObject;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.util.Assets;

public class AnimatedGameObject {
    protected final Animation<TextureAtlas.AtlasRegion> currentAnimation;
    protected Vector2 pos;

    public AnimatedGameObject(String region) {
        this(region, new Vector2(0, 0));
    }

    public AnimatedGameObject(String region, Vector2 position) {
        this.currentAnimation = new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region));
        System.out.println(currentAnimation.getKeyFrames().length);
        pos = new Vector2(position);
    }

    public Vector2 getPos() {
        return pos;
    }

    public Animation<TextureAtlas.AtlasRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
