package com.itzjacki.afterglow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.itzjacki.afterglow.controllers.EventManager;
import com.itzjacki.afterglow.screens.MainMenuScreen;
import com.itzjacki.afterglow.screens.OptionsScreen;
import com.itzjacki.afterglow.screens.PlayScreen;

import java.util.HashMap;
import java.util.Map;


public class AfterglowGame extends Game {
	public static final int V_WIDTH = 1920;
	public static final int V_HEIGHT = 1080;

	public SpriteBatch batch;
	public static Map<String, Screen> screens;

	@Override
	public void create () {
		batch = new SpriteBatch();

		screens = new HashMap<String, Screen>();
		screens.put("MainMenu", new MainMenuScreen());
		screens.put("Play", new PlayScreen());
		screens.put("Options", new OptionsScreen());

		EventManager.getInstance().changeScreen("MainMenu");
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
