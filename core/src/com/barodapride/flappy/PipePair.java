package com.barodapride.flappy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Created by Mike on 3/27/2015.
 */
public class PipePair {

    public static final float STARTING_X_POSITION = 400f;
    private Pipe topPipe;
    private Pipe bottomPipe;
    private Coin coin;

    public static float GAP_SIZE = 130f;

    public PipePair(Pipe topPipe, Pipe bottomPipe) {
        this.topPipe = topPipe;
        this.bottomPipe = bottomPipe;
        this.coin = new Coin();
    }

    /**
     * Resets the pipe pair if it's scrolled off the screen
     */
    public void update(){
        if (topPipe.getX(Align.right) < 0){
            reInitPipes();
        }
    }

    /**
     * Re initialize the pipes when they scroll off the screen
     */
    private void reInitPipes() {
        float y = Utils.getRandomYOpening();

        float xDisplacement = GameplayScreen.PIPE_SPACING * GameplayScreen.PIPE_SETS;

        bottomPipe.setPosition(bottomPipe.getX() + xDisplacement, y - GAP_SIZE/2, Align.topLeft);
        topPipe.setPosition(topPipe.getX() + xDisplacement, y + GAP_SIZE/2, Align.bottomLeft);
        coin.setPosition(bottomPipe.getX(Align.center), bottomPipe.getY(Align.top) + GAP_SIZE/2, Align.center);
    }


    public void initFirst(){
        float y = Utils.getRandomYOpening();
        bottomPipe.setPosition(STARTING_X_POSITION, y - GAP_SIZE/2, Align.topLeft);
        topPipe.setPosition(STARTING_X_POSITION, y + GAP_SIZE/2, Align.bottomLeft);
        coin.setPosition(bottomPipe.getX(Align.center), bottomPipe.getY(Align.top) + GAP_SIZE/2, Align.center);
    }
    public void initSecond(){
        float y = Utils.getRandomYOpening();
        bottomPipe.setPosition(STARTING_X_POSITION + GameplayScreen.PIPE_SPACING, y - GAP_SIZE/2, Align.topLeft);
        topPipe.setPosition(STARTING_X_POSITION + GameplayScreen.PIPE_SPACING, y + GAP_SIZE/2, Align.bottomLeft);
        coin.setPosition(bottomPipe.getX(Align.center), bottomPipe.getY(Align.top) + GAP_SIZE/2, Align.center);
    }
    public void initThird(){
        float y = Utils.getRandomYOpening();
        bottomPipe.setPosition(STARTING_X_POSITION + GameplayScreen.PIPE_SPACING * 2, y - GAP_SIZE/2, Align.topLeft);
        topPipe.setPosition(STARTING_X_POSITION + GameplayScreen.PIPE_SPACING * 2, y + GAP_SIZE/2, Align.bottomLeft);
        coin.setPosition(bottomPipe.getX(Align.center), bottomPipe.getY(Align.top) + GAP_SIZE/2, Align.center);
    }

    public Pipe getTopPipe() {
        return topPipe;
    }

    public void setTopPipe(Pipe topPipe) {
        this.topPipe = topPipe;
    }

    public Pipe getBottomPipe() {
        return bottomPipe;
    }

    public void setBottomPipe(Pipe bottomPipe) {
        this.bottomPipe = bottomPipe;
    }

    public Coin getCoin() {
        return coin;
    }

    public void moveCoinOffscreen() {
        coin.setY(coin.getY() + 10000);
    }
}
