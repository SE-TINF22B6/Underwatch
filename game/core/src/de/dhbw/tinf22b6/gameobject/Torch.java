package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Torch extends AnimatedGameObject {
    public Torch(World world, Vector2 position) {
        super("torch", position);

    }
}
