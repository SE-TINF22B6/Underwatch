package de.dhbw.tinf22b6.world;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

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
import de.dhbw.tinf22b6.gameobject.*;
import de.dhbw.tinf22b6.gameobject.enemy.*;
import de.dhbw.tinf22b6.gameobject.interaction.WeaponBox;
import de.dhbw.tinf22b6.util.Constants;
import java.util.ArrayList;
import java.util.Random;

public class WorldParser {
    static final int TILE_EMPTY = 0;
    static final int TILE_FLOOR = 1;
    static final int TILE_WALL = 2;
    private static final String TAG = WorldRenderer.class.getName();

    public static void parseStaticObjects(TiledMap map) {
        World world = Box2dWorld.instance.getWorld();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("walls");
        if (layer == null) {
            return;
        }
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) continue;

                MapObjects cellObjects = cell.getTile().getObjects();
                if (cellObjects.getCount() != 1) continue;

                MapObject mapObject = cellObjects.get(0);

                if (mapObject instanceof RectangleMapObject rectangleObject) {
                    Rectangle rectangle = rectangleObject.getRectangle();
                    BodyDef bodyDef = getStaticBodyDef(
                            x * TILE_SIZE + TILE_SIZE / 2f + rectangle.getX() - (TILE_SIZE - rectangle.getWidth()) / 2f,
                            y * TILE_SIZE
                                    + TILE_SIZE / 2f
                                    + rectangle.getY()
                                    - (TILE_SIZE - rectangle.getHeight()) / 2f);

                    Body body = world.createBody(bodyDef);
                    PolygonShape polygonShape = new PolygonShape();
                    polygonShape.setAsBox(rectangle.getWidth() / 2f, rectangle.getHeight() / 2f);
                    body.createFixture(getWallFixture(polygonShape));
                    polygonShape.dispose();
                } else if (mapObject instanceof EllipseMapObject circleMapObject) {
                    Ellipse ellipse = circleMapObject.getEllipse();
                    BodyDef bodyDef = getStaticBodyDef(
                            x * TILE_SIZE + TILE_SIZE / 2f + ellipse.x, y * TILE_SIZE + TILE_SIZE / 2f + ellipse.y);

                    if (ellipse.width != ellipse.height)
                        Gdx.app.error(
                                TAG,
                                "Only circles are allowed.",
                                new IllegalArgumentException("Only circles are allowed."));

                    Body body = world.createBody(bodyDef);
                    CircleShape circleShape = new CircleShape();
                    circleShape.setRadius(ellipse.width / 2f);
                    body.createFixture(circleShape, 0.0f);
                    circleShape.dispose();
                } else if (mapObject instanceof PolygonMapObject polygonMapObject) {
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

    public static ArrayList<GameObject> parseGameObjects(TiledMap map) {
        ArrayList<GameObject> list = new ArrayList<>();
        // TODO refactor animated game objects using an enum
        String[] objects = new String[] {"coins", "torch", "chests", "enemy", "teleporter"};
        for (String s : objects) {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(s);
            if (layer == null) continue;
            for (int x = 0; x < layer.getWidth(); x++) {
                for (int y = 0; y < layer.getHeight(); y++) {
                    TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                    if (cell == null) continue;

                    MapObjects cellObjects = cell.getTile().getObjects();
                    if (cellObjects.getCount() != 1) continue;

                    MapObject cellObject = cellObjects.get(0);
                    int[][] rawMap = parseNavigationMap(map);
                    if (cellObject instanceof RectangleMapObject rectangleObject) {
                        switch (s) {
                            case "torch":
                                list.add(new CandleStick(new Vector2(x, y), rectangleObject.getRectangle()));
                                break;
                            case "coins":
                                list.add(new Coin(new Vector2(x, y), rectangleObject.getRectangle()));
                                break;
                            case "chests":
                                list.add(new WeaponBox(new Vector2(x, y), rectangleObject.getRectangle()));
                                break;
                            case "enemy":
                                Random r = new Random();
                                int next = r.nextInt(8);
                                switch (next) {
                                    case 0 -> list.add(new Snarg(new Vector2(x, y), rawMap));
                                    case 1 -> list.add(new Grommok(new Vector2(x, y), rawMap));
                                    case 2 -> list.add(new Grakor(new Vector2(x, y), rawMap));
                                    case 3 -> list.add(new Durgosh(new Vector2(x, y), rawMap));
                                    case 4 -> list.add(new Morglak(new Vector2(x, y), rawMap));
                                    case 5 -> list.add(new Babo(new Vector2(x, y), rawMap));
                                    case 6 -> list.add(new Skeleton(new Vector2(x, y), rawMap));
                                    case 7 -> list.add(new SkeletonHat(new Vector2(x, y), rawMap));
                                }
                                break;
                            case "teleporter":
                                list.add(new Teleporter(
                                        new Vector2(x, y),
                                        rectangleObject.getRectangle(),
                                        layer.getProperties().get("destination", String.class)));
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
        if (layer == null) return;

        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) continue;

                MapObjects cellObjects = cell.getTile().getObjects();
                if (cellObjects.getCount() != 1) continue;
                PointLight light = new PointLight(
                        rayHandler,
                        100,
                        new Color(0.95f, 0.37f, 0.07f, 1f),
                        TILE_SIZE * 5,
                        x * TILE_SIZE + 8,
                        y * TILE_SIZE + 8);
                light.setSoftnessLength(10);
                light.setContactFilter((short) 0b11111111111, (short) 0, (short) 0b11111111111);
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

    public static int[][] parseNavigationMap(TiledMap tiledMap) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("walls");
        if (layer == null) return new int[][] {};

        // first we fill the entire map with empty cells
        int[][] map = new int[layer.getWidth()][layer.getHeight()];
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {

                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) {
                    map[x][y] = TILE_FLOOR;
                    continue;
                }

                MapObjects cellObjects = cell.getTile().getObjects();
                if (cellObjects.getCount() != 0) map[x][y] = TILE_WALL;
            }
        }
        return map;
    }
}
