package de.dhbw.tinf22b6.gameobject.enemy;

import static de.dhbw.tinf22b6.gameobject.Direction.DOWN;
import static de.dhbw.tinf22b6.gameobject.Direction.LEFT;
import static de.dhbw.tinf22b6.gameobject.Direction.RIGHT;
import static de.dhbw.tinf22b6.gameobject.Direction.UP;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.DamageNumber;
import de.dhbw.tinf22b6.gameobject.Direction;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.util.Assets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class MobGameObject extends GameObject {
    protected List<DamageNumber> damageNumbers = new ArrayList<>();
    protected Map<Direction, Animation<TextureAtlas.AtlasRegion>> currentAnimations;
    protected Map<Direction, Animation<TextureAtlas.AtlasRegion>> idleAnimations;
    protected Map<Direction, Animation<TextureAtlas.AtlasRegion>> walkingAnimations;
    protected Direction currentDirection;
    protected boolean toggle = true;

    public MobGameObject(String region, Vector2 position, short collisionMask) {
        super(region + "_idle_front", position, collisionMask);
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
        updateDamageNumbers(delta);
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

    public void addDamageNumber(float damage) {
        // Assume you have a BitmapFont and color predefined somewhere, e.g.,
        // Assets.instance.font
        Vector2 position = new Vector2(
                body.getPosition().x,
                body.getPosition().y
                        + this.currentAnimation.getKeyFrame(0).originalHeight / 2.5f); // Position above the entity
        damageNumbers.add(new DamageNumber(String.valueOf(damage), position, toggle));
    }

    public void updateDamageNumbers(float delta) {
        Iterator<DamageNumber> iterator = damageNumbers.iterator();
        while (iterator.hasNext()) {
            DamageNumber damageNumber = iterator.next();
            damageNumber.update(delta);
            if (!damageNumber.isVisible()) {
                iterator.remove();
            }
        }
    }

    public void renderDamageNumbers(Batch batch) {
        for (DamageNumber damageNumber : damageNumbers) {
            damageNumber.render(batch);
        }
    }

    public void takeDamage(float damage) {
        toggle = !toggle;
        addDamageNumber(damage);
        // Apply other damage logic...
    }

    @Override
    public void render(Batch batch) {
        renderDamageNumbers(batch);
    }
}
