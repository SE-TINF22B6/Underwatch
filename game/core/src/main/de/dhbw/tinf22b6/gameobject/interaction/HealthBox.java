package de.dhbw.tinf22b6.gameobject.interaction;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.PlayerStatistics;

public class HealthBox extends InteractionObject {

    public HealthBox(Vector2 position, Rectangle rectangle) {
        // TODO: proper Texture region (Tracked in #231)
        super("chest", position, rectangle);
    }

    @Override
    public void activate() {
        super.activate();
        // TODO: opening sound of HP Box (Tracked in #231)
    }

    @Override
    public void interact(Player player) {
        PlayerStatistics.instance.resetHP();
        super.interact(player);
    }
}
