package de.dhbw.tinf22b6.ui.ingame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.dhbw.tinf22b6.gameobject.Player;
import de.dhbw.tinf22b6.util.Assets;

public class HudStage extends Stage {
    private Player player;
    private Label labelHP;

    public HudStage(Player player) {
        this.player = player;
        Skin skin = Assets.instance.getSkin();
        labelHP = new Label("HP:", skin);
        this.addActor(labelHP);
    }

    @Override
    public void draw() {
        if (labelHP.getText().toString().equals("HP: " + player.getHealth())) {
            super.draw();
            return;
        }
        labelHP.setText("HP: " + player.getHealth());
        super.draw();
    }
}
