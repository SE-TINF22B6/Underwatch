package de.dhbw.tinf22b6.gameobject.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.Enemy;

public class Morglak extends Enemy {
  public Morglak(Vector2 position, World world, int[][] rawMap) {
    super("O5", position, world, rawMap);
  }
}
