package com.barodapride.flappy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ruby extends Actor{

    public static final int WIDTH = 24; // pixels
    public static final int HEIGHT = 24;

    private float time;
    private Vector2 vel;
    private TextureRegion region;

    private Rectangle bounds;

    public Ruby() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
        vel = new Vector2();
        vel.x = -Pipe.MOVE_VELOCITY;
        region = new TextureRegion(Assets.bird);

        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        setRandomTime();
    }

    @Override
    public void act(float delta) {
        time += delta;
        setX(getX() + vel.x*delta);
        bounds.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Assets.rubyAnimation.getKeyFrame(time), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }

    public Vector2 getVel() {
        return vel;
    }

    public void setVel(float x, float y) {
        this.vel.x = x;
        this.vel.y = y;
    }

    public void setRandomTime(){
        time = MathUtils.random(1f, 123f);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
