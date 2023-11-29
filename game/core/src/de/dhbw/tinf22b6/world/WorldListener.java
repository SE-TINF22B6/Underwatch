package de.dhbw.tinf22b6.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import de.dhbw.tinf22b6.gameobject.Bullet;
import de.dhbw.tinf22b6.gameobject.Enemy;
import de.dhbw.tinf22b6.gameobject.GameObject;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.screen.GameScreen;

import static de.dhbw.tinf22b6.util.Constants.*;

public class WorldListener implements ContactListener {
    private static final String TAG = GameScreen.class.getName();

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cDef) {
            case PLAYER_BIT | ENEMY_SIGHT_BIT:
                if (fixA.getFilterData().categoryBits == PLAYER_BIT) {
                    ((Enemy) fixB.getUserData()).setTarget(null);
                } else {
                    ((Enemy) fixA.getUserData()).setTarget(null);
                }
                break;
        }
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
                break;
            case WALL_BIT | WEAPON_ENEMY_BIT:
            case WALL_BIT | WEAPON_BIT:
                if (fixA.getFilterData().categoryBits == WEAPON_BIT || fixA.getFilterData().categoryBits == WEAPON_ENEMY_BIT) {
                    ((Bullet) fixA.getUserData()).setRemove(true);
                } else
                    ((Bullet) fixB.getUserData()).setRemove(true);
                Gdx.audio.newSound(Gdx.files.internal("sfx/arrow-impact.mp3")).play(1);
                break;
            case ENEMY_BIT | WEAPON_BIT:
                if (fixA.getFilterData().categoryBits == WEAPON_BIT) {
                    ((Bullet) fixA.getUserData()).setRemove(true);
                    ((Enemy) fixB.getUserData()).hit();
                } else {
                    ((Bullet) fixB.getUserData()).setRemove(true);
                    ((Enemy) fixA.getUserData()).hit();
                }
                break;
            case WEAPON_ENEMY_BIT | PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == WEAPON_ENEMY_BIT) {
                    ((Bullet) fixA.getUserData()).setRemove(true);
                    ((Player) fixB.getUserData()).hit();
                } else {
                    ((Bullet) fixB.getUserData()).setRemove(true);
                    ((Player) fixA.getUserData()).hit();
                }
                break;
            case ENEMY_SIGHT_BIT | PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == PLAYER_BIT) {
                    ((Enemy) fixB.getUserData()).setTarget(((Player) fixA.getUserData()));
                } else {
                    ((Enemy) fixA.getUserData()).setTarget(((Player) fixB.getUserData()));
                }
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

