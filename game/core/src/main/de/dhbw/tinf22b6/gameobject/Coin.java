package de.dhbw.tinf22b6.gameobject;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;
import static de.dhbw.tinf22b6.world.WorldParser.getDynamicBodyDef;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.world.Box2dWorld;

public class Coin extends GameObject {
    public Coin(Vector2 position, Rectangle rectangle) {
        super("coin", position, Constants.COIN_BIT);

        body = Box2dWorld.instance
                .getWorld()
                .createBody(getDynamicBodyDef(
                        pos.x + TILE_SIZE / 2f + rectangle.getX() - (TILE_SIZE - rectangle.getWidth()) / 2f,
                        pos.y + 1 + TILE_SIZE / 2f + rectangle.getY() - (TILE_SIZE - rectangle.getHeight()) / 2f));
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(rectangle.getWidth() / 2f, rectangle.getHeight() / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = collisionMask;

        body.createFixture(fixtureDef).setUserData(this);
        polygonShape.dispose();
    }
}
