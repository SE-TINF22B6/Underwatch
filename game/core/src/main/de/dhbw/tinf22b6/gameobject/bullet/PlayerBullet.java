package de.dhbw.tinf22b6.gameobject.bullet;

import com.badlogic.gdx.math.Vector2;

public class PlayerBullet extends Bullet {

    public PlayerBullet(Vector2 position, float angle, int damage, short mask) {
        super("bullet7x13", position, angle, damage, mask);
    }

}
