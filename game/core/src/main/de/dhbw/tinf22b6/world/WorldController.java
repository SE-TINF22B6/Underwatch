package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.screen.GameScreen;
import de.dhbw.tinf22b6.util.CameraHelper;
import de.dhbw.tinf22b6.util.EntitySystem;
import de.dhbw.tinf22b6.util.PlayerStatistics;

import java.util.HashSet;
import java.util.Iterator;

public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();
    private final Preferences prefs = Gdx.app.getPreferences("Controls");
    private final int left = prefs.getInteger("left", Input.Keys.A);
    private final int right = prefs.getInteger("right", Input.Keys.D);
    private final int up = prefs.getInteger("up", Input.Keys.W);
    private final int down = prefs.getInteger("down", Input.Keys.S);
    private final int inventory = prefs.getInteger("inventory", Input.Keys.I);
    private final int interact = prefs.getInteger("interact", Input.Keys.E);
    private final int dodge = prefs.getInteger("dodge", Input.Keys.SPACE);
    public CameraHelper cameraHelper;
    public boolean debugBox2D = false;
    private Game game;
    private Player player;
    private Camera camera;
    private GameScreen gameScreen;
    private HashSet<Integer> pressedKeys = new HashSet<>();

    public WorldController(Game game, Camera camera, GameScreen gameScreen) {
        this.game = game;
        this.camera = camera;
        this.gameScreen = gameScreen;
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
        player = new Player(PlayerStatistics.instance.getStartLocation(), camera);
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(player);
        EntitySystem.instance.add(player);
    }

    public void update(float deltaTime) {
        World world = Box2dWorld.instance.getWorld();
        handleInput(deltaTime);
        cameraHelper.update(deltaTime);

        // // remove deleted objects from the World
        // EntitySystem.instance.getGameObjects().stream()
        // .filter(GameObject::isRemove)
        // .forEach(gameObject -> {
        // if (!world.isLocked()) world.destroyBody(gameObject.getBody());
        // });
        // // remove deleted objects from the Map
        // EntitySystem.instance.getGameObjects().stream()
        // .filter(GameObject::isRemove)
        // .forEach(EntitySystem.instance::remove);
        Iterator<GameObject> i = EntitySystem.instance.getGameObjects().iterator();
        while (i.hasNext()) {
            GameObject gameObject = i.next();
            if (gameObject.isRemove()) {
                EntitySystem.instance.remove(gameObject);
                if (!world.isLocked())
                    world.destroyBody(gameObject.getBody());
            }
        }
        // Update motion vector based on the state of pressed keys
        float motionX = 0;
        float motionY = 0;

        if (pressedKeys.contains(left) && !pressedKeys.contains(right)) {
            motionX = -1;
        } else if (pressedKeys.contains(right) && !pressedKeys.contains(left)) {
            motionX = 1;
        }

        if (pressedKeys.contains(up) && !pressedKeys.contains(down)) {
            motionY = 1;
        } else if (pressedKeys.contains(down) && !pressedKeys.contains(up)) {
            motionY = -1;
        }

        player.getMotionVector().x = motionX;
        player.getMotionVector().y = motionY;

        world.step(deltaTime, 6, 2);

        // tick objects
        EntitySystem.instance.getGameObjects().forEach(gameObject -> gameObject.tick(deltaTime));
    }

    private void handleInput(float deltaTime) {
        // Camera Controls (move)
        float camMoveSpeed = 32 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            camMoveSpeed *= camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveCamera(-camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveCamera(camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            moveCamera(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            moveCamera(0, -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE))
            cameraHelper.setPosition(0, 0);

        // Camera Controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            camZoomSpeed *= camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA))
            cameraHelper.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD))
            cameraHelper.addZoom(-camZoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH))
            cameraHelper.setZoom(1);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.shoot();
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        // Set Motion Vector
        pressedKeys.add(keycode);
        if (keycode == interact)
            player.interact(player);
        if (keycode == dodge)
            player.dodge();
        if (keycode == Input.Keys.ESCAPE)
            gameScreen.setPaused();
        if (keycode == Input.Keys.C)
            debugBox2D = !debugBox2D;

        return super.keyDown(keycode);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY < 0) {
            PlayerStatistics.instance.cycleWeapon(false);
        } else if (amountY > 0) {
            PlayerStatistics.instance.cycleWeapon(true);
        }
        return super.scrolled(amountX, amountY);
    }

    @Override
    public boolean keyUp(int keycode) {
        // Reset Motion Vector
        pressedKeys.remove(keycode);
        if (keycode == Input.Keys.R) {
            game.setScreen(new GameScreen(game, WorldType.PATHFINDING.getMap()));
            Gdx.app.debug(TAG, "Game world reset");
        }
        if (keycode == Input.Keys.ENTER) {
            cameraHelper.setTarget(cameraHelper.hasTarget() ? null : player);
            Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
        }
        return super.keyUp(keycode);
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }
}
