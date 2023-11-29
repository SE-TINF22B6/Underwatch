package de.dhbw.tinf22b6.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.gameobject.Enemy;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

public class EnemyStateMachine {
    private static final String TAG = EnemyStateMachine.class.getName();
    private static final float COOLDOWN = 1;
    private EnemyState currentState;
    private final Enemy enemy;
    private final World world;
    float canShoot = 0;
    private Player player;
    private EnemyState oldState;

    public EnemyStateMachine(Enemy enemy, World world) {
        this.world = world;
        this.enemy = enemy;
        this.currentState = EnemyState.WALKING;
    }


    public void tick(float delta) {
        if (oldState != currentState) {
            Gdx.app.debug(TAG, currentState.name());
            oldState = currentState;
        }

        Vector2 moveVector = new Vector2();
        rayCast();
        canShoot -= delta;
        switch (currentState) {
            case WALKING:
                moveVector = getMovementVector();
                break;
            case RUNANDGUN:
                moveVector = getMovementVector();
                shoot();
                break;
            case SHOOTING:
                shoot();
                break;
        }
        enemy.applyForce(moveVector);
    }

    private Vector2 getMovementVector() {
        Vector2 tmp = new Vector2();
        Vector2 direction = new Vector2(player.getPos()).sub(enemy.getPos());
        if (direction.x < 0) tmp.x = -1;
        if (direction.y < 0) tmp.y = -1;
        if (direction.x > 0) tmp.x = 1;
        if (direction.y > 0) tmp.y = 1;
        return tmp;
    }

    private void shoot() {
        if (canShoot < 0) {
            Vector2 direction = new Vector2(player.getPos()).sub(enemy.getPos());
            EntitySystem.instance.add(new Bullet(enemy.getPos(), world, direction.setLength(1), Constants.WEAPON_ENEMY_BIT));
            canShoot = COOLDOWN;
        }
    }

    private void rayCast() {
        if (player != null) {
            world.rayCast((fixture, point, normal, fraction) -> {
                if (fixture.getFilterData().categoryBits == Constants.WALL_BIT) {
                    this.currentState = EnemyState.WALKING;
                    Gdx.app.debug(TAG, "Raycast with wall!");
                    return 0;
                }
                if (fixture.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    this.currentState = EnemyState.RUNANDGUN;
                    Gdx.app.debug(TAG, "Raycast with Player!");
                    return 0;
                }
                return -1;
            }, enemy.getBody().getPosition(), player.getBody().getPosition());
        }
    }

    public void setTarget(Player player) {
        this.player = player;
        if (player != null) {
            this.currentState = EnemyState.WALKING;
        } else {
            this.currentState = EnemyState.RUNANDGUN;
        }
    }
}
