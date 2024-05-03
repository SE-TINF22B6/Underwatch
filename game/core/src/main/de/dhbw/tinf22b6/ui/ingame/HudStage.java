package de.dhbw.tinf22b6.ui.ingame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.dhbw.tinf22b6.util.Assets;
import de.dhbw.tinf22b6.util.PlayerStatistics;

public class HudStage extends Stage {
    private final Label labelHP;

    public HudStage() {
        Skin skin = Assets.instance.getSkin();
        labelHP = new Label("HP:", skin);
        this.addActor(labelHP);
    }

    @Override
    public void draw() {
        if (labelHP.getText().toString().equals("HP: " + PlayerStatistics.instance.hp())) {
            super.draw();
            return;
        }
        labelHP.setText("HP: " + PlayerStatistics.instance.hp());
        super.draw();
    }
}
