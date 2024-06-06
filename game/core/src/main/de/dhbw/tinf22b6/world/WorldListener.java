package de.dhbw.tinf22b6.world;

import static de.dhbw.tinf22b6.util.Constants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import de.dhbw.tinf22b6.gameobject.Coin;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.gameobject.Teleporter;
import de.dhbw.tinf22b6.gameobject.bullet.BouncingBullet;
import de.dhbw.tinf22b6.gameobject.bullet.Bullet;
import de.dhbw.tinf22b6.gameobject.enemy.Enemy;
import de.dhbw.tinf22b6.gameobject.interaction.InteractionObject;
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
            case PLAYER_BIT | INTERACTION_BIT:
                if (fixA.getFilterData().categoryBits == INTERACTION_BIT) {
                    ((InteractionObject) fixA.getUserData()).deactivate();
                    ((Player) fixB.getUserData()).canPickUp(null);
                } else {
                    ((InteractionObject) fixB.getUserData()).deactivate();
                    ((Player) fixA.getUserData()).canPickUp(null);
                }
                break;
            case PLAYER_BIT | ENEMY_SIGHT_BIT:
                if (fixA.getFilterData().categoryBits == PLAYER_BIT) {
                    ((Enemy) fixB.getUserData()).setTagged(false);
                } else {
                    ((Enemy) fixA.getUserData()).setTagged(false);
                }
                break;
            case WEAPON_BIT | WALL_BIT:
                if (fixA.getFilterData().categoryBits == WEAPON_BIT) {
                    if (fixA.getUserData() instanceof BouncingBullet) {
                        ((BouncingBullet) fixA.getUserData()).leaveWall();
                    }
                } else if (fixB.getUserData() instanceof BouncingBullet) {
                    ((BouncingBullet) fixB.getUserData()).leaveWall();
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
            case PLAYER_BIT | INTERACTION_BIT:
                if (fixA.getFilterData().categoryBits == INTERACTION_BIT) {
                    ((InteractionObject) fixA.getUserData()).activate();
                    ((Player) fixB.getUserData()).canPickUp(((InteractionObject) fixA.getUserData()));
                } else {
                    ((InteractionObject) fixB.getUserData()).activate();
                    ((Player) fixA.getUserData()).canPickUp(((InteractionObject) fixB.getUserData()));
                }
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
                    if (fixA.getUserData() instanceof BouncingBullet) {
                        ((BouncingBullet) fixA.getUserData()).bounce();
                        return;
                    }
                    ((Bullet) fixA.getUserData()).setRemove(true);
                } else {
                    if (fixB.getUserData() instanceof BouncingBullet) {
                        ((BouncingBullet) fixB.getUserData()).bounce();
                        return;
                    }
                    ((Bullet) fixB.getUserData()).setRemove(true);
                }
                // Gdx.audio.newSound(Gdx.files.internal("sfx/arrow-impact.mp3")).play(1);
                break;
            case ENEMY_BIT | WEAPON_BIT:
                if (fixA.getFilterData().categoryBits == WEAPON_BIT) {
                    ((Bullet) fixA.getUserData()).setRemove(true);
                    ((Enemy) fixB.getUserData()).hit(((Bullet) fixA.getUserData()).getDamage());
                } else {
                    ((Bullet) fixB.getUserData()).setRemove(true);
                    ((Enemy) fixA.getUserData()).hit(((Bullet) fixB.getUserData()).getDamage());
                }
                Gdx.audio
                        .newSound(Gdx.files.internal("sfx/hitSound.mp3"))
                        .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
                break;
            case WEAPON_ENEMY_BIT | PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == WEAPON_ENEMY_BIT) {
                    ((Bullet) fixA.getUserData()).setRemove(true);
                    PlayerStatistics.instance.hitHP(((Bullet) fixA.getUserData()).getDamage());
                } else {
                    ((Bullet) fixB.getUserData()).setRemove(true);
                    PlayerStatistics.instance.hitHP(((Bullet) fixB.getUserData()).getDamage());
                }
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
                break;
            }
            case PLAYER_BIT | ENEMY_SIGHT_BIT: {
                if (fixA.getFilterData().categoryBits == PLAYER_BIT) {
                    ((Enemy) fixB.getUserData()).setTagged(true);
                } else {
                    ((Enemy) fixA.getUserData()).setTagged(true);
                }
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
