package de.dhbw.tinf22b6.world.tiled;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.Test;

/**
 * TiledRaycastCollisionDetectorTest
 */
public class TiledRaycastCollisionDetectorTest {
    private static int[][] noObstacles3x3Map = {
            { 1, 1, 1 },
            { 1, 1, 1 },
            { 1, 1, 1 },
    };
    private static int[][] horizontalWall3x3Map = {
            { 1, 1, 1 },
            { 2, 2, 2 },
            { 1, 1, 1 },
    };

    @Test
    public void testCollides_FreePath() {
        // Create a world map with no obstacles
        TiledGraph<FlatTiledNode> worldMap = createEmptyWorldMap();
        TiledRaycastCollisionDetector<FlatTiledNode> detector = new TiledRaycastCollisionDetector<>(worldMap);

        // Create a ray that passes through empty space
        Ray<Vector2> ray = createRay(new Vector2(0, 0), new Vector2(2, 2));

        assertFalse(detector.collides(ray));
    }

    @Test
    public void testCollides_FreePath_Reversed() {
        // Create a world map with no obstacles
        TiledGraph<FlatTiledNode> worldMap = createEmptyWorldMap();
        TiledRaycastCollisionDetector<FlatTiledNode> detector = new TiledRaycastCollisionDetector<>(worldMap);

        // Create a ray that passes through empty space
        Ray<Vector2> ray = createRay(new Vector2(2, 2), new Vector2(0, 0));

        assertFalse(detector.collides(ray));
    }

    @Test
    public void testCollides_FreePath_Steep() {
        // Create a world map with no obstacles
        TiledGraph<FlatTiledNode> worldMap = createEmptyWorldMap();
        TiledRaycastCollisionDetector<FlatTiledNode> detector = new TiledRaycastCollisionDetector<>(worldMap);

        // Create a ray that passes through empty space
        Ray<Vector2> ray = createRay(new Vector2(1, 2), new Vector2(0, 0));

        assertFalse(detector.collides(ray));
    }
    @Test
    public void testCollides_FreePath_yStep() {
        // Create a world map with no obstacles
        TiledGraph<FlatTiledNode> worldMap = createEmptyWorldMap();
        TiledRaycastCollisionDetector<FlatTiledNode> detector = new TiledRaycastCollisionDetector<>(worldMap);

        // Create a ray that passes through empty space
        Ray<Vector2> ray = createRay(new Vector2(1, 0), new Vector2(0, 2));

        assertFalse(detector.collides(ray));
    }
    @Test
    public void testCollides_WithWall() {
        // Create a world map with a wall
        TiledGraph<FlatTiledNode> worldMap = createWorldMapWithWall();
        TiledRaycastCollisionDetector<FlatTiledNode> detector = new TiledRaycastCollisionDetector<>(worldMap);

        // Create a ray that collides with the wall
        Ray<Vector2> ray = createRay(new Vector2(0, 0), new Vector2(2, 2));

        assertTrue(detector.collides(ray));
    }

    @Test
    public void testCollides_WithWall_Reversed() {
        // Create a world map with no obstacles
        TiledGraph<FlatTiledNode> worldMap = createWorldMapWithWall();
        TiledRaycastCollisionDetector<FlatTiledNode> detector = new TiledRaycastCollisionDetector<>(worldMap);

        // Create a ray that passes through empty space
        Ray<Vector2> ray = createRay(new Vector2(2, 2), new Vector2(0, 0));

        assertTrue(detector.collides(ray));
    }

    // Add more test methods for other scenarios...

    private TiledGraph<FlatTiledNode> createEmptyWorldMap() {
        return new FlatTiledGraph(noObstacles3x3Map);
    }

    private TiledGraph<FlatTiledNode> createWorldMapWithWall() {
        return new FlatTiledGraph(horizontalWall3x3Map);
    }

    private Ray<Vector2> createRay(Vector2 start, Vector2 end) {
        // Create a ray from start to end
        return new Ray<>(start, end);
    }
}
