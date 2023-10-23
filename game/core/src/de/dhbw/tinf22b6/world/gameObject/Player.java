package de.dhbw.tinf22b6.world.gameObject;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import de.dhbw.tinf22b6.util.Assets;

public class Player {
    private Animation<TextureAtlas.AtlasRegion> currentAnimation;
    private float x, y;

    public Player() {
        this.currentAnimation = new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion("priest1_v1"));
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

    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public Animation<TextureAtlas.AtlasRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
