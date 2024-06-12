package de.dhbw.tinf22b6.gameobject.interaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.PlayerStatistics;

public class AmmoBox extends InteractionObject {

    public AmmoBox(Vector2 position, Rectangle rectangle) {
        super("box_2", position, rectangle, true);
    }

    @Override
    public void activate() {
        super.activate();
        Gdx.audio
                .newSound(Gdx.files.internal("sfx/chest_open.mp3"))
                .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
        PlayerStatistics.instance.reloadWeapons();
        this.setRemove(true);
    }

    /**
     * This method is overwritten, as we don't want to deal with an
     * edge case where the player spams the interact button resulting
     * in #activate() being called after/simultaneously with #interact()
     * therefore trying to remove the object twice from the ECS.
     *
     * @param player Player which interacts
     */
    @Override
    public void interact(Player player) {
        // do nothing
    }
}
