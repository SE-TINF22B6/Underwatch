package de.dhbw.tinf22b6.gameobject;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;
import static de.dhbw.tinf22b6.world.WorldParser.getStaticBodyDef;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.world.Box2dWorld;

public class CandleStick extends GameObject {
    public CandleStick(Vector2 position, Rectangle rectangle) {
        super("candlestick_1", position, Constants.WALL_BIT);

        body = Box2dWorld.instance
                .getWorld()
                .createBody(getStaticBodyDef(
                        pos.x - 1 + TILE_SIZE / 2f + rectangle.getX() - (TILE_SIZE - rectangle.getWidth()) / 2f,
                        pos.y + TILE_SIZE / 2f + rectangle.getY() - (TILE_SIZE - rectangle.getHeight()) / 2f));
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(rectangle.getWidth() / 2f, rectangle.getHeight() / 2f);
        FixtureDef def = new FixtureDef();
        def.filter.categoryBits = collisionMask;
        def.shape = polygonShape;
        body.createFixture(def);
        polygonShape.dispose();
    }
}
