package de.dhbw.tinf22b6;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import de.dhbw.tinf22b6.util.CameraHelper;
import de.dhbw.tinf22b6.world.tiled.FlatTiledGraph;
import de.dhbw.tinf22b6.world.tiled.FlatTiledNode;
import de.dhbw.tinf22b6.world.tiled.TiledRaycastCollisionDetector;
import de.dhbw.tinf22b6.world.tiled.TiledSmoothableGraphPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CameraTests {
    private CameraHelper cameraHelper;

    @BeforeEach
    void setUpTest() {
        this.cameraHelper = new CameraHelper();
    }

    @Test
    @DisplayName("Camera Initializes at the right position")
    void cameraInitHasRightPosition() {
        assertEquals(new Vector2(0, 0), this.cameraHelper.getPosition());
    }

    @ParameterizedTest(name = "translate to ({0},{1})")
    @CsvSource({
            "0,    1",
            "1,    2",
            "-49,  51",
            "1,  -100"
    })
    void cameraTranslationWorksCorrectly(int x, int y) {
        this.cameraHelper.setPosition(x, y);
        assertEquals(new Vector2(x, y), this.cameraHelper.getPosition());
    }

    @Test
    void testNavigation() {
        int[][] rawMap = new int[][]{
                new int[]{2, 2, 2, 2, 2, 2, 2},
                new int[]{2, 1, 1, 1, 1, 1, 2},
                new int[]{2, 1, 1, 1, 1, 1, 2},
                new int[]{2, 1, 1, 1, 1, 1, 2},
                new int[]{2, 1, 1, 1, 1, 1, 2},
                new int[]{2, 1, 1, 1, 1, 1, 2},
                new int[]{2, 2, 2, 2, 2, 2, 2}
        };
        FlatTiledGraph worldMap = new FlatTiledGraph(
                rawMap
        );

        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<>();
        Heuristic<FlatTiledNode> heuristic = (node, endNode) -> (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
        IndexedAStarPathFinder<FlatTiledNode> pathFinder = new IndexedAStarPathFinder<>(worldMap, true);
        PathSmoother<FlatTiledNode, Vector2> pathSmoother = new PathSmoother<>(new TiledRaycastCollisionDetector<FlatTiledNode>(worldMap));

        long startTime = TimeUtils.nanoTime();
        FlatTiledNode startNode = worldMap.getNode(1, 1);
        FlatTiledNode endNode = worldMap.getNode(5, 5);
        worldMap.startNode = startNode;

        pathFinder.searchNodePath(startNode, endNode, heuristic, path);

        if (pathFinder.metrics != null) {
            float elapsed = (TimeUtils.nanoTime() - startTime) / 1000000f;
            System.out.println("----------------- Indexed A* Path Finder Metrics -----------------");
            System.out.println("Visited nodes................... = " + pathFinder.metrics.visitedNodes);
            System.out.println("Open list additions............. = " + pathFinder.metrics.openListAdditions);
            System.out.println("Open list peak.................. = " + pathFinder.metrics.openListPeak);
            System.out.println("Path finding elapsed time (ms).. = " + elapsed);
        }

        for (int i = 0; i < path.nodes.size; i++) {
            System.out.println(path.nodes.get(i));
        }
    }
}