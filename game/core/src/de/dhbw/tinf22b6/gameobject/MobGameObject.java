package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Assets;

import java.util.HashMap;
import java.util.Map;

import static de.dhbw.tinf22b6.gameobject.Direction.*;

public abstract class MobGameObject extends GameObject {
    protected Animation<TextureAtlas.AtlasRegion> idleDAnimation;

    protected Map<Direction, Animation<TextureAtlas.AtlasRegion>> currentAnimations;
    protected Map<Direction, Animation<TextureAtlas.AtlasRegion>> idleAnimations;
    protected Map<Direction, Animation<TextureAtlas.AtlasRegion>> walkingAnimations;

    public MobGameObject(String region, Vector2 position, World world, short collisionMask) {
        super(region + "_idle_front", position, world, collisionMask);
        idleAnimations = new HashMap<>();
        walkingAnimations = new HashMap<>();
        currentAnimations = idleAnimations;

        idleAnimations.put(LEFT, new Animation<>(0.5f, Assets.instance.getAnimationAtlasRegion(region + "_idle_left")));
        idleAnimations.put(RIGHT, new Animation<>(0.5f, Assets.instance.getAnimationAtlasRegion(region + "_idle_right")));
        idleAnimations.put(UP, new Animation<>(0.5f, Assets.instance.getAnimationAtlasRegion(region + "_idle_back")));
        idleAnimations.put(DOWN, new Animation<>(0.5f, Assets.instance.getAnimationAtlasRegion(region + "_idle_front")));

        walkingAnimations.put(LEFT, new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region + "_walk_left")));
        walkingAnimations.put(RIGHT, new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region + "_walk_right")));
        walkingAnimations.put(UP, new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region + "_walk_back")));
        walkingAnimations.put(DOWN, new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region + "_walk_front")));
    }

    public void showAnimation(Direction direction) {
        currentAnimation = currentAnimations.get(direction);
    }

    public void setWalking() {
        currentAnimations = walkingAnimations;
    }

    public void setIdle() {
        currentAnimations = idleAnimations;
    }
}