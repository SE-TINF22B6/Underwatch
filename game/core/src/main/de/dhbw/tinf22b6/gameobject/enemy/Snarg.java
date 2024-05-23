package de.dhbw.tinf22b6.gameobject.enemy;

import com.badlogic.gdx.math.Vector2;

public class Snarg extends Enemy {
    public Snarg(Vector2 position, int[][] rawMap) {
        super("O1", position, rawMap, 20, 150);
    }
}
