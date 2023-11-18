package de.dhbw.tinf22b6.world;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import de.dhbw.tinf22b6.gameobject.AnimatedGameObject;

import static de.dhbw.tinf22b6.util.Constants.VIEWPORT_HEIGHT;
import static de.dhbw.tinf22b6.util.Constants.VIEWPORT_WIDTH;

public class WorldRenderer implements Disposable {
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final WorldController worldController;
    private final TiledMapRenderer renderer;
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final RayHandler rayHandler;
    private float stateTime = 0;

    public WorldRenderer(WorldController worldController, World world, TiledMap map) {
        this.worldController = worldController;
        this.world = world;
        box2DDebugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.6f);
        rayHandler.setShadows(true);
        rayHandler.setBlurNum(1);
        WorldParser.parseTorches(map, rayHandler);
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
        renderer = new OrthogonalTiledMapRenderer(map);
    }


    public void render() {
        renderer.setView(camera);
        renderer.render(new int[]{0, 1});
        renderMapObjects();

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        if (worldController.debugBox2D)
            box2DDebugRenderer.render(world, camera.combined);
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }


    private void renderMapObjects() {
        stateTime += Gdx.graphics.getDeltaTime();
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (AnimatedGameObject object : worldController.getGameObjects()) {
            batch.draw(object.getCurrentAnimation().getKeyFrame(stateTime, true), object.getPos().x, object.getPos().y);
        }
        batch.draw(worldController.getPlayer().getCurrentAnimation().getKeyFrame(stateTime, true), worldController.getPlayer().getPos().x, worldController.getPlayer().getPos().y);
        batch.end();
        world.clearForces();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (VIEWPORT_HEIGHT / height) * width;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        rayHandler.dispose();
    }
}
