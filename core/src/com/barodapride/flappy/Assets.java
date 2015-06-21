package com.barodapride.flappy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Mike on 3/20/2015.
 */
public class Assets {

    // Disposeables
    public static TextureAtlas atlas;
    public static SpriteBatch batch;
    public static BitmapFont fontMedium;



    // Non-Disposeables
    public static TextureRegion bird;
    public static TextureRegion bird2;
    public static TextureRegion bird3;
    public static TextureRegion background;
    public static TextureRegion ground;
    public static TextureRegion pipe;
    public static TextureRegion title;
    public static TextureRegion playDown;
    public static TextureRegion playUp;

    // Animations
    public static Animation birdAnimation;

    public static void load() {

        atlas = new TextureAtlas("pack.atlas");
        batch = new SpriteBatch();

        fontMedium = new BitmapFont(Gdx.files.internal("font/font.fnt"), Gdx.files.internal("font/font_0.png"), false);

        bird = atlas.findRegion("bird-16x16");
        bird2 = atlas.findRegion("bird2-16x16");
        bird3 = atlas.findRegion("bird3-16x16");

        background = atlas.findRegion("background-300x480");
        ground = atlas.findRegion("ground-321x24");
        pipe = atlas.findRegion("pipe-64x400");
        title = atlas.findRegion("title");
        playDown = atlas.findRegion("play_down");
        playUp = atlas.findRegion("play_up");


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

        fontMedium.dispose();
    }

}
