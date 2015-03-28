package com.barodapride.flappy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Created by Mike on 3/20/2015.
 */
public class Ground extends Actor {

    public static final int WIDTH = 300; // pixels
    public static final int HEIGHT = 24;

    public static final float MOVE_VELOCITY = 120f; // pixels per second

    // Actor keeps track of position so we just need to keep track of velocity and acceleration
    private Vector2 vel;

    private TextureRegion region;

    private State state;

    private enum State { alive, dead };

    public Ground() {
        region = new TextureRegion(Assets.ground);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        state = State.alive;

        vel = new Vector2(-MOVE_VELOCITY, 0);

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
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }

    public TextureRegion getRegion() {
        return region;
    }
}
