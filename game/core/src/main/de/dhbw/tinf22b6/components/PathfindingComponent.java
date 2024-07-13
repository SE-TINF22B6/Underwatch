package de.dhbw.tinf22b6.components;

import de.dhbw.tinf22b6.world.tiled.FlatTiledGraph;
import de.dhbw.tinf22b6.world.tiled.FlatTiledNode;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;

/**
 * PathfindingComponent
 */
public class PathfindingComponent implements Component{
    public SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
    public IndexedAStarPathFinder<FlatTiledNode> finder;
    public Heuristic<FlatTiledNode> heuristic;
    public FlatTiledGraph worldGraph;
    protected SteeringBehavior<Vector2> steeringBehavior;
}
