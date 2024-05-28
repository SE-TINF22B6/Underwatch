package de.dhbw.tinf22b6.gameobject.interaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.MusicPlayer;
import de.dhbw.tinf22b6.util.PlayerStatistics;

public class HolyGrail extends InteractionObject {

    public HolyGrail(Vector2 position, Rectangle rectangle) {
        super("trophy", position, rectangle, false);
        this.activeAnimation.setFrameDuration(0.5f);
    }

    @Override
    public void activate() {
        super.activate();
        MusicPlayer.instance.stop();
        Gdx.audio
                .newSound(Gdx.files.internal("sfx/holymoly.mp3"))
                .play(Gdx.app.getPreferences("Controls").getFloat("sfx"));
    }

    @Override
    public void interact(Player player) {
        PlayerStatistics.instance.setWon();
        super.interact(player);
    }
}
