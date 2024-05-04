package de.dhbw.tinf22b6.world.tiled;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * TiledManhattanDistanceTest
 */
public class TiledManhattanDistanceTest {

    @Test
    public void test_estimate() {

        // Arrange
        int[][] map = {
                { 1, 1, 1 },
                { 1, 2, 1 },
                { 1, 1, 1 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(map);

        // Act
        FlatTiledNode node = worldMap.getNode(1, 1);
        FlatTiledNode endNode = worldMap.getNode(2, 2);
        TiledManhattanDistance<FlatTiledNode> heuristic = new TiledManhattanDistance<>();
        assertEquals(heuristic.estimate(node, endNode), Math.abs(2 - 1) + Math.abs(2 - 1));
    }
}
