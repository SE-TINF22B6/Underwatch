package de.dhbw.tinf22b6.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class MusicPlayer implements Disposable {
    public static final MusicPlayer instance = new MusicPlayer();
    private Music music;

    private MusicPlayer() {}

    public void setMusic(Music music) {
        if (this.music != null) {
            this.music.stop();
            music.dispose();
        }
        this.music = music;
        this.music.setLooping(true);
        this.music.setVolume(Gdx.app.getPreferences("Controls").getFloat("music"));
        this.music.play();
    }

    public void stop() {
        this.music.stop();
    }

    @Override
    public void dispose() {
        if (music != null) music.dispose();
    }
}
