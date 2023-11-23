package de.dhbw.tinf22b6.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.gameobject.Enemy;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class EnemyStateMachine {
    private static final String TAG = EnemyStateMachine.class.getName();
    private static final float COOLDOWN = 2;
    private EnemyState currentState;
    private Enemy enemy;
    private World world;
    float canShoot = 0;

    public EnemyStateMachine(Enemy enemy, World world) {
        this.world = world;
        this.enemy = enemy;
        this.currentState = EnemyState.FIGHTING;
    }

    public void tick(float delta) {
        switch (currentState) {
            case IDLE:
                this.currentState = EnemyState.FIGHTING;
                break;
            case SEARCHING:
                break;
            case FIGHTING:
                canShoot -= delta;
                if (canShoot < 0) {
                    Vector2 direction = new Vector2(1, 1);
                    Gdx.app.debug(TAG, "Direction of bullet spawn: " + direction);
                    EntitySystem.instance.add(new Bullet(enemy.getPos(), world, direction.setLength(1), Constants.WEAPON_ENEMY_BIT));
                    canShoot = COOLDOWN;
                }
                break;
        }

    }
}
