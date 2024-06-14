package de.dhbw.tinf22b6.gameobject.interaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.PlayerStatistics;

public class DamageBoost extends InteractionObject {

    public DamageBoost(Vector2 position, Rectangle rectangle) {
        super("damage_big", position, rectangle, false);
    }

    @Override
    public void activate() {
        super.activate();
//        Gdx.audio
//                .newSound(Gdx.files.internal("sfx/damage_boost.mp3"))
//                .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
    }

    @Override
    public void interact(Player player) {
        PlayerStatistics.instance.getCurrentWeapon().increaseDamage(0.2f);
        super.interact(player);
    }
}