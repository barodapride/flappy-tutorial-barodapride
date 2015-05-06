package com.barodapride.flappy;

import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Created by Mike on 3/27/2015.
 */
public class GroundPair {

    private Ground ground1;
    private Ground ground2;

    public GroundPair(Ground g1, Ground g2) {
        this.ground1 = g1;
        this.ground2 = g2;
    }

    /**
     * Resets the pipe pair if it's scrolled off the screen
     */
    public void update(){
        if (ground1.getX(Align.right) <= 0){
            ground1.setX(ground1.getX() + FlappyGame.WIDTH*2);
        }
        if (ground2.getX(Align.right) <= 0){
            ground2.setX(ground2.getX() + FlappyGame.WIDTH*2);
        }
    }

    public void stop(){

        ground1.vel.x = 0;
        ground2.vel.x = 0;

    }

    public Ground getGround1() {
        return ground1;
    }

    public void setGround1(Ground ground1) {
        this.ground1 = ground1;
    }

    public Ground getGround2() {
        return ground2;
    }

    public void setGround2(Ground ground2) {
        this.ground2 = ground2;
    }
}
