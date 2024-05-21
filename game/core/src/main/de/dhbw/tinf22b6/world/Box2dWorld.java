package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class Box2dWorld implements Disposable {
    private static final String TAG = Box2dWorld.class.getName();
    public static final Box2dWorld instance = new Box2dWorld();
    private World world;

    private Box2dWorld() {}

    public void init() {
        this.world = new World(new Vector2(0, 0), false);
        Gdx.app.debug(TAG, "initialized Box2dWorld");
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
