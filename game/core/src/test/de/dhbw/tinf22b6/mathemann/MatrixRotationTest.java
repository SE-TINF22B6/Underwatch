package de.dhbw.tinf22b6.mathemann;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixRotationTest {
  @Test
  public void testRotation() {
    Affine2 affine2 = new Affine2().setToRotation(45);
    Affine2 translate = new Affine2().setToTranslation(10,10);
    for (int i = 0; i < 365; i++) {
      Vector2 tmp = new Vector2(i, MathUtils.cos(i));
      affine2.applyTo(tmp);
      translate.applyTo(tmp);
      System.out.println(tmp);
    }
    assertEquals(1,0);
  }
}
