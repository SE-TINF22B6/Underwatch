package de.dhbw.tinf22b6.util;

import com.badlogic.gdx.Gdx;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.gameobject.Player;

import java.util.ArrayList;
import java.util.List;

public class EntitySystem {
    public static final String TAG = EntitySystem.class.getName();
    public static final EntitySystem instance = new EntitySystem();
    private List<GameObject> gameObjects;

    // singleton: prevent instantiation from other classes
    private EntitySystem() {
    }

    public void init(List<GameObject> objectList) {
        this.gameObjects = objectList;
        Gdx.app.debug(TAG, "initialized with size: " + gameObjects.size());
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void add(GameObject object) {
        this.gameObjects.add(object);
    }

    public void remove(GameObject object) {
        this.gameObjects.remove(object);
    }

    public Player getPlayer() {
        for (GameObject object : gameObjects) {
            if (object instanceof Player)
                return (Player) object;
        }
        return null;
    }
}
