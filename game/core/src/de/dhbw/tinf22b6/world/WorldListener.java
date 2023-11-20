package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.screen.GameScreen;

import static de.dhbw.tinf22b6.util.Constants.*;

public class WorldListener implements ContactListener {
    private static final String TAG = GameScreen.class.getName();

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
                    ((GameObject) fixA.getUserData()).setRemove(true);
                else
                    ((GameObject) fixB.getUserData()).setRemove(true);
                Gdx.app.debug(TAG, "Player picked up Coin");
                break;
            case WALL_BIT | WEAPON_BIT:
                // TODO the hit sound is not supposed to be here
                Gdx.audio.newSound(Gdx.files.internal("sfx/hitSound.mp3")).play(1);
                if (fixA.getFilterData().categoryBits == WEAPON_BIT) {
                    ((Bullet) fixA.getUserData()).setRemove(true);
                }
                else
                    ((Bullet) fixB.getUserData()).setRemove(true);
                Gdx.app.debug(TAG, "Weapon and wall");
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
