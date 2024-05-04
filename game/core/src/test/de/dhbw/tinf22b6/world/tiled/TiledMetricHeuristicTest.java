package de.dhbw.tinf22b6.world.tiled;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

import org.junit.jupiter.api.Test;

/**
 * TiledMetricHeuristicTest
 */
public class TiledMetricHeuristicTest {

    @Test
    public void test_estimate() {

        int[][] map = {
                { 1, 1, 1 },
                { 1, 1, 1 },
                { 1, 1, 1 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(map, false);

        // Act
        FlatTiledNode node = worldMap.getNode(1, 1);
        FlatTiledNode endNode = worldMap.getNode(2, 2);
        TiledMetricHeuristic<FlatTiledNode> heuristic = new TiledMetricHeuristic<>();
        assertEquals(heuristic.estimate(node, endNode), (float) Math.sqrt(Math.pow(1 - 2, 2) + Math.pow(1 - 2, 2)));
    }

}
