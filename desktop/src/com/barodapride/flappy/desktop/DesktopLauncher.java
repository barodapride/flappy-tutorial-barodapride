package com.barodapride.flappy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.barodapride.flappy.FlappyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) (FlappyGame.WIDTH*1.5f);
        config.height = (int) (FlappyGame.HEIGHT*1.5f);

		new LwjglApplication(new FlappyGame(), config);
	}
}
