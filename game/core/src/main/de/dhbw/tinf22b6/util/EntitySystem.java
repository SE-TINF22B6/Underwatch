package de.dhbw.tinf22b6.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.gameobject.bullet.Bullet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntitySystem {
    public static final String TAG = EntitySystem.class.getName();
    public static final EntitySystem instance = new EntitySystem();
    private List<GameObject> gameObjects;
    private List<Bullet> syncBulletsToBeCreated;
    private List<Vector2> syncAmmoBoxesToBeCreated;

    // singleton: prevent instantiation from other classes
    private EntitySystem() {}

    public void init(List<GameObject> objectList) {
        this.gameObjects = objectList;
        this.syncBulletsToBeCreated = Collections.synchronizedList(new ArrayList<>());
        this.syncAmmoBoxesToBeCreated = Collections.synchronizedList(new ArrayList<>());
        Gdx.app.debug(TAG, "initialized with size: " + gameObjects.size());
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public List<Vector2> getSyncAmmoBoxesToBeCreated() {
        return syncAmmoBoxesToBeCreated;
    }

    public void addAmmoBoxToQueue(Vector2 ammoBox) {
        this.syncAmmoBoxesToBeCreated.add(ammoBox);
    }

    public void addBulletToQueue(Bullet bullet) {
        syncBulletsToBeCreated.add(bullet);
    }

    public List<Bullet> getSyncBulletsToBeCreated() {
        return syncBulletsToBeCreated;
    }

    public void add(GameObject object) {
        this.gameObjects.add(object);
    }

    public void remove(GameObject object) {
        this.gameObjects.remove(object);
    }

    public Player getPlayer() {
        for (GameObject object : gameObjects) {
            if (object instanceof Player) return (Player) object;
        }
        return null;
    }
}
