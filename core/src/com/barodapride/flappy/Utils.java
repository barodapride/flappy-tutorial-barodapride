package com.barodapride.flappy;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Mike on 3/27/2015.
 */
public class Utils {


    public static float getRandomYOpening(){
        return MathUtils.random(FlappyGame.HEIGHT * .15f, FlappyGame.HEIGHT * .85f);
    }
}
