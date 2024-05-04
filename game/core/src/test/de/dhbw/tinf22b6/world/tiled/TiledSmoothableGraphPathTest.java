package de.dhbw.tinf22b6.world.tiled;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.Test;

/**
 * TiledSmoothableGraphPathTest
 */
public class TiledSmoothableGraphPathTest {

    @Test
    public void test_getNodePosition() {

        int[][] map = {
                { 1, 1, 1 },
                { 1, 2, 1 },
                { 1, 1, 1 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(map);
        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        Heuristic<FlatTiledNode> heuristic = (node, endNode) -> (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
        IndexedAStarPathFinder<FlatTiledNode> pathFinder = new IndexedAStarPathFinder<>(worldMap, true);
        PathSmoother<FlatTiledNode, Vector2> pathSmoother = new PathSmoother<>(new TiledRaycastCollisionDetector<FlatTiledNode>(worldMap));
        FlatTiledNode startNode = worldMap.getNode(0, 0);
        FlatTiledNode endNode = worldMap.getNode(2, 2);
        worldMap.startNode = startNode;
        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        assertEquals(path.getNodePosition(0), new Vector2(startNode.x, startNode.y));

    }

    @Test
    public void test_swapNodes() {

        int[][] map = {
                { 1, 1, 1 },
                { 1, 2, 1 },
                { 1, 1, 1 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(map);
        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        Heuristic<FlatTiledNode> heuristic = (node, endNode) -> (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
        IndexedAStarPathFinder<FlatTiledNode> pathFinder = new IndexedAStarPathFinder<>(worldMap, true);
        PathSmoother<FlatTiledNode, Vector2> pathSmoother = new PathSmoother<>(new TiledRaycastCollisionDetector<FlatTiledNode>(worldMap));
        FlatTiledNode startNode = worldMap.getNode(0, 0);
        FlatTiledNode endNode = worldMap.getNode(2, 2);
        worldMap.startNode = startNode;
        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        path.swapNodes(0, 1);
        assertEquals(path.getNodePosition(0), new Vector2(worldMap.getNode(1, 0).x, worldMap.getNode(1, 0).y));

    }

    @Test
    public void test_Truncate() {

        int[][] map = {
                { 1, 1, 1 },
                { 1, 2, 1 },
                { 1, 1, 1 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(map);
        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        Heuristic<FlatTiledNode> heuristic = (node, endNode) -> (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
        IndexedAStarPathFinder<FlatTiledNode> pathFinder = new IndexedAStarPathFinder<>(worldMap, true);
        PathSmoother<FlatTiledNode, Vector2> pathSmoother = new PathSmoother<>(new TiledRaycastCollisionDetector<FlatTiledNode>(worldMap));
        FlatTiledNode startNode = worldMap.getNode(0, 0);
        FlatTiledNode endNode = worldMap.getNode(2, 2);
        worldMap.startNode = startNode;
        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        path.truncatePath(2);
        assertEquals(path.nodes.size, 2);

    }
    @Test
    public void test_ToString() {

        int[][] map = {
                { 1, 1, 1 },
                { 1, 2, 1 },
                { 1, 1, 1 }
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(map);
        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        Heuristic<FlatTiledNode> heuristic = (node, endNode) -> (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
        IndexedAStarPathFinder<FlatTiledNode> pathFinder = new IndexedAStarPathFinder<>(worldMap, true);
        PathSmoother<FlatTiledNode, Vector2> pathSmoother = new PathSmoother<>(new TiledRaycastCollisionDetector<FlatTiledNode>(worldMap));
        FlatTiledNode startNode = worldMap.getNode(0, 0);
        FlatTiledNode endNode = worldMap.getNode(2, 2);
        worldMap.startNode = startNode;
        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        assertEquals(path.toString(), "TiledSmoothableGraphPath [nodes=[0 0, 1 0, 2 1, 2 2], tmpPosition=(0.0,0.0), getCount()=4");
    }
}
