package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Coin extends AnimatedGameObject{

    public Coin(World world, Vector2 position) {
        super("coin", position);

    }

}
