package de.dhbw.tinf22b6.gameobject.enemy;

import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Enemy;

public class Grommok extends Enemy {
    public Grommok(Vector2 position, int[][] rawMap) {
        super("O2", position, rawMap);
    }
}
