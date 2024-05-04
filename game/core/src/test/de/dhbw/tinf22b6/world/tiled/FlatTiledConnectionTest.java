package de.dhbw.tinf22b6.world.tiled;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
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

        // in the return statement we have four possible combinations:
        // |x|y|&&|
        // |1|0| 0|
        // |1|1| 1|
        // |0|0| 0|
        // |0|1| 0|
        @Test
        @DisplayName("Test with return statement resolving to false for true false")
        public void testGetCostWithoutDiagonal() {
                // Arrange
                int[][] map = {
                                { 1, 1, 1 },
                                { 1, 1, 1 },
                                { 1, 1, 1 }
                };
                FlatTiledGraph worldMap = new FlatTiledGraph(map, false);
                FlatTiledNode fromNode = worldMap.getNode(0, 0);
                worldMap.startNode = worldMap.getNode(1, 1);
                FlatTiledNode toNode = worldMap.getNode(0, 1);
                // getToNode().x!=worldMap.startNode.x => true
                // getToNode().y!=worldMap.startNode.y => false
                FlatTiledConnection connection = new FlatTiledConnection(worldMap, fromNode, toNode);

                // Act
                float cost = connection.getCost();

                // Assert
                assertEquals(1f, cost, 0.001f); // Non-diagonal cost should be square root of 2
        }

        @Test
        @DisplayName("Test with return statement resolving to true")
        public void test_GetCostWithoutDiagonalSecondCase() {

                // Arrange
                int[][] map = {
                                { 1, 1, 1 },
                                { 1, 1, 1 },
                                { 1, 1, 1 }
                };
                FlatTiledGraph worldMap = new FlatTiledGraph(map, false);
                FlatTiledNode fromNode = worldMap.getNode(1, 1);
                worldMap.startNode = worldMap.getNode(0, 0);
                // getToNode().x!=worldMap.startNode.x => true
                // getToNode().y!=worldMap.startNode.y => true
                FlatTiledNode toNode = worldMap.getNode(2, 2);
                FlatTiledConnection connection = new FlatTiledConnection(worldMap, fromNode, fromNode);

                // Act
                float cost = connection.getCost();

                // Assert
                assertEquals((float) Math.sqrt(2), cost, 0.001f); // Non-diagonal cost should be square root of 2
        }

        @Test
        @DisplayName("Test with return statement resolving to false false")
        public void test_GetCostWithoutDiagonalFalseFalse() {

                // Arrange
                int[][] map = {
                                { 1, 1, 1 },
                                { 1, 1, 1 },
                                { 1, 1, 1 }
                };
                FlatTiledGraph worldMap = new FlatTiledGraph(map, false);
                FlatTiledNode fromNode = worldMap.getNode(0, 0);
                worldMap.startNode = worldMap.getNode(0, 0);
                // getToNode().x!=worldMap.startNode.x => false
                // getToNode().y!=worldMap.startNode.y => false
                FlatTiledNode toNode = worldMap.getNode(0, 0);
                FlatTiledConnection connection = new FlatTiledConnection(worldMap, fromNode, toNode);

                // Act
                float cost = connection.getCost();

                // Assert
                assertEquals(1f, cost, 0.001f); // Non-diagonal cost should be square root of 2
        }

        @Test
        @DisplayName("Test with return statement resolving to false true")
        public void test_GetCostWithoutDiagonalFalseTrue() {

                // Arrange
                int[][] map = {
                                { 1, 1, 1 },
                                { 1, 1, 1 },
                                { 1, 1, 1 }
                };
                FlatTiledGraph worldMap = new FlatTiledGraph(map, false);
                FlatTiledNode fromNode = worldMap.getNode(1, 0);
                worldMap.startNode = worldMap.getNode(0, 0);
                // getToNode().x!=worldMap.startNode.x => false
                // getToNode().y!=worldMap.startNode.y => true
                FlatTiledNode toNode = worldMap.getNode(0, 1);
                FlatTiledConnection connection = new FlatTiledConnection(worldMap, fromNode, toNode);

                // Act
                float cost = connection.getCost();

                // Assert
                assertEquals(1f, cost, 0.001f); // Non-diagonal cost should be square root of 2
        }
}
