package de.dhbw.tinf22b6.ui.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.dhbw.tinf22b6.util.Assets;

import java.util.concurrent.atomic.AtomicReference;

public class Settings extends Stage {
    private int lastKeyCode;
    private final Preferences preferences = Gdx.app.getPreferences("Controls");
    private TextButton currentEntry;

    public Settings(StageManager stageManager, Music music) {
        super();
        Skin skin = Assets.instance.getSkin();

        Table contentTable = new Table(skin);
        ScrollPane scrollPane = new ScrollPane(contentTable);
        scrollPane.setSize(getWidth() / 20 * 18, getHeight() / 10 * 7);
        scrollPane.setPosition(getWidth() / 20, getHeight() / 10 * 2);
        this.addActor(scrollPane);

        Label l = new Label("Settings", skin);
        l.setPosition(this.getWidth() / 2 - l.getWidth() / 2, this.getHeight() - 50);
        this.addActor(l);

        contentTable.add(new Label("Music Volume", skin));
        Slider musicSlider = new Slider(0, 1, 0.1f, false, skin);
        musicSlider.setValue(preferences.getFloat("music"));
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                preferences.putFloat("music", musicSlider.getValue());
                preferences.putBoolean("muteMusic", false);
                preferences.flush();
                music.setVolume(musicSlider.getValue());
            }
        });
        contentTable.add(musicSlider).row();

        contentTable.add(new Label("SFX Volume", skin));
        Slider sfxSlider = new Slider(0, 1, 0.1f, false, skin);
        sfxSlider.setValue(preferences.getFloat("sfx"));
        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                preferences.putFloat("sfx", sfxSlider.getValue());
                preferences.flush();
            }
        });
        contentTable.add(sfxSlider).row();

        contentTable.add(new Label("Move Up", skin));
        TextButton btnSelectUp = new TextButton(Input.Keys.toString(preferences.getInteger("up")), skin);
        btnSelectUp.addListener(new OptionsClickHandler());
        contentTable.add(btnSelectUp).row();

        contentTable.add(new Label("Move Down", skin));
        TextButton btnSelectDown = new TextButton(Input.Keys.toString(preferences.getInteger("down")), skin);
        btnSelectDown.addListener(new OptionsClickHandler());
        contentTable.add(btnSelectDown).row();

        contentTable.add(new Label("Move Left", skin));
        TextButton btnSelectLeft = new TextButton(Input.Keys.toString(preferences.getInteger("left")), skin);
        btnSelectLeft.addListener(new OptionsClickHandler());
        contentTable.add(btnSelectLeft).row();

        contentTable.add(new Label("Move Right", skin));
        TextButton btnSelectRight = new TextButton(Input.Keys.toString(preferences.getInteger("right")), skin);
        btnSelectRight.addListener(new OptionsClickHandler());
        contentTable.add(btnSelectRight).row();

        contentTable.add(new Label("Dodge", skin));
        TextButton btnDodge = new TextButton(Input.Keys.toString(preferences.getInteger("dodge")), skin);
        btnDodge.addListener(new OptionsClickHandler());
        contentTable.add(btnDodge).row();

        contentTable.add(new Label("Inventory", skin));
        TextButton btnInventory = new TextButton(Input.Keys.toString(preferences.getInteger("inventory")), skin);
        btnInventory.addListener(new OptionsClickHandler());
        contentTable.add(btnInventory).row();

        Button btnBack = new Button(skin);
        btnBack.add(new Label("Back to Menu", skin));
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.setStage(new Menu(stageManager, stageManager.getMusic()));
            }
        });
        btnBack.setPosition(getWidth() / 2 - btnBack.getWidth() / 2, 10);
        this.addActor(btnBack);

        this.setScrollFocus(scrollPane);
        this.addListener(new OptionsKeyPressHandler());
    }

    private class OptionsClickHandler extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            // if we are currently already editing a setting
            if (currentEntry != null) {
                currentEntry.setText(Input.Keys.toString(lastKeyCode));
            }

            currentEntry = (TextButton) event.getTarget().getParent();
            lastKeyCode = Input.Keys.valueOf(currentEntry.getText().toString());
            currentEntry.setText("Press a Button");
            super.clicked(event, x, y);
        }
    }

    private class OptionsKeyPressHandler extends InputListener {
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            // skip if we're not editing
            if (currentEntry == null || keycode == Input.Keys.ESCAPE) {
                return super.keyDown(event, keycode);
            }

            // get current setting we're editing
            AtomicReference<String> settingToBeEdited = new AtomicReference<>();
            preferences.get().forEach((s, o) -> {
                if (Integer.parseInt((String) o) == lastKeyCode) settingToBeEdited.set(s);
            });

            // check if the new keycode is already bound
            AtomicReference<Boolean> canEdit = new AtomicReference<>(true);
            preferences.get().forEach((s, o) -> {
                if (!settingToBeEdited.get().equals(s)) if (Integer.parseInt((String) o) == keycode) canEdit.set(false);
            });

            // update preferences and persist them
            if (canEdit.get()) preferences.putInteger(settingToBeEdited.get(), keycode);
            preferences.flush();

            currentEntry.setText(Input.Keys.toString(canEdit.get() ? keycode : lastKeyCode));
            currentEntry = null;
            return super.keyDown(event, keycode);
        }
    }

}

