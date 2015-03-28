package com.barodapride.flappy;

import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Created by Mike on 3/27/2015.
 */
public class PipePair {

    private Pipe topPipe;
    private Pipe bottomPipe;

    public static float GAP_SIZE = 150f;

    public PipePair(Pipe topPipe, Pipe bottomPipe) {
        this.topPipe = topPipe;
        this.bottomPipe = bottomPipe;
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
        bottomPipe.setPosition(bottomPipe.getX() + 350, y - GAP_SIZE/2, Align.topLeft);
        topPipe.setPosition(topPipe.getX() + 350, y + GAP_SIZE/2, Align.bottomLeft);
    }

    /**
     * Initialize the pipes for the first time
     */
    public void init(){
        float y = Utils.getRandomYOpening();
        bottomPipe.setPosition(350, y - GAP_SIZE/2, Align.topLeft);
        topPipe.setPosition(350, y + GAP_SIZE/2, Align.bottomLeft);
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
}
