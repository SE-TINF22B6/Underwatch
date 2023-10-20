package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import de.dhbw.tinf22b6.util.Constants;

public class WorldRenderer implements Disposable {
    private static final String TAG = WorldRenderer.class.getName();
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;
    private TiledMap map;
    private TiledMapRenderer renderer;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
        map = new TmxMapLoader().load("level/Sample.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render() {
        renderTestObjects();
        renderer.setView(camera);
        renderer.render();
    }

    private void renderTestObjects() {
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Sprite sprite : worldController.testSprites) {
            sprite.draw(batch);
        }
        batch.end();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
