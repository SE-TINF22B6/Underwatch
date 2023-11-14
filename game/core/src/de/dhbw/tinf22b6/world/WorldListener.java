package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import de.dhbw.tinf22b6.gameobject.AnimatedCollisionObject;
import de.dhbw.tinf22b6.screen.GameScreen;

import static de.dhbw.tinf22b6.util.Constants.*;

public class WorldListener implements ContactListener {
    private static final String TAG = GameScreen.class.getName();
    private final World world;

    public WorldListener(World world) {
        this.world = world;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case PLAYER_BIT | COIN_BIT:
                if (fixA.getFilterData().categoryBits == COIN_BIT)
                    ((AnimatedCollisionObject) fixA.getUserData()).setDead(true);
                else
                    ((AnimatedCollisionObject) fixB.getUserData()).setDead(true);
                Gdx.app.debug(TAG, "Player picked up Coin");
                break;
            case PLAYER_BIT | POTION_BIT:
                Gdx.app.debug(TAG, "Player picked up Potion");
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
};

