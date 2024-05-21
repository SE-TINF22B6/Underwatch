package de.dhbw.tinf22b6.gameobject.enemy;

import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Enemy;

public class Skeleton extends Enemy {
    public Skeleton(Vector2 position, int[][] rawMap) {
        super("SS", position, rawMap);
    }
}
