package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.screen.MenuScreen;
import de.dhbw.tinf22b6.util.CameraHelper;
import de.dhbw.tinf22b6.world.gameObject.AnimatedGameObject;
import de.dhbw.tinf22b6.world.gameObject.Player;

import java.util.ArrayList;

public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();
    public CameraHelper cameraHelper;
    private Game game;
    private ArrayList<AnimatedGameObject> objects;
    private Player player;
    private World world;

    public WorldController(Game game) {
        this.game = game;
        init();
    }

    private void init() {
        this.world = new World(new Vector2(0,0), true);
        objects = new ArrayList<>();
        Gdx.input.setInputProcessor(this);
        player = new Player(world);
        objects.add(player);
        cameraHelper = new CameraHelper();
        cameraHelper.setPosition(16 * 10, 16 * 10);
    }


    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private Vector2 motion = new Vector2(0,0);
    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;

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

        // Back to menu
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) backToMainMenu();
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    private void backToMainMenu() {
        game.setScreen(new MenuScreen(game));
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.S:
            case Input.Keys.W:
            case Input.Keys.D:
                motion.set(0,0);
                player.applyForce(motion);
                break;
            case Input.Keys.R:
                init();
                Gdx.app.debug(TAG, "Game world reset");
                break;
            case Input.Keys.ENTER:
                cameraHelper.setTarget(cameraHelper.hasTarget() ? null : player);
                Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
                break;
        }
        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public ArrayList<AnimatedGameObject> getObjects() {
        return objects;
    }

    public void addObject(AnimatedGameObject object) {
        this.objects.add(object);
    }
}