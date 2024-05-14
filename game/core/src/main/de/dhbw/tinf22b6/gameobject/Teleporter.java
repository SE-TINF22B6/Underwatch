package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.world.WorldType;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;
import static de.dhbw.tinf22b6.world.WorldParser.getDynamicBodyDef;

public class Teleporter extends GameObject {
    private final String destination;

    public Teleporter(Vector2 position, World world, Rectangle rectangle, String destination) {
        super("keys_1", position, world, Constants.TELEPORTER_BIT);
        this.destination = destination;

        body = world.createBody(getDynamicBodyDef(
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

    public WorldType getDestination() {
        return WorldType.valueOf(destination);
    }
}
