package com.barodapride.flappy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Mike on 3/20/2015.
 */
public class Assets {

    // Disposeables
    public static TextureAtlas atlas;
    public static SpriteBatch batch;


    // Non-Disposeables
    public static TextureRegion bird;
    public static TextureRegion bird2;
    public static TextureRegion bird3;
    public static TextureRegion background;
    public static TextureRegion ground;
    public static TextureRegion pipe;

    // Animations
    public static Animation birdAnimation;

    public static void load() {

        atlas = new TextureAtlas("pack.atlas");
        batch = new SpriteBatch();

        bird = atlas.findRegion("bird-16x16");
        bird2 = atlas.findRegion("bird2-16x16");
        bird3 = atlas.findRegion("bird3-16x16");

        background = atlas.findRegion("background-300x480");
        ground = atlas.findRegion("ground-300x24");
        pipe = atlas.findRegion("pipe-64x400");


        birdAnimation = new Animation(.1f, bird, bird2, bird3);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

    }

    // Dispose all disposeables here
    public static void dispose(){
        if (atlas != null) {
            atlas.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
    }

}
