package de.dhbw.tinf22b6.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimationComponent implements Component {
    public Animation<TextureAtlas.AtlasRegion> currentAnimation;
    public float width, height;
}
