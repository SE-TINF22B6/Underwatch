package de.dhbw.tinf22b6.gameobject.bullet;

import com.badlogic.gdx.math.Vector2;

public class MPBullet extends Bullet {
    public MPBullet(Vector2 position, float angle, int damage, short mask) {
        super("bullet7x13", position, angle, damage, mask);
        this.speed = 3;
        this.range = 1.5f;
    }
}