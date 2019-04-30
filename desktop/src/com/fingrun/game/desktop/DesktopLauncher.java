package com.fingrun.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fingrun.game.FingRun;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FingRun.WIDTH;
		config.height = FingRun.HEIGHT;
		config.title = FingRun.TITLE;
		config.fullscreen = true;
		new LwjglApplication(new FingRun(), config);
	}
}
