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
        loadAllFromFile();
    }
    protected void loadAllFromFile(){
        screenWidth = prefs.getInteger("screenWidth", 1600);
        screenHeight = prefs.getInteger("screenHeight", 900);
        fullscreen = prefs.getBoolean("fullscreen", false);
        firstStartup = prefs.getBoolean("firstStartup", true);
        // Nickname should never have to rely on default value. Dedicated saving function is used in practice,
        // it is only included here for completeness sake. (in case i forget about this later lol)
        nickname = prefs.getString("nickname", "null");
        audioEffectLevel = prefs.getFloat("audioEffectLevel", 1f);
        musicLevel = prefs.getFloat("musicLevel", 1f);
    }

    protected void saveAllToFile(){
        prefs.putInteger("screenWidth", screenWidth);
        prefs.putInteger("screenHeight", screenHeight);
        prefs.putBoolean("fullscreen", fullscreen);
        prefs.putBoolean("firstStartup", firstStartup);
        prefs.putString("nickname", nickname);
        prefs.putFloat("audioEffectLevel", audioEffectLevel);
        prefs.putFloat("musicLevel", musicLevel);
        prefs.flush();
    }

    // Saves nickname to config file. Nickname should be sanitized and assigned to variable before this is used.

    protected void saveNickname(String nick){
        prefs.putString(nick, "null");
        prefs.flush();
    }

    // Getters and setters for option variables
    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isFirstStartup() {
        return firstStartup;
    }

    public void setFirstStartup(boolean firstStartup) {
        this.firstStartup = firstStartup;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public float getAudioEffectLevel() {
        return audioEffectLevel;
    }

    public void setAudioEffectLevel(float audioEffectLevel) {
        this.audioEffectLevel = audioEffectLevel;
    }

    public float getMusicLevel() {
        return musicLevel;
    }

    public void setMusicLevel(float musicLevel) {
        this.musicLevel = musicLevel;
    }
}
