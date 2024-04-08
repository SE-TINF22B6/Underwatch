package de.dhbw.tinf22b6.ui.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.dhbw.tinf22b6.util.Assets;

public class Settings extends Stage {
    public Settings(StageManager stageManager) {
        super();
        Skin skin = Assets.instance.getSkin();

        Table table = new Table(skin);
        table.setFillParent(true);
        this.addActor(table);

        Label l = new Label("Settings", skin);
        l.setPosition(this.getWidth() / 2 - l.getWidth() / 2, this.getHeight() - 50);
        this.addActor(l);

        table.add(new Label("Sound Volume", skin));
        table.add(new Slider(0, 100, 1, false, skin)).row();
        table.add(new Label("Music Volume", skin));
        table.add(new Slider(0, 100, 1, false, skin)).row();
        table.add(new Label("Move Up", skin));
        Button btnSelectUp = new Button(skin);
        btnSelectUp.add(new Label("W", skin));
        table.add(btnSelectUp).row();
        table.add(new Label("Move Down", skin));
        Button btnSelectDown = new Button(skin);
        btnSelectDown.add(new Label("S", skin));
        table.add(btnSelectDown).row();
        table.add(new Label("Move Left", skin));
        Button btnSelectLeft = new Button(skin);
        btnSelectLeft.add(new Label("A", skin));
        table.add(btnSelectLeft).row();
        table.add(new Label("Move Right", skin));
        Button btnSelectRight = new Button(skin);
        btnSelectRight.add(new Label("D", skin));
        table.add(btnSelectRight).row();

        Button btnBack = new Button(skin);
        btnBack.add(new Label("Back to Menu", skin));
        btnBack.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        stageManager.setStage(new Menu(stageManager));
                    }
                }
        );
        btnBack.setPosition(getWidth() / 2 - btnBack.getWidth() / 2, 10);
        this.addActor(btnBack);

        table.setTransform(true);
        table.setScale(0.8f);
        table.setPosition(getWidth() / 2 - 250 , 60);
    }
}
