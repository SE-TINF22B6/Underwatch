package de.dhbw.tinf22b6.ai;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.gameobject.Enemy;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;
import de.dhbw.tinf22b6.world.tiled.FlatTiledGraph;
import de.dhbw.tinf22b6.world.tiled.FlatTiledNode;
import de.dhbw.tinf22b6.world.tiled.TiledManhattanDistance;
import de.dhbw.tinf22b6.world.tiled.TiledSmoothableGraphPath;

public class EnemyStateMachine {

    private static final String TAG = EnemyStateMachine.class.getName();
    private static final float COOLDOWN = 1;
    private final Enemy enemy;
    private final World world;
    float shootCountdown = 0;
    private EnemyState currentState;
    private EnemyState oldState;
    private FlatTiledGraph worldGraph;
    private IndexedAStarPathFinder<FlatTiledNode> finder;
    private Vector2 currentMovementVector;
    private Heuristic<FlatTiledNode> heuristic;

    // private
    // private NavigationGrid<GridCell> gridCells;
    // AStarGridFinder<GridCell> finder;
    private Vector2 checkPoint = new Vector2();
    private TiledSmoothableGraphPath<FlatTiledNode> path;

    public EnemyStateMachine(Enemy enemy, World world, int[][] rawMap) {
        this.world = world;
        this.enemy = enemy;
        this.currentState = EnemyState.WALKING;
        this.worldGraph = new FlatTiledGraph(rawMap);
        this.finder = new IndexedAStarPathFinder<>(worldGraph, true);
        this.path = new TiledSmoothableGraphPath<>();
        this.heuristic = new TiledManhattanDistance<>();
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
                // shoot();
                break;
            case SHOOTING:
                // shoot();
                break;
        }
        // enemy.applyForce(moveVector);
    }

    private Vector2 getMovementVector() {
        Player player = EntitySystem.instance.getPlayer();
        FlatTiledNode startNode = worldGraph.getNode((int) enemy.getPos().x / TILE_SIZE, (int) enemy.getPos().y / TILE_SIZE);
        FlatTiledNode endNode = worldGraph.getNode((int) player.getPos().x / TILE_SIZE, (int) player.getPos().y / TILE_SIZE);

        worldGraph.startNode = startNode;
        finder.searchNodePath(startNode, endNode, heuristic, path);


        if (path.nodes.size == 1) {
            return new Vector2(0, 0);
        }

        if (!inRange(path) || currentMovementVector == null) {
            currentMovementVector = new Vector2(path.get(1).x * TILE_SIZE, path.get(1).y * TILE_SIZE);
            currentMovementVector.sub(enemy.getPos());
            currentMovementVector.setLength(1);
            Gdx.app.debug(TAG, "New Vector: " + currentMovementVector);
        }
        return currentMovementVector;
    }

    private boolean inRange(TiledSmoothableGraphPath<FlatTiledNode> path) {
        // float tx = checkPoint.x;
        // float ty = checkPoint.y;
        // float ex = enemy.getPos().x;
        // float ey = enemy.getPos().y;
        // float sizeEnemy = (float) TILE_SIZE / 3;
        // Gdx.app.debug(TAG, "Enemy Pos: [" + tx + ", " + ty + "], [" + ex + ", " + ey
        // + "]");
        // return (Math.abs(tx - sizeEnemy) / 2 + tx == ex && Math.abs(ty - sizeEnemy) /
        // 2 + ty == ey);

        // return enemy.getPos().epsilonEquals(path.get(0).x * TILE_SIZE, path.get(0).y
        // * TILE_SIZE);
        int outerX1 = path.get(1).x * TILE_SIZE;
        int outerY1 = path.get(1).y * TILE_SIZE;
        int outerX2 = outerX1 + TILE_SIZE;
        int outerY2 = outerY1 + TILE_SIZE;

        int innerX1 = (int) enemy.getPos().x;
        int innerY1 = (int) enemy.getPos().y;
        int innerX2 = innerX1 + TILE_SIZE / 3;
        int innerY2 = innerY1 + TILE_SIZE / 3;
        return innerX1 >= outerX1 && innerY1 >= outerY1 && innerX2 <= outerX2 && innerY2 <= outerY2;
    }

    private void shoot() {
        if (shootCountdown < 0) {
            Player player = EntitySystem.instance.getPlayer();
            if (player != null) {
                Vector2 direction = new Vector2(player.getPos()).sub(enemy.getPos());
                EntitySystem.instance.add(new Bullet(
                        new Vector2(enemy.getPos().x + Constants.TILE_SIZE / 2f, enemy.getPos().y),
                        world,
                        direction.angleDeg(),
                        Constants.WEAPON_ENEMY_BIT));
                shootCountdown = COOLDOWN;
            }
        }
    }

    private void rayCast() {
        Player player = EntitySystem.instance.getPlayer();
        if (player != null) {
            world.rayCast(
                          (fixture, point, normal, fraction) -> {
                              if (fixture.getFilterData().categoryBits == Constants.WALL_BIT) {
                                  this.currentState = EnemyState.WALKING;
                                  return 0;
                              }
                              if (fixture.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                                  this.currentState = EnemyState.RUNANDGUN;
                                  return 0;
                              }
                              return -1;
                          },
                          enemy.getBody().getPosition(),
                          player.getBody().getPosition());
        }
    }
}
