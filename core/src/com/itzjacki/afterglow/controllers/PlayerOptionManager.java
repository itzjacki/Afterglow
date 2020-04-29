package com.itzjacki.afterglow.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PlayerOptionManager {
    Preferences prefs;
    private int screenWidth;
    private int screenHeight;
    private boolean fullscreen;
    private boolean firstStartup;
    private String nickname;
    private float audioEffectLevel;
    private float musicLevel;

    // Class should only be used by event manager
    protected PlayerOptionManager(){
        prefs = Gdx.app.getPreferences("Afterglow Preferences");
        loadFromFile();
    }

    protected void loadFromFile(){
        screenWidth = prefs.getInteger("screenWidth", 1600);
        screenHeight = prefs.getInteger("screenHeight", 900);
        fullscreen = prefs.getBoolean("fullscreen", false);
        firstStartup = prefs.getBoolean("firstStartup", true);
        nickname = prefs.getString("nickname", "null"); // Should never have to rely on default value
        audioEffectLevel = prefs.getFloat("audioEffectLevel", 1f);
        musicLevel = prefs.getFloat("musicLevel", 1f);
    }

    protected void saveToFile(){
        prefs.putInteger("screenWidth", screenWidth);
        prefs.putInteger("screenHeight", screenHeight);
        prefs.putBoolean("fullscreen", fullscreen);
        prefs.putBoolean("firstStartup", firstStartup);
        prefs.putString("nickname", nickname);
        prefs.putFloat("audioEffectLevel", audioEffectLevel);
        prefs.putFloat("musicLevel", musicLevel);
    }
}
