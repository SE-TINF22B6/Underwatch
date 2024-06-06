package de.dhbw.tinf22b6.world;

import static de.dhbw.tinf22b6.util.Constants.VIEWPORT_HEIGHT;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.util.EntitySystem;
import java.util.ArrayList;
import java.util.List;

public class WorldRenderer implements Disposable {
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final WorldController worldController;
    private final TiledMapRenderer renderer;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final RayHandler rayHandler;
    private final int[] renderBelow;
    private final int[] renderAbove;
    private boolean inside;

    public WorldRenderer(WorldController worldController, TiledMap map, OrthographicCamera camera) {
        this.worldController = worldController;
        box2DDebugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        rayHandler = new RayHandler(Box2dWorld.instance.getWorld());
        RayHandler.useDiffuseLight(true);
        rayHandler.setShadows(true);
        rayHandler.setBlurNum(1);
        WorldParser.parseTorches(map, rayHandler);
        this.camera = camera;
        camera.position.set(0, 0, 0);
        camera.update();
        renderer = new OrthogonalTiledMapRenderer(map);

        List<Integer> tmpBelow = new ArrayList<>();
        List<Integer> tmpAbove = new ArrayList<>();
        for (int i = 0; i < map.getLayers().size(); i++) {
            if (map.getLayers().get(i).getProperties().containsKey("render")) {
                if (map.getLayers().get(i).getProperties().get("render").equals("below")) {
                    tmpBelow.add(i);
                } else if (map.getLayers().get(i).getProperties().get("render").equals("above")) {
                    tmpAbove.add(i);
                }
            }
        }
        this.renderBelow = tmpBelow.stream().mapToInt(i -> i).toArray();
        this.renderAbove = tmpAbove.stream().mapToInt(i -> i).toArray();
        if (map.getProperties().containsKey("inside")) {
            if ((Boolean) map.getProperties().get("inside")) {
                inside = true;
                rayHandler.setAmbientLight(0.5f, 0.5f, 0.5f, 1f);
            } else {
                rayHandler.setAmbientLight(1f, 1f, 1f, 0.5f);
            }
        } else {
            rayHandler.setAmbientLight(Color.ORANGE);
        }
    }

    public void render() {
        if (inside) {
            Gdx.gl.glClearColor(0, 0, 0, 0);
        } else {
            Gdx.gl.glClearColor(83 / 255f, 160 / 255f, 58 / 255f, 1);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);

        renderer.render(renderBelow);
        renderMapObjects();
        renderer.render(renderAbove);
        if (worldController.debugBox2D) box2DDebugRenderer.render(Box2dWorld.instance.getWorld(), camera.combined);
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    private void renderMapObjects() {
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (GameObject object : EntitySystem.instance.getGameObjects()) {
            object.render(batch);
        }
        batch.end();
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
