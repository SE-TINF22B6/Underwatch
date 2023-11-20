package de.dhbw.tinf22b6.world;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.dhbw.tinf22b6.gameobject.CandleStick;
import de.dhbw.tinf22b6.gameobject.Chest;
import de.dhbw.tinf22b6.gameobject.Coin;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.util.Constants;

import java.util.ArrayList;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class WorldParser {
    private static final String TAG = WorldRenderer.class.getName();

    public static void parseStaticObjects(TiledMap map, World world) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("walls");

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

                    BodyDef bodyDef = getStaticBodyDef(x * TILE_SIZE + TILE_SIZE / 2f + rectangle.getX() - (TILE_SIZE - rectangle.getWidth()) / 2f, y * TILE_SIZE + TILE_SIZE / 2f + rectangle.getY() - (TILE_SIZE - rectangle.getHeight()) / 2f);

                    Body body = world.createBody(bodyDef);
                    PolygonShape polygonShape = new PolygonShape();
                    polygonShape.setAsBox(rectangle.getWidth() / 2f, rectangle.getHeight() / 2f);
                    body.createFixture(getWallFixture(polygonShape));
                    polygonShape.dispose();
                } else if (mapObject instanceof EllipseMapObject) {
                    EllipseMapObject circleMapObject = (EllipseMapObject) mapObject;
                    Ellipse ellipse = circleMapObject.getEllipse();

                    BodyDef bodyDef = getStaticBodyDef(x * TILE_SIZE + TILE_SIZE / 2f + ellipse.x, y * TILE_SIZE + TILE_SIZE / 2f + ellipse.y);

                    if (ellipse.width != ellipse.height)
                        Gdx.app.error(TAG, "Only circles are allowed.", new IllegalArgumentException("Only circles are allowed."));

                    Body body = world.createBody(bodyDef);
                    CircleShape circleShape = new CircleShape();
                    circleShape.setRadius(ellipse.width / 2f);
                    body.createFixture(circleShape, 0.0f);
                    circleShape.dispose();
                } else if (mapObject instanceof PolygonMapObject) {
                    PolygonMapObject polygonMapObject = (PolygonMapObject) mapObject;
                    Polygon polygon = polygonMapObject.getPolygon();

                    BodyDef bodyDef = getStaticBodyDef(x * TILE_SIZE + polygon.getX(), y * TILE_SIZE + polygon.getY());

                    Body body = world.createBody(bodyDef);
                    PolygonShape polygonShape = new PolygonShape();
                    polygonShape.set(polygon.getVertices());
                    body.createFixture(polygonShape, 0.0f);
                    polygonShape.dispose();
                }
            }

        }
    }

    public static ArrayList<GameObject> parseGameObjects(TiledMap map, World world) {
        ArrayList<GameObject> list = new ArrayList<>();
        //TODO refactor animated game objects using an enum
        String[] objects = new String[]{"coins", "torch", "chests"};
        for (String s : objects) {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(s);
            for (int x = 0; x < layer.getWidth(); x++) {
                for (int y = 0; y < layer.getHeight(); y++) {
                    TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                    if (cell == null)
                        continue;

                    MapObjects cellObjects = cell.getTile().getObjects();
                    if (cellObjects.getCount() != 1)
                        continue;

                    MapObject cellObject = cellObjects.get(0);
                    if (cellObject instanceof RectangleMapObject) {
                        RectangleMapObject rectangleObject = (RectangleMapObject) cellObject;

                        switch (s) {
                            case "torch":
                                list.add(new CandleStick(new Vector2(x, y), world, rectangleObject.getRectangle()));
                                break;
                            case "coins":
                                list.add(new Coin(new Vector2(x, y), world, rectangleObject.getRectangle()));
                                break;
                            case "chests":
                                list.add(new Chest(new Vector2(x, y), world));
                                break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static void parseTorches(TiledMap map, RayHandler rayHandler) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("torch");
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null)
                    continue;

                MapObjects cellObjects = cell.getTile().getObjects();
                if (cellObjects.getCount() != 1)
                    continue;
                new PointLight(rayHandler, 100, new Color(0.95f, 0.37f, 0.07f, 1f), TILE_SIZE * 5, x * TILE_SIZE + 8, y * TILE_SIZE + 8).setSoftnessLength(10);
            }
        }
    }

    public static BodyDef getStaticBodyDef(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        return bodyDef;
    }

    public static BodyDef getDynamicBodyDef(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        return bodyDef;
    }

    public static BodyDef getDynamicBodyDef(float x, float y) {
        return getDynamicBodyDef(new Vector2(x, y));
    }

    private static FixtureDef getWallFixture(Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Constants.WALL_BIT;
        fixtureDef.restitution = 0.0f;
        fixtureDef.density = 0f;

        return fixtureDef;
    }
}
