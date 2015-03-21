package com.barodapride.flappy;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Mike on 3/20/2015.
 */
public class GameplayScreen extends ScreenAdapter {

    protected OrthographicCamera camera;
    protected FlappyGame game;

    public GameplayScreen(FlappyGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, FlappyGame.WIDTH, FlappyGame.HEIGHT);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false, width, height);

    }
}
