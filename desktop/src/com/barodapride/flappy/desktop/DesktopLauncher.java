package com.barodapride.flappy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.barodapride.flappy.FlappyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = FlappyGame.WIDTH*2;
        config.height = FlappyGame.HEIGHT*2;

		new LwjglApplication(new FlappyGame(), config);
	}
}
