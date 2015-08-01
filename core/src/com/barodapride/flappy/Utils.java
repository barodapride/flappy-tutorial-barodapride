package com.barodapride.flappy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Utils {

    public static final Color MAIN_MENU_BACKGROUND_COLOR = new Color(204f/255f, 204f/255f, 1f, 1f);

    public static float getRandomYOpening() {
        return MathUtils.random(96f + 48f, FlappyGame.HEIGHT - 64f);
    }

    public static Action getFloatyAction() {
        MoveByAction a1 = Actions.moveBy(0, 10f, 1f, Interpolation.sine);
        MoveByAction a2 = Actions.moveBy(0, -10f, 1f, Interpolation.sine);
        SequenceAction sa = Actions.sequence(a1, a2);
        return Actions.forever(sa);
    }

    public static Color getRandomBackgroundColor(){

        float r;
        float g;
        float b;

        int random = MathUtils.random(0, 2);

        if (random == 0){
            // A light blue color
            r = 204f/255f;
            g = 204f/255f;
            b = 255f/255f;
        }
        else if (random == 1){
            // A light purple color
            r = 153f/255f;
            g = 102f/255f;
            b = 255f/255f;
        }
        else {
            // A light peach color
            r = 255f/255f;
            g = 204f/255f;
            b = 153f/255f;
        }

        return new Color(r, g, b, 1f);
    }
}
