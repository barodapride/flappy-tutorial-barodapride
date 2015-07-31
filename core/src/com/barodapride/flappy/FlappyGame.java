package com.barodapride.flappy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyGame extends Game {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 480;
    public static final int GROUND_LEVEL = 96;

	@Override
	public void create () {
        Assets.load();
//		setScreen(new GameplayScreen(this));
        setScreen(new MainMenuScreen(this));
	}

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
    }
}
