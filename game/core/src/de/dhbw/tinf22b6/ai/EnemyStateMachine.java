package de.dhbw.tinf22b6.ai;

import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.gameobject.Enemy;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;
import de.dhbw.tinf22b6.world.tiled.FlatTiledGraph;
import de.dhbw.tinf22b6.world.tiled.FlatTiledNode;
import de.dhbw.tinf22b6.world.tiled.TiledManhattanDistance;
import de.dhbw.tinf22b6.world.tiled.TiledSmoothableGraphPath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class EnemyStateMachine {

    private static final String TAG = EnemyStateMachine.class.getName();
    private static final float COOLDOWN = 1;
    private EnemyState currentState;
    private final Enemy enemy;
    private final World world;
    float shootCountdown = 0;
    private EnemyState oldState;
    private FlatTiledGraph worldGraph;
    private IndexedAStarPathFinder<FlatTiledNode> finder;

    // private
    // private NavigationGrid<GridCell> gridCells;
    // AStarGridFinder<GridCell> finder;

    public EnemyStateMachine(Enemy enemy, World world, int[][] rawMap) {
        this.world = world;
        this.enemy = enemy;
        this.currentState = EnemyState.WALKING;
        this.worldGraph = new FlatTiledGraph(rawMap);
        this.finder = new IndexedAStarPathFinder<>(worldGraph, true);
        // this.gridCells = grid;

        // finder = new AStarGridFinder<>(GridCell.class);
    }

    public void tick(float delta) {
        if (oldState != currentState) {
            Gdx.app.debug(TAG, currentState.name());
            oldState = currentState;
        }

        Vector2 moveVector = new Vector2();
        rayCast();
        shootCountdown -= delta;
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
        Player player = EntitySystem.instance.getPlayer();
        FlatTiledNode startNode = worldGraph.getNode((int) enemy.getPos().x, (int) enemy.getPos().y);
        FlatTiledNode endNode = worldGraph.getNode((int) player.getPos().x, (int) player.getPos().y);

        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        finder.searchNodePath(startNode, endNode, new TiledManhattanDistance<>(), path);
        // List<GridCell> path = finder.findPath((int) enemy.getPos().x / 16,
        // (int) enemy.getPos().y / 16,
        // (int) player.getPos().x / 16,
        // (int) player.getPos().y / 16, gridCells);
        // Gdx.app.debug(TAG, "Path: " + path);
        // Gdx.app.debug(TAG, "Enemy: " + enemy.getPos().x / 16 + "," + enemy.getPos().y
        // / 16);
        Vector2 tmp = new Vector2(path.get(0).x * 16, path.get(0).y * 16);
        tmp.sub(enemy.getPos());
        tmp.setLength(1);
        // Gdx.app.debug(TAG, "Player: " + player.getPos() + " Enemy: " +
        enemy.getPos();
        // + " Dir" + tmp + " 1st" + path.get(0));
        return tmp;
    }

    private void shoot() {
        if (shootCountdown < 0) {
            Player player = EntitySystem.instance.getPlayer();
            if (player != null) {
                Vector2 direction = new Vector2(player.getPos()).sub(enemy.getPos());
                EntitySystem.instance.add(new Bullet(new Vector2(enemy.getPos().x + Constants.TILE_SIZE / 2f, enemy.getPos().y), world, direction.setLength(1), Constants.WEAPON_ENEMY_BIT));
                shootCountdown = COOLDOWN;
            }
        }
    }

    private void rayCast() {
        Player player = EntitySystem.instance.getPlayer();
        if (player != null) {
            world.rayCast((fixture, point, normal, fraction) -> {
                if (fixture.getFilterData().categoryBits == Constants.WALL_BIT) {
                    this.currentState = EnemyState.WALKING;
                    return 0;
                }
                if (fixture.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    this.currentState = EnemyState.RUNANDGUN;
                    return 0;
                }
                return -1;
            }, enemy.getBody().getPosition(), player.getBody().getPosition());
        }
    }

}
