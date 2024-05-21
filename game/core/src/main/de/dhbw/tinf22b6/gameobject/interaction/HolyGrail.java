package de.dhbw.tinf22b6.gameobject.interaction;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.PlayerStatistics;

public class HolyGrail extends InteractionObject {

    public HolyGrail(Vector2 position, Rectangle rectangle) {
        // TODO: proper Texture region (Tracked in #231)
        super("chest", position, rectangle);
    }

    @Override
    public void activate() {
        super.activate();
        // TODO: activate sound of Holy Grail (Tracked in #231)
    }

    @Override
    public void interact(Player player) {
        PlayerStatistics.instance.setWon();
        super.interact(player);
    }
}
