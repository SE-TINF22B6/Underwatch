package de.dhbw.tinf22b6.gameobject.enemy;

import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Enemy;

public class Snarg extends Enemy {
    public Snarg(Vector2 position, int[][] rawMap) {
        super("O1", position, rawMap);
    }
}
