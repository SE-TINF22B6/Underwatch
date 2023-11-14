package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Constants;

public class Coin extends AnimatedCollisionObject {
    public Coin(Vector2 position, World world) {
        super("coin", position, world, Constants.COIN_BIT);
    }
}
