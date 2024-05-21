package de.dhbw.tinf22b6.gameobject.enemy;

import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Enemy;

public class Grakor extends Enemy {
    public Grakor(Vector2 position, int[][] rawMap) {
        super("O3", position, rawMap);
    }
}
