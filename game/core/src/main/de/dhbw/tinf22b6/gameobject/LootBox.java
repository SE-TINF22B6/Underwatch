package de.dhbw.tinf22b6.gameobject;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;
import static de.dhbw.tinf22b6.world.WorldParser.getStaticBodyDef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.Constants;

public class LootBox extends GameObject {
    private final Animation<TextureAtlas.AtlasRegion> closedAnimation;
    private final Animation<TextureAtlas.AtlasRegion> openAnimation;

    public LootBox(Vector2 position, World world, Rectangle rectangle) {
        super("chest", position, world, Constants.BOX_INTERACTION_BIT);
        this.openAnimation = new Animation<>(0.2f, Assets.instance.getAnimationAtlasRegion("chest_open"));
        this.closedAnimation = currentAnimation;

        body = world.createBody(getStaticBodyDef(
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

    public void open() {
        this.currentAnimation = openAnimation;
        Gdx.audio
                .newSound(Gdx.files.internal("sfx/chest_open.mp3"))
                .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
    }

    public void close() {
        this.currentAnimation = closedAnimation;
    }

    @Override
    public void interact(Player player) {
        player.pickupWeapon();
        super.interact(player);
    }
}
