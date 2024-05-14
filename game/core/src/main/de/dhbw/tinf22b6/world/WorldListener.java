package de.dhbw.tinf22b6.world;

import static de.dhbw.tinf22b6.util.Constants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import de.dhbw.tinf22b6.gameobject.*;
import de.dhbw.tinf22b6.screen.GameScreen;
import de.dhbw.tinf22b6.util.PlayerStatistics;

public class WorldListener implements ContactListener {
    private static final String TAG = GameScreen.class.getName();
    private final GameScreen gameScreen;

    public WorldListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case PLAYER_BIT | BOX_INTERACTION_BIT:
                if (fixA.getFilterData().categoryBits == BOX_INTERACTION_BIT) {
                    ((LootBox) fixA.getUserData()).close();
                    ((Player) fixB.getUserData()).canPickUp(null);
                } else {
                    ((LootBox) fixB.getUserData()).close();
                    ((Player) fixA.getUserData()).canPickUp(null);
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
            case PLAYER_BIT | BOX_INTERACTION_BIT:
                if (fixA.getFilterData().categoryBits == BOX_INTERACTION_BIT) {
                    ((LootBox) fixA.getUserData()).open();
                    ((Player) fixB.getUserData()).canPickUp(((LootBox) fixA.getUserData()));
                } else {
                    ((LootBox) fixB.getUserData()).open();
                    ((Player) fixA.getUserData()).canPickUp(((LootBox) fixB.getUserData()));
                }
                // TODO: sfx open box
                // Gdx.audio.newSound(Gdx.files.internal("sfx/coin_pickup.mp3")).play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
                break;
            case PLAYER_BIT | COIN_BIT:
                if (fixA.getFilterData().categoryBits == COIN_BIT) {
                    ((Coin) fixA.getUserData()).setRemove(true);
                } else {
                    ((Coin) fixB.getUserData()).setRemove(true);
                }
                PlayerStatistics.instance.addCoins(1);
                Gdx.audio
                        .newSound(Gdx.files.internal("sfx/coin_pickup.mp3"))
                        .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
                break;
            case WALL_BIT | WEAPON_BIT:
                if (fixA.getFilterData().categoryBits == WEAPON_BIT) {
                    ((Bullet) fixA.getUserData()).setRemove(true);
                } else ((Bullet) fixB.getUserData()).setRemove(true);
                // Gdx.audio.newSound(Gdx.files.internal("sfx/arrow-impact.mp3")).play(1);
                break;
            case ENEMY_BIT | WEAPON_BIT:
                if (fixA.getFilterData().categoryBits == WEAPON_BIT) {
                    ((Bullet) fixA.getUserData()).setRemove(true);
                    ((Enemy) fixB.getUserData()).hit();
                } else {
                    ((Bullet) fixB.getUserData()).setRemove(true);
                    ((Enemy) fixA.getUserData()).hit();
                }
                Gdx.app.debug(TAG, "Weapon and Enemy");
                break;
            case WEAPON_ENEMY_BIT | PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == WEAPON_ENEMY_BIT) {
                    ((Bullet) fixA.getUserData()).setRemove(true);
                } else {
                    ((Bullet) fixB.getUserData()).setRemove(true);
                }
                PlayerStatistics.instance.hitHP();
                Gdx.audio
                        .newSound(Gdx.files.internal("sfx/player_hit.mp3"))
                        .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
                break;
            case WEAPON_ENEMY_BIT | WALL_BIT:
                if (fixA.getFilterData().categoryBits == WEAPON_ENEMY_BIT) {
                    ((Bullet) fixA.getUserData()).setRemove(true);
                } else {
                    ((Bullet) fixB.getUserData()).setRemove(true);
                }
                break;
            case PLAYER_BIT | TELEPORTER_BIT: {
                if (fixA.getFilterData().categoryBits == TELEPORTER_BIT) {
                    WorldType nextWorld = ((Teleporter) fixA.getUserData()).getDestination();
                    gameScreen.changeMap(nextWorld);
                } else {
                    WorldType nextWorld = ((Teleporter) fixB.getUserData()).getDestination();
                    gameScreen.changeMap(nextWorld);
                }
                Gdx.app.debug(TAG, "BEAM ME UP SCOTTY!");
                break;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
;
