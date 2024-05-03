package de.dhbw.tinf22b6.ai;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.gameobject.Enemy;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;
import de.dhbw.tinf22b6.world.tiled.FlatTiledGraph;
import de.dhbw.tinf22b6.world.tiled.FlatTiledNode;
import de.dhbw.tinf22b6.world.tiled.TiledSmoothableGraphPath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Heuristic;
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
    private int[][] initMap;
    private float timer;
    private Vector2 currentMovementVector;

    // private
    // private NavigationGrid<GridCell> gridCells;
    // AStarGridFinder<GridCell> finder;

    public EnemyStateMachine(Enemy enemy, World world, int[][] rawMap) {
        this.world = world;
        this.initMap = rawMap;
        this.enemy = enemy;
        this.currentState = EnemyState.WALKING;
        this.worldGraph = new FlatTiledGraph(rawMap);
        this.finder = new IndexedAStarPathFinder<>(worldGraph, true);
        // this.gridCells = grid;

        // finder = new AStarGridFinder<>(GridCell.class);
    }

    public void tick(float delta) {
        timer += delta;
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
        enemy.applyForce(moveVector);
    }

    private Vector2 getMovementVector() {
        Player player = EntitySystem.instance.getPlayer();
        FlatTiledNode startNode = worldGraph.getNode((int) enemy.getPos().x / TILE_SIZE, (int) enemy.getPos().y / TILE_SIZE);
        FlatTiledNode endNode = worldGraph.getNode((int) player.getPos().x / TILE_SIZE, (int) player.getPos().y / TILE_SIZE);

        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        Heuristic<FlatTiledNode> heuristic = (node, end) -> (float) Math.sqrt(Math.pow(node.x - end.x, 2) + Math.pow(node.y - end.y, 2));
        worldGraph.startNode = startNode;
        finder.searchNodePath(startNode, endNode, heuristic, path);

        int[][] rawMap = new int[initMap.length][initMap[0].length];
        for (int x = 0; x < initMap.length; x++) {
            for (int y = 0; y < initMap[x].length; y++) {
                rawMap[x][y] = initMap[x][y];
            }
        }
        System.out.println("#########################");
        for (int i = path.getCount() - 1; i > 0; i--) {
            FlatTiledNode node = path.get(i);
            rawMap[node.x][node.y] = 8;
        }
        rawMap[(int) player.getPos().x / TILE_SIZE][(int) player.getPos().y / TILE_SIZE] = 0;
        rawMap[(int) enemy.getPos().x / TILE_SIZE][(int) enemy.getPos().y / TILE_SIZE] = 5;

        printRawMap(rawMap);

        if (path.nodes.size == 1) {
            return new Vector2(0, 0);
        }
        // if ((int) enemy.getPos().x / TILE_SIZE != path.get(0).x * TILE_SIZE && (int)
        // enemy.getPos().y / TILE_SIZE != path.get(0).y * TILE_SIZE) {
        // tmp = new Vector2(path.get(0).x * TILE_SIZE + (float) TILE_SIZE / 2,
        // path.get(0).y * TILE_SIZE + (float) TILE_SIZE / 1.3f);
        // Gdx.app.debug(TAG, "Chasing old Node");
        // } else {
        // tmp = new Vector2(path.get(1).x * TILE_SIZE + (float) TILE_SIZE / 2,
        // path.get(1).y * TILE_SIZE + (float) TILE_SIZE / 1.3f);
        // Gdx.app.debug(TAG, "Chasing new Node");
        // }

        // float x = path.get(1).x * TILE_SIZE - enemy.getPos().x;
        // float y = path.get(1).y * TILE_SIZE - enemy.getPos().y;
        // System.out.printf("x=%.2f\ty=%.2f%n", x, y);
        // float scaleX = 2;
        // float scaleY = 2;
        // if (x < 0 && y > 0) {
        // scaleX = 2.3f;
        // }
        // if (x < 0 && y < 0) {
        // scaleX = 1.3f;
        // }
        // if (x > 0 && y < 0) {
        // scaleY = 1.3f;
        // }
        // if (x > 0 && y > 0) {
        // scaleY = 1.3f;
        // }
        // tmp = new Vector2(x + (float) TILE_SIZE / scaleX, y + (float) TILE_SIZE /
        // scaleY);

        if ((timer > 0.5f || inRange(path)) || currentMovementVector == null) {

            timer = 0;
            currentMovementVector = new Vector2(path.get(1).x * TILE_SIZE + (float) TILE_SIZE / 2,
                    path.get(1).y * TILE_SIZE + (float) TILE_SIZE / 2f);
            currentMovementVector.sub(enemy.getPos());
            currentMovementVector.setLength(1);
            Gdx.app.debug(TAG, "New Vector: " + currentMovementVector);
        }
        // Gdx.app.debug(TAG, "Direction: " + currentMovementVector);
        return currentMovementVector;
    }

    private boolean inRange(TiledSmoothableGraphPath<FlatTiledNode> path) {
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

    private void printRawMap(int[][] rawMap) {
        for (int j = rawMap[0].length - 1; j > 0; j--) {
            for (int i = 0; i < rawMap.length; i++) {
                System.out.printf("%d", rawMap[i][j]);
            }
            System.out.println();
        }
    }

    private void shoot() {
        if (shootCountdown < 0) {
            Player player = EntitySystem.instance.getPlayer();
            if (player != null) {
                Vector2 direction = new Vector2(player.getPos()).sub(enemy.getPos());
                EntitySystem.instance.add(new Bullet(new Vector2(enemy.getPos().x + Constants.TILE_SIZE / 2f, enemy.getPos().y), world, direction.angleDeg(), Constants.WEAPON_ENEMY_BIT));
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
