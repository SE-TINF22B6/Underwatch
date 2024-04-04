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

    public Player getPlayer() {
        return player;
    }

    private Player player;
    private World world;
    private final Vector2 motion = new Vector2(0, 0);
    public boolean debugBox2D = false;
    private Camera camera;
    private final Preferences prefs = Gdx.app.getPreferences("Controls");
    private final int left = prefs.getInteger("left", Input.Keys.A);
    private final int right = prefs.getInteger("right", Input.Keys.D);
    private final int up = prefs.getInteger("up", Input.Keys.W);
    private final int down = prefs.getInteger("down", Input.Keys.S);
    private final int inventory = prefs.getInteger("inventory", Input.Keys.I);
    private final int dodge = prefs.getInteger("dodge", Input.Keys.SPACE);


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
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.audio.newSound(Gdx.files.internal("sfx/gun-shot.mp3")).play(0.1f);
        Vector3 unproject = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector2 reducedDimension = new Vector2(unproject.x - player.getPos().x - TILE_SIZE / 2f, unproject.y - player.getPos().y - TILE_SIZE / 2f);
        //Gdx.app.debug(TAG, reducedDimension.setLength(1) + "." + reducedDimension.angleDeg());
        EntitySystem.instance.add(new Bullet(new Vector2(player.getPos().x + TILE_SIZE / 2f, player.getPos().y), world, reducedDimension.setLength(1), Constants.WEAPON_BIT));
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == left) {
            motion.x = -1;
        } else if (keycode == right) {
            motion.x = 1;
        } else if (keycode == up) {
            motion.y = 1;
        } else if (keycode == down) {
            motion.y = -1;
        }
        if (keycode == left || keycode == right || keycode == up || keycode == down)
            player.applyForce(motion.setLength(1));
        if (keycode == inventory) {
            Gdx.app.debug(TAG, "Objects in List: " + EntitySystem.instance.getGameObjects().size());
        }
        if (keycode == dodge) player.dodge();
        if (keycode == Input.Keys.ESCAPE) game.setScreen(new MenuScreen(game));
        if (keycode == Input.Keys.C) debugBox2D = !debugBox2D;

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == left || keycode == right) {
            motion.x = 0;
        } else if (keycode == up || keycode == down) {
            motion.y = 0;
        }
        if (keycode == left || keycode == right || keycode == up || keycode == down)
            player.applyForce(motion.setLength(1));

        if (keycode == Input.Keys.R) {
            game.setScreen(new GameScreen(game, WorldType.LEVEL1.getMap()));
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