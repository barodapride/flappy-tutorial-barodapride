package com.barodapride.flappy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Mike on 3/20/2015.
 */
public class Bird extends Actor {

    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    // Actor keeps track of position so we just need to keep track of velocity and acceleration
    private Vector2 vel;
    private Vector2 accel;

    private TextureRegion region;

    public Bird() {
        region = new TextureRegion(Assets.bird);
        setWidth(WIDTH);
        setHeight(HEIGHT);

        vel = new Vector2();
        accel = new Vector2();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }

}
