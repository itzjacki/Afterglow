package com.itzjacki.afterglow.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.itzjacki.afterglow.AfterglowGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Afterglow";
		config.resizable = false;
		config.width = 1600; // Default size before configured by user
		config.height = 900;
		config.samples = 8;
		new LwjglApplication(new AfterglowGame(), config);
	}
}
