package de.dhbw.tinf22b6.gameobject.enemy;

import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Enemy;

public class Durgosh extends Enemy {
    public Durgosh(Vector2 position, int[][] rawMap) {
        super("O4", position, rawMap);
    }
}
