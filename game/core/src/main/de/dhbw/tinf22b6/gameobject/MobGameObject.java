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
    protected Map<Direction, Animation<TextureAtlas.AtlasRegion>> currentAnimations;
    protected Map<Direction, Animation<TextureAtlas.AtlasRegion>> idleAnimations;
    protected Map<Direction, Animation<TextureAtlas.AtlasRegion>> walkingAnimations;
    protected Direction currentDirection;

    public MobGameObject(String region, Vector2 position, World world, short collisionMask) {
        super(region + "_idle_front", position, world, collisionMask);
        this.idleAnimations = new HashMap<>();
        this.walkingAnimations = new HashMap<>();
        this.currentAnimations = this.idleAnimations;
        this.currentDirection = DOWN;

        this.idleAnimations.put(
                LEFT, new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region + "_idle_left")));
        this.idleAnimations.put(
                RIGHT, new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region + "_idle_right")));
        this.idleAnimations.put(
                UP, new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region + "_idle_back")));
        this.idleAnimations.put(
                DOWN, new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region + "_idle_front")));

        this.walkingAnimations.put(
                LEFT, new Animation<>(0.15f, Assets.instance.getAnimationAtlasRegion(region + "_walk_left")));
        this.walkingAnimations.put(
                RIGHT, new Animation<>(0.15f, Assets.instance.getAnimationAtlasRegion(region + "_walk_right")));
        this.walkingAnimations.put(
                UP, new Animation<>(0.15f, Assets.instance.getAnimationAtlasRegion(region + "_walk_back")));
        this.walkingAnimations.put(
                DOWN, new Animation<>(0.15f, Assets.instance.getAnimationAtlasRegion(region + "_walk_front")));
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        this.currentAnimation = this.currentAnimations.get(this.currentDirection);
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public void setWalking() {
        this.currentAnimations = this.walkingAnimations;
    }

    public void setIdle() {
        this.currentAnimations = this.idleAnimations;
    }
}
