package com.barodapride.flappy;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Mike on 3/27/2015.
 */
public class Utils {


    /**
     *
     * @return a random Y that represents the top of the pipe
     */
    public static float generateTopYPositionForABottomPipe(){
        return MathUtils.random(FlappyGame.GROUND_LEVEL + Bird.HEIGHT, FlappyGame.HEIGHT * .75f);
    }
}
