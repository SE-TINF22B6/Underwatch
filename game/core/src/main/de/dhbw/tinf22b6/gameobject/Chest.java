package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.util.Constants;

public class Chest extends GameObject {
    public Chest(Vector2 position, World world) {
        super("chest", position, Constants.BOX_COLLISION_BIT);
    }
}
