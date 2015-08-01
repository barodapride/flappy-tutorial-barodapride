package com.barodapride.flappy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

    public static Sound punch;
    public static Sound flap;
    public static Sound bing;



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
    public static TextureRegion whitePixel;
    public static TextureRegion ruby1;
    public static TextureRegion ruby2;
    public static TextureRegion ruby3;
    public static TextureRegion ruby4;
    public static TextureRegion ruby5;
    public static TextureRegion ruby6;

    // Animations
    public static Animation birdAnimation;
    public static Animation rubyAnimation;

    public static void load() {

        atlas = new TextureAtlas("pack.atlas");
        batch = new SpriteBatch();

        fontMedium = new BitmapFont(Gdx.files.internal("font/font.fnt"), Gdx.files.internal("font/font_0.png"), false);

        bird = atlas.findRegion("peng");
        bird2 = atlas.findRegion("peng2");
        bird3 = atlas.findRegion("peng3");

        background = atlas.findRegion("background-300x480");
        ground = atlas.findRegion("ground-321x96");
        pipe = atlas.findRegion("pipe-32x200");
        title = atlas.findRegion("title");
        playDown = atlas.findRegion("play_down");
        playUp = atlas.findRegion("play_up");
        whitePixel = atlas.findRegion("white-pixel");
        ruby1 = atlas.findRegion("ruby1");
        ruby2 = atlas.findRegion("ruby2");
        ruby3 = atlas.findRegion("ruby3");
        ruby4 = atlas.findRegion("ruby4");
        ruby5 = atlas.findRegion("ruby5");
        ruby6 = atlas.findRegion("ruby6");


        birdAnimation = new Animation(.1f, bird, bird2, bird3);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        Array<TextureRegion> rubyArray = new Array<TextureRegion>();
        rubyArray.add(ruby1);
        rubyArray.add(ruby2);
        rubyArray.add(ruby3);
        rubyArray.add(ruby4);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby5);
        rubyArray.add(ruby6);
        rubyArray.add(ruby5);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyAnimation = new Animation(.07f, rubyArray, Animation.PlayMode.LOOP);

        punch = Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
        flap = Gdx.audio.newSound(Gdx.files.internal("sounds/flap.mp3"));
        bing = Gdx.audio.newSound(Gdx.files.internal("sounds/bing.mp3"));

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

        punch.dispose();
        flap.dispose();
        bing.dispose();
    }

    public static void playPunchSound(){
        punch.play();
    }

    public static void playFlapSound() {
        flap.play();
    }

    public static void playBingSound() {
        bing.play();
    }
}
