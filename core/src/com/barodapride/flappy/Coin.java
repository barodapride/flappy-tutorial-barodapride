package com.barodapride.flappy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Mike on 6/20/2015.
 */
public class Coin extends Actor{

    public static final int WIDTH = 2; // pixels
    public static final int HEIGHT = 128;

    private Vector2 vel;
    private TextureRegion region;

    private Rectangle bounds;

    public Coin() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
        vel = new Vector2();
        vel.x = -Pipe.MOVE_VELOCITY;
        region = new TextureRegion(Assets.bird);

        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        setX(getX() + vel.x*delta);
        bounds.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

//        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
//                getScaleY(), getRotation());

    }

    public Vector2 getVel() {
        return vel;
    }

    public void setVel(float x, float y) {
        this.vel.x = x;
        this.vel.y = y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
