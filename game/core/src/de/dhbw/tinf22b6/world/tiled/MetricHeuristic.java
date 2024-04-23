package de.dhbw.tinf22b6.world.tiled;

import com.badlogic.gdx.ai.pfa.Heuristic;

/**
 * MetricHeuristic
 */
public class MetricHeuristic<N extends TiledNode<N>> implements Heuristic<N> {

    @Override
    public float estimate(N node, N endNode) {
        return (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
    }

}
