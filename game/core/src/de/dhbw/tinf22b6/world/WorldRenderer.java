package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

import static de.dhbw.tinf22b6.util.Constants.*;

public class WorldRenderer implements Disposable {
    private static final String TAG = WorldRenderer.class.getName();
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private final WorldController worldController;
    private TiledMap map;
    private TiledMapRenderer renderer;
    private float stateTime = 0;
    private final World world;
    private Box2DDebugRenderer worldRenderer;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        this.world = worldController.getWorld();
        init();
    }

    private void init() {
        worldRenderer = new Box2DDebugRenderer();

        batch = new SpriteBatch();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
        map = new TmxMapLoader().load("level/2.0Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        parseMap();
    }

    public void render() {
        renderer.setView(camera);
        renderer.render();
        renderTestObjects();

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        worldRenderer.render(world, camera.combined);
    }

    private void renderTestObjects() {
        stateTime += Gdx.graphics.getDeltaTime();
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // draw Player
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
    }

    private void parseMap() {
        for (int i = 0; i < map.getLayers().size(); i++) {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(i);
            for (int x = 0; x < layer.getWidth(); x++) {
                for (int y = 0; y < layer.getHeight(); y++) {
                    TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                    if (cell == null)
                        continue;

                    MapObjects cellObjects = cell.getTile().getObjects();
                    if (cellObjects.getCount() != 1)
                        continue;

                    MapObject mapObject = cellObjects.get(0);

                    if (mapObject instanceof RectangleMapObject) {
                        RectangleMapObject rectangleObject = (RectangleMapObject) mapObject;
                        Rectangle rectangle = rectangleObject.getRectangle();

                        BodyDef bodyDef = getBodyDef(x * TILE_SIZE + TILE_SIZE / 2f + rectangle.getX() - (TILE_SIZE - rectangle.getWidth()) / 2f, y * TILE_SIZE + TILE_SIZE / 2f + rectangle.getY() - (TILE_SIZE - rectangle.getHeight()) / 2f);

                        Body body = world.createBody(bodyDef);
                        PolygonShape polygonShape = new PolygonShape();
                        polygonShape.setAsBox(rectangle.getWidth() / 2f, rectangle.getHeight() / 2f);
                        body.createFixture(polygonShape, 0.0f);
                        polygonShape.dispose();
                    } else if (mapObject instanceof EllipseMapObject) {
                        EllipseMapObject circleMapObject = (EllipseMapObject) mapObject;
                        Ellipse ellipse = circleMapObject.getEllipse();

                        BodyDef bodyDef = getBodyDef(x * TILE_SIZE + TILE_SIZE / 2f + ellipse.x, y * TILE_SIZE + TILE_SIZE / 2f + ellipse.y);

                        if (ellipse.width != ellipse.height)
                            Gdx.app.error(TAG, "Only circles are allowed." ,new IllegalArgumentException("Only circles are allowed."));

                        Body body = world.createBody(bodyDef);
                        CircleShape circleShape = new CircleShape();
                        circleShape.setRadius(ellipse.width / 2f);
                        body.createFixture(circleShape, 0.0f);
                        circleShape.dispose();
                    } else if (mapObject instanceof PolygonMapObject) {
                        PolygonMapObject polygonMapObject = (PolygonMapObject) mapObject;
                        Polygon polygon = polygonMapObject.getPolygon();

                        BodyDef bodyDef = getBodyDef(x * TILE_SIZE + polygon.getX(), y * TILE_SIZE + polygon.getY());

                        Body body = world.createBody(bodyDef);
                        PolygonShape polygonShape = new PolygonShape();
                        polygonShape.set(polygon.getVertices());
                        body.createFixture(polygonShape, 0.0f);
                        polygonShape.dispose();
                    }
                }
            }
        }
    }

    private BodyDef getBodyDef(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        return bodyDef;
    }
}
