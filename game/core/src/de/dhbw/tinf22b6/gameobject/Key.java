package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Key extends AnimatedGameObject {
    public Key(World world, Vector2 position) {
        super("keys_1", position);

    }

}
