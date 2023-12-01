package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.*;
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
import de.dhbw.tinf22b6.util.Constants;
import de.dhbw.tinf22b6.util.EntitySystem;

import static de.dhbw.tinf22b6.util.Constants.TILE_SIZE;

public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();
    public CameraHelper cameraHelper;
    private Game game;
    private Player player;
    private World world;
    private final Vector2 motion = new Vector2(0, 0);
    public boolean debugBox2D = false;
    private Camera camera;
    private final Preferences prefs = Gdx.app.getPreferences("Controls");


    public WorldController(Game game, World world, Camera camera) {
        this.game = game;
        this.world = world;
        this.camera = camera;
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
        player = new Player(world, new Vector2(5, 5));
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(player);
        EntitySystem.instance.add(player);
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
        cameraHelper.update(deltaTime);
        world.step(deltaTime, 6, 2);

        // tick objects
        EntitySystem.instance.getGameObjects().forEach(gameObject -> gameObject.tick(deltaTime));
        // remove deleted objects from the World
        EntitySystem.instance.getGameObjects().stream().filter(GameObject::isRemove).forEach(gameObject -> world.destroyBody(gameObject.getBody()));
        // remove deleted objects from the Map
        EntitySystem.instance.getGameObjects().stream().filter(GameObject::isRemove).forEach(EntitySystem.instance::remove);
    }

    private void handleInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(prefs.getInteger("left", Input.Keys.A))) {
            motion.x = -1;
            player.applyForce(motion);
        }
        if (Gdx.input.isKeyPressed(prefs.getInteger("right", Input.Keys.D))) {
            motion.x = 1;
            player.applyForce(motion);
        }
        if (Gdx.input.isKeyPressed(prefs.getInteger("up", Input.Keys.W))) {
            motion.y = 1;
            player.applyForce(motion);
        }
        if (Gdx.input.isKeyPressed(prefs.getInteger("down", Input.Keys.S))) {
            motion.y = -1;
            player.applyForce(motion);
        }
        if (Gdx.input.isKeyPressed(prefs.getInteger("inventory", Input.Keys.I))) {
            Gdx.app.debug(TAG, "Objects in List: " + EntitySystem.instance.getGameObjects().size());
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
            Gdx.audio.newSound(Gdx.files.internal("sfx/gun-shot.mp3")).play(1);
            Vector3 unproject = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Vector2 reducedDimension = new Vector2(unproject.x - player.getPos().x - TILE_SIZE / 2f, unproject.y - player.getPos().y - TILE_SIZE / 2f);
            //Gdx.app.debug(TAG, reducedDimension.setLength(1) + "." + reducedDimension.angleDeg());
            EntitySystem.instance.add(new Bullet(player.getPos(), world, reducedDimension.setLength(1), Constants.WEAPON_BIT));
        }

        if (Gdx.input.isKeyJustPressed(prefs.getInteger("dodge", Input.Keys.SPACE))) player.dodge();

        // Debugging
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) debugBox2D = !debugBox2D;

        // Back to menu
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) game.setScreen(new MenuScreen(game));
    }

    @Override
    public boolean keyUp(int keycode) {
        final int left = prefs.getInteger("left", Input.Keys.A);
        final int right = prefs.getInteger("right", Input.Keys.D);
        final int up = prefs.getInteger("up", Input.Keys.W);
        final int down = prefs.getInteger("down", Input.Keys.S);
        if (keycode == left || keycode == right || keycode == up || keycode == down) {
            motion.set(0, 0);
            player.applyForce(motion);
        } else if (keycode == Input.Keys.R) {
            game.setScreen(new GameScreen(game, WorldType.LEVEL1.getMap()));
            Gdx.app.debug(TAG, "Game world reset");
        } else if (keycode == Input.Keys.ENTER) {
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