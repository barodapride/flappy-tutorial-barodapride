package com.barodapride.flappy;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Mike on 3/27/2015.
 */
public class Utils {


    public static float getRandomYOpening(){
        return MathUtils.random(96 + 24, FlappyGame.HEIGHT * .85f);
    }
}
