package de.dhbw.tinf22b6.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    // singleton: prevent instantiation from other classes
    private Assets() {}

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // load texture atlas
        assetManager.load(Constants.ATLAS_PATH, TextureAtlas.class);
        assetManager.load(Constants.SKIN_PATH, Skin.class);
        // start loading assets and wait until finished
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames()) Gdx.app.debug(TAG, "asset: " + a);
    }

    public Array<TextureAtlas.AtlasRegion> getAnimationAtlasRegion(String path) {
        return assetManager.get(Constants.ATLAS_PATH, TextureAtlas.class).findRegions(path);
    }

    public Skin getSkin() {
        return assetManager.get(Constants.SKIN_PATH, Skin.class);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", throwable);
    }

    public TextureAtlas.AtlasRegion getSprite(String path) {
        return assetManager.get(Constants.ATLAS_PATH, TextureAtlas.class).findRegion(path);
    }

    public TextureAtlas getAtlas() {
        return assetManager.get(Constants.ATLAS_PATH, TextureAtlas.class);
    }
}
