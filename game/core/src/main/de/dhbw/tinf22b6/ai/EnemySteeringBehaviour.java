package de.dhbw.tinf22b6.ai;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;

public class EnemySteeringBehaviour extends SteeringBehavior<Vector2> {
  public EnemySteeringBehaviour(Steerable<Vector2> owner) {
    super(owner);
  }

  @Override
  protected SteeringAcceleration<Vector2> calculateRealSteering(SteeringAcceleration<Vector2> steering) {
    SteeringAcceleration<Vector2> res = new SteeringAcceleration<>(new Vector2(100, 0));
    return res;
  }
}
