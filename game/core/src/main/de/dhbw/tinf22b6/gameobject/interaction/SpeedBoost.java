package de.dhbw.tinf22b6.gameobject.interaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Player;

public class SpeedBoost extends InteractionObject {
    private final boolean big;

    public SpeedBoost(Vector2 position, Rectangle rectangle, boolean big) {
        super("speed" + (big ? "_big" : "_small"), position, rectangle);
        this.big = big;
    }

    @Override
    public void activate() {
        super.activate();
        Gdx.audio
                .newSound(Gdx.files.internal("sfx/health_restore.mp3"))
                .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
    }

    @Override
    public void interact(Player player) {
        player.speedBoost(big);
        super.interact(player);
    }
}
