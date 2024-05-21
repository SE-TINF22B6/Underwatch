package de.dhbw.tinf22b6.gameobject.interaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.PlayerStatistics;

public class AmmoBox extends InteractionObject {

    public AmmoBox(Vector2 position, Rectangle rectangle) {
        super("chest", position, rectangle);
    }

    @Override
    public void activate() {
        super.activate();
        Gdx.audio
                .newSound(Gdx.files.internal("sfx/chest_open.mp3"))
                .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
    }

    @Override
    public void interact(Player player) {
        PlayerStatistics.instance.reloadWeapons();
        super.interact(player);
    }
}
