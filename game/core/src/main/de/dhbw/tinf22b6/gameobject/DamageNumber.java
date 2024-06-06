package de.dhbw.tinf22b6.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

/**
 * DamaegeNumber
 */
public class DamageNumber {

    private final BitmapFont font; // Font used to render the text
    private final String text; // Text to display (damage amount)
    private final Vector2 position; // The position where the text should appear
    private float duration = .5f; // Duration for which the text will be visible
    private boolean left;

    public DamageNumber(String text, Vector2 position, boolean left) {
        this.font = new BitmapFont();
        font.getData().setScale(.4f);
        font.setColor(Color.WHITE);
        this.left = left;

        this.text = text;
        this.position = new Vector2(position);
    }

    public void update(float delta) {
        duration -= delta;

        if (!left) {
            position.x += Math.sqrt(position.x) * delta;
            position.y += Math.sqrt(position.y) * delta;
        }
        if (left) {
            position.x -= Math.sqrt(position.x) * delta;
            position.y += Math.sqrt(position.y) * delta;
        }
    }

    public void render(Batch batch) {
        if (duration > 0) {
            font.draw(batch, text, position.x - 6, position.y);
        }
    }

    public boolean isVisible() {
        return duration > 0;
    }
}
