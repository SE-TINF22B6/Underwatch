package de.dhbw.tinf22b6.gameobject.interaction;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;
import static de.dhbw.tinf22b6.world.WorldParser.getStaticBodyDef;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.world.Box2dWorld;

public abstract class InteractionObject extends GameObject {
    protected final Animation<TextureAtlas.AtlasRegion> inactiveAnimation;
    protected final Animation<TextureAtlas.AtlasRegion> activeAnimation;

    public InteractionObject(String region, Vector2 position, Rectangle rectangle) {
        super(region, position, Constants.INTERACTION_BIT);
        this.activeAnimation = new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion(region + "_open"));
        this.inactiveAnimation = currentAnimation;

        body = Box2dWorld.instance
                .getWorld()
                .createBody(getStaticBodyDef(
                        pos.x + TILE_SIZE / 2f + rectangle.getX() - (TILE_SIZE - rectangle.getWidth()) / 2f,
                        pos.y + 1 + TILE_SIZE / 2f + rectangle.getY() - (TILE_SIZE - rectangle.getHeight()) / 2f));
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(rectangle.getWidth() / 2f, rectangle.getHeight() / 2f);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(rectangle.getWidth());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = Constants.WALL_BIT;

        FixtureDef fixtureActiveArea = new FixtureDef();
        fixtureActiveArea.shape = circleShape;
        fixtureActiveArea.isSensor = true;
        fixtureActiveArea.filter.categoryBits = collisionMask;

        body.createFixture(fixtureDef).setUserData(this);
        body.createFixture(fixtureActiveArea).setUserData(this);
        polygonShape.dispose();
        circleShape.dispose();
    }

    public void activate() {
        this.currentAnimation = activeAnimation;
    }

    public void deactivate() {
        this.currentAnimation = inactiveAnimation;
    }

    @Override
    public void interact(Player player) {
        super.interact(player);
    }
}
