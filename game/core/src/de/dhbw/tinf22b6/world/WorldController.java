package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.screen.GameScreen;
import de.dhbw.tinf22b6.screen.MenuScreen;
import de.dhbw.tinf22b6.util.CameraHelper;

import java.util.List;
import java.util.stream.Collectors;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();
    public CameraHelper cameraHelper;
    private Game game;
    private List<GameObject> objects;
    private Player player;
    private World world;
    private final Vector2 motion = new Vector2(0, 0);
    public boolean debugBox2D = false;
    private Camera camera;

    public WorldController(Game game, World world, List<GameObject> objects, Camera camera) {
        this.game = game;
        this.world = world;
        this.objects = objects;
        this.camera = camera;
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
        player = new Player(world, new Vector2(5, 5));
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(player);
        objects.add(player);
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
        cameraHelper.update(deltaTime);
        world.step(deltaTime, 6, 2);

        // tick objects
        objects.forEach(gameObject -> gameObject.tick(deltaTime));
        // remove deleted objects from the World
        objects.stream().filter(GameObject::isRemove).forEach(gameObject -> world.destroyBody(gameObject.getBody()));
        // remove deleted objects from the Map
        objects = objects.stream().filter(gameObject -> !gameObject.isRemove()).collect(Collectors.toList());
    }

    private void handleInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            motion.x = -1;
            player.applyForce(motion);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            motion.x = 1;
            player.applyForce(motion);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            motion.y = 1;
            player.applyForce(motion);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            motion.y = -1;
            player.applyForce(motion);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            Gdx.app.debug(TAG, "Objects in List: " + objects.size());
        }

        // Camera Controls (move)
        float camMoveSpeed = 32 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveCamera(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveCamera(0, -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) cameraHelper.setPosition(0, 0);

        // Camera Controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) cameraHelper.setZoom(1);

        if (Gdx.input.justTouched()) {
            Vector3 unproject = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Vector2 reducedDimension = new Vector2(unproject.x - player.getPos().x - TILE_SIZE / 2f, unproject.y - player.getPos().y - TILE_SIZE / 2f);
            objects.add(new Bullet(player.getPos(), world, reducedDimension.setLength(1)));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) player.dodge();

        // Debugging
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) debugBox2D = !debugBox2D;

        // Back to menu
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) game.setScreen(new MenuScreen(game));
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.S:
            case Input.Keys.W:
            case Input.Keys.D:
                motion.set(0, 0);
                player.applyForce(motion);
                break;
            case Input.Keys.R:
                game.setScreen(new GameScreen(game, WorldType.LEVEL1.getMap()));
                Gdx.app.debug(TAG, "Game world reset");
                break;
            case Input.Keys.ENTER:
                cameraHelper.setTarget(cameraHelper.hasTarget() ? null : player);
                Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
                break;
        }
        return super.keyUp(keycode);
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    public List<GameObject> getGameObjects() {
        return objects;
    }
}