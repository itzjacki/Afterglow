package com.itzjacki.afterglow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.itzjacki.afterglow.controllers.EventManager;
import com.itzjacki.afterglow.screens.LoadingScreen;
import com.itzjacki.afterglow.screens.MainMenuScreen;
import com.itzjacki.afterglow.screens.OptionsScreen;
import com.itzjacki.afterglow.screens.PlayScreen;

import java.util.HashMap;
import java.util.Map;


public class AfterglowGame extends Game {
	// Active play refers to the area where actual projectiles exist. Should be constant across resolutions for balance.
	public static final int ACTIVE_PLAY_WIDTH = 1920;
	public static final int ACTIVE_PLAY_HEIGHT = 1080;
	public static int CURRENT_RESOLUTION_WIDTH;
	public static int CURRENT_RESOLUTION_HEIGHT;
	public static final String[] resolutions = new String[]{"1024x576", "1280x720", "1366x768", "1600x900", "1920x1080", "2560x1440"};

	public SpriteBatch batch;
	public static Map<String, Screen> screens;
	public static Screen loadingScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();

		screens = new HashMap<String, Screen>();
		loadingScreen = new LoadingScreen();
		createScreens();

		EventManager.getInstance().applyResolution(EventManager.getInstance().getScreenWidth(), EventManager.getInstance().getScreenHeight());
		EventManager.getInstance().changeScreen("MainMenu");

		CURRENT_RESOLUTION_WIDTH = Gdx.graphics.getWidth();
		CURRENT_RESOLUTION_HEIGHT = Gdx.graphics.getWidth();
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public static void createScreens(){
		screens.put("MainMenu", new MainMenuScreen());
		screens.put("Options", new OptionsScreen());
		screens.put("Play", new PlayScreen());
	}
}
