package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.dhbw.tinf22b6.gameobject.AnimatedCollisionObject;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.screen.GameScreen;
import de.dhbw.tinf22b6.screen.MenuScreen;
import de.dhbw.tinf22b6.util.CameraHelper;

import java.util.List;
import java.util.stream.Collectors;

public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();
    public CameraHelper cameraHelper;
    private Game game;
    private List<AnimatedCollisionObject> objects;
    private Player player;
    private World world;
    private final Vector2 motion = new Vector2(0, 0);

    public WorldController(Game game, World world, List<AnimatedCollisionObject> objects) {
        this.game = game;
        this.world = world;
        this.objects = objects;
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        player = new Player(world, new Vector2(5, 5));
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(player);
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
        cameraHelper.update(deltaTime);
        // remove deleted objects from the World
        objects.stream().filter(AnimatedCollisionObject::isDead).forEach(animatedCollisionObject -> world.destroyBody(animatedCollisionObject.getBody()));
        // remove deleted objects from the Map
        objects = objects.stream().filter(animatedCollisionObject -> !animatedCollisionObject.isDead()).collect(Collectors.toList());
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

    public Player getPlayer() {
        return player;
    }

    public List<AnimatedCollisionObject> getGameObjects() {
        return objects;
    }
}