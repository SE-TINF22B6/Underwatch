package de.dhbw.tinf22b6.gameobject.enemy;

import com.badlogic.gdx.math.Vector2;

public class Skeleton extends Enemy {
    public Skeleton(Vector2 position, int[][] rawMap) {
        super("SS", position, rawMap, 25, 300);
    }
}
