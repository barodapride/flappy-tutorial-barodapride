package com.barodapride.flappy;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Utils {

    public static float getRandomYOpening() {
        return MathUtils.random(96f + 32f, FlappyGame.HEIGHT - 64f);
    }

    public static Action getFloatyAction() {
        MoveByAction a1 = Actions.moveBy(0, 10f, 1f, Interpolation.sine);
        MoveByAction a2 = Actions.moveBy(0, -10f, 1f, Interpolation.sine);
        SequenceAction sa = Actions.sequence(a1, a2);
        return Actions.forever(sa);
    }
}
