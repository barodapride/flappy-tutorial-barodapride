package com.barodapride.flappy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Created by Mike on 3/20/2015.
 */
public class Pipe extends Actor {

    public static final int WIDTH = 64; // pixels
    public static final int HEIGHT = 400;

    public static final float MOVE_VELOCITY = 120f; // pixels per second
    private Rectangle bounds;

    // Actor keeps track of position so we just need to keep track of velocity and acceleration
    private Vector2 vel;

    private TextureRegion region;

    private State state;

    public enum State { alive, dead }

    public Pipe() {
        region = new TextureRegion(Assets.pipe);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        state = State.alive;

        vel = new Vector2(-MOVE_VELOCITY, 0);

        bounds = new Rectangle(0, 0, WIDTH, HEIGHT);

        // An actor's origin defines the center for rotation.
        setOrigin(Align.center);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        switch (state){
            case alive:
                actAlive(delta);
                break;
            case dead:
                vel = Vector2.Zero;
                break;
        }

        updateBounds();

    }

    private void updateBounds() {
        bounds.x = getX();
        bounds.y = getY();
    }

    private void actAlive(float delta) {
        updatePosition(delta);
    }

    private void updatePosition(float delta) {
        setX(getX() + vel.x * delta);
        setY(getY() + vel.y * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }

    public TextureRegion getRegion() {
        return region;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
