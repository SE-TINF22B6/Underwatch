package de.dhbw.tinf22b6.world.tiled;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * FlatTiledConnectionTest
 */
public class FlatTiledConnectionTest {

        @Test
        public void testGetCostWithDiagonal() {
                // Arrange
                int[][] map = {
                                { 1, 1, 1 },
                                { 1, 1, 1 },
                                { 1, 1, 1 }
                };
                FlatTiledGraph worldMap = new FlatTiledGraph(map);
                worldMap.diagonal = true;
                FlatTiledNode fromNode = worldMap.getNode(0, 0);
                FlatTiledNode toNode = worldMap.getNode(1, 1);
                FlatTiledConnection connection = new FlatTiledConnection(worldMap, fromNode, toNode);

                // Act
                float cost = connection.getCost();

                // Assert
                assertEquals(1.0f, cost, 0.001f); // Diagonal cost should be 1
        }

        @Test
        public void testGetCostWithoutDiagonal() {
                // Arrange
                int[][] map = {
                                { 1, 1, 1 },
                                { 1, 1, 1 },
                                { 1, 1, 1 }
                };
                FlatTiledGraph worldMap = new FlatTiledGraph(map, false);
                FlatTiledNode fromNode = worldMap.getNode(0, 0);
                worldMap.startNode = fromNode;
                FlatTiledNode toNode = worldMap.getNode(2, 2);
                FlatTiledConnection connection = new FlatTiledConnection(worldMap, fromNode, toNode);

                // Act
                float cost = connection.getCost();

                // Assert
                assertEquals(Math.sqrt(2), cost, 0.001f); // Non-diagonal cost should be square root of 2
        }
}
