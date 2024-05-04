package de.dhbw.tinf22b6.world.tiled;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * FlatTiledGraphTest
 */
public class FlatTiledGraphTest {

    @Test
    @DisplayName("Test A* No Obstructions")
    void testNavigation() {
        int[][] rawMap = new int[][] {
                new int[] { 2, 2, 2, 2, 2, 2, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 2, 2, 2, 2, 2, 2 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(rawMap);

        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        Heuristic<FlatTiledNode> heuristic = (node, endNode) -> (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
        IndexedAStarPathFinder<FlatTiledNode> pathFinder = new IndexedAStarPathFinder<>(worldMap, true);
        PathSmoother<FlatTiledNode, Vector2> pathSmoother = new PathSmoother<>(new TiledRaycastCollisionDetector<FlatTiledNode>(worldMap));

        FlatTiledNode startNode = worldMap.getNode(1, 1);
        FlatTiledNode endNode = worldMap.getNode(5, 5);
        worldMap.startNode = startNode;

        long startTime = TimeUtils.nanoTime();
        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        float elapsed = (TimeUtils.nanoTime() - startTime) / 1000000f;
        printPathFinderMetrics(pathFinder, elapsed);

        for (int i = 0; i < path.getCount(); i++) {
            FlatTiledNode node = path.get(i);
            rawMap[node.x][node.y] = 8;
        }

        for (int[] ints : rawMap) {
            Arrays.stream(ints).forEach(value -> System.out.print(value + " "));
            System.out.println();
        }
    }

    @Test
    @DisplayName("Test A* With Obstructions")
    void testNavigation_2() {
        int[][] rawMap = new int[][] {
                new int[] { 2, 2, 2, 2, 2, 2, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 1, 1, 1, 2, 1, 2 },
                new int[] { 2, 1, 1, 1, 2, 1, 2 },
                new int[] { 2, 2, 2, 2, 2, 1, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 2, 2, 2, 2, 2, 2 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(rawMap);

        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        Heuristic<FlatTiledNode> heuristic = (node, endNode) -> (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
        IndexedAStarPathFinder<FlatTiledNode> pathFinder = new IndexedAStarPathFinder<>(worldMap, true);
        PathSmoother<FlatTiledNode, Vector2> pathSmoother = new PathSmoother<>(new TiledRaycastCollisionDetector<FlatTiledNode>(worldMap));

        FlatTiledNode startNode = worldMap.getNode(1, 1);
        FlatTiledNode endNode = worldMap.getNode(5, 5);
        worldMap.startNode = startNode;

        long startTime = TimeUtils.nanoTime();
        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        float elapsed = (TimeUtils.nanoTime() - startTime) / 1000000f;
        printPathFinderMetrics(pathFinder, elapsed);

        for (int i = 0; i < path.getCount(); i++) {
            FlatTiledNode node = path.get(i);
            rawMap[node.x][node.y] = 8;
        }

        for (int[] ints : rawMap) {
            Arrays.stream(ints).forEach(value -> System.out.print(value + " "));
            System.out.println();
        }
    }

    @Test
    @DisplayName("Test A* With No valid Path")
    void testNavigation_3() {
        int[][] rawMap = new int[][] {
                new int[] { 2, 2, 2, 2, 2, 2, 2 },
                new int[] { 2, 1, 1, 1, 2, 1, 2 },
                new int[] { 2, 1, 1, 1, 2, 1, 2 },
                new int[] { 2, 1, 1, 1, 2, 1, 2 },
                new int[] { 2, 2, 2, 2, 2, 1, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 2, 2, 2, 2, 2, 2 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(rawMap);

        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        Heuristic<FlatTiledNode> heuristic = (node, endNode) -> (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
        IndexedAStarPathFinder<FlatTiledNode> pathFinder = new IndexedAStarPathFinder<>(worldMap, true);
        PathSmoother<FlatTiledNode, Vector2> pathSmoother = new PathSmoother<>(new TiledRaycastCollisionDetector<FlatTiledNode>(worldMap));

        FlatTiledNode startNode = worldMap.getNode(1, 1);
        FlatTiledNode endNode = worldMap.getNode(5, 5);
        // worldMap.startNode = startNode;

        long startTime = TimeUtils.nanoTime();
        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        float elapsed = (TimeUtils.nanoTime() - startTime) / 1000000f;
        printPathFinderMetrics(pathFinder, elapsed);

        for (int i = 0; i < path.getCount(); i++) {
            FlatTiledNode node = path.get(i);
            rawMap[node.x][node.y] = 8;
        }

        for (int[] ints : rawMap) {
            Arrays.stream(ints).forEach(value -> System.out.print(value + " "));
            System.out.println();
        }
    }

    @Test
    @DisplayName("Test A* With some block in the middle Path")
    void testNavigation_4() {
        int[][] rawMap = new int[][] {
                new int[] { 2, 2, 2, 2, 2, 2, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 1, 2, 1, 1, 1, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 1, 1, 1, 1, 1, 2 },
                new int[] { 2, 2, 2, 2, 2, 2, 2 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(rawMap);

        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        Heuristic<FlatTiledNode> heuristic = (node, endNode) -> (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
        IndexedAStarPathFinder<FlatTiledNode> pathFinder = new IndexedAStarPathFinder<>(worldMap, true);
        PathSmoother<FlatTiledNode, Vector2> pathSmoother = new PathSmoother<>(new TiledRaycastCollisionDetector<FlatTiledNode>(worldMap));

        FlatTiledNode startNode = worldMap.getNode(1, 1);
        FlatTiledNode endNode = worldMap.getNode(5, 5);
        // worldMap.startNode = startNode;

        long startTime = TimeUtils.nanoTime();
        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        float elapsed = (TimeUtils.nanoTime() - startTime) / 1000000f;
        printPathFinderMetrics(pathFinder, elapsed);

        for (int i = 0; i < path.getCount(); i++) {
            FlatTiledNode node = path.get(i);
            rawMap[node.x][node.y] = 8;
        }

        for (int[] ints : rawMap) {
            Arrays.stream(ints).forEach(value -> System.out.print(value + " "));
            System.out.println();
        }
    }

    private void printPathFinderMetrics(IndexedAStarPathFinder<FlatTiledNode> pathFinder, float elapsed) {
        if (pathFinder.metrics != null) {
            System.out.println("----------------- Indexed A* Path Finder Metrics -----------------");
            System.out.println("Visited nodes................... = " + pathFinder.metrics.visitedNodes);
            System.out.println("Open list additions............. = " + pathFinder.metrics.openListAdditions);
            System.out.println("Open list peak.................. = " + pathFinder.metrics.openListPeak);
            System.out.println("Path finding elapsed time (ms).. = " + elapsed);
        }
    }

    @Test
    @DisplayName("Initialization with Diagonal Connections")
    public void testInitWithDiagonal() {
        // Arrange
        int[][] map = {
                { 1, 1, 1 },
                { 1, 2, 1 },
                { 1, 1, 1 }
        };

        // Act
        FlatTiledGraph worldMap = new FlatTiledGraph(map);
        worldMap.startNode = worldMap.getNode(0, 0);

        // Assert
        assertNotNull(worldMap); // Ensure world map is initialized
        assertEquals(3, worldMap.sizeX); // Check sizeX
        assertEquals(3, worldMap.sizeY); // Check sizeY
        assertTrue(worldMap.diagonal); // Diagonal should be true by default
        assertNotNull(worldMap.startNode); // Start node should not be null
        assertEquals(FlatTiledNode.class, worldMap.startNode.getClass()); // Start node should be of type FlatTiledNode
        assertEquals(9, worldMap.getNodeCount()); // Total nodes should match sizeX * sizeY
    }

    @Test
    @DisplayName("Disable Diagonal Connections")
    public void testInitWithoutDiagonal() {
        // Arrange
        int[][] map = {
                { 1, 1, 1 },
                { 1, 2, 1 },
                { 1, 1, 1 }
        };

        // Act
        FlatTiledGraph worldMap = new FlatTiledGraph(map);
        worldMap.diagonal = false;

        // Assert
        assertFalse(worldMap.diagonal); // Diagonal should be false now
    }

    @Test
    @DisplayName("Retrieve Node from Map")
    public void testGetNode() {
        // Arrange
        int[][] map = {
                { 1, 1, 1 },
                { 1, 2, 1 },
                { 1, 1, 1 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(map);

        // Act
        FlatTiledNode node = worldMap.getNode(1, 1);

        // Assert
        assertNotNull(node); // Node should not be null
        assertEquals(1, node.x); // Node's x-coordinate should match
        assertEquals(1, node.y); // Node's y-coordinate should match
        assertEquals(2, node.type); // Node's type should be 2 (wall)
    }

    @Test
    @DisplayName("Retrieve Connections for a Node")
    public void testGetConnections() {
        // Arrange
        int[][] map = {
                { 1, 1, 1 },
                { 1, 1, 1 },
                { 1, 1, 1 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(map);
        worldMap.diagonal = false;

        // Act
        FlatTiledNode node = worldMap.getNode(1, 1);
        Array<Connection<FlatTiledNode>> connections = worldMap.getConnections(node);

        // Assert
        assertNotNull(connections); // Connections should not be null
        assertEquals(8, connections.size); // The connections are always 8
    }

}
