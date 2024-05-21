package de.dhbw.tinf22b6.gameobject.enemy;

import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Enemy;

public class SkeletonHat extends Enemy {
    public SkeletonHat(Vector2 position, int[][] rawMap) {
        super("SH", position, rawMap);
    }
}
