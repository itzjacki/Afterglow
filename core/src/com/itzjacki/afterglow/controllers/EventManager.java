package com.itzjacki.afterglow.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.itzjacki.afterglow.AfterglowGame;

public class EventManager {

    // EventManager is a singleton
    private static final EventManager INSTANCE = new EventManager();
    private PlayerOptionManager pom;
    // im will hold the currently active instance manager when a song is being played.
    private InstanceManager im;

    private EventManager(){
        pom = new PlayerOptionManager();
    }

    public static EventManager getInstance(){
        return INSTANCE;
    }

    // Uses the LibGDX screen system to change the active screen
    public void changeScreen(String screenName){
        if(screenName.equals("Loading")){
            ((Game) Gdx.app.getApplicationListener()).setScreen(AfterglowGame.loadingScreen);
        }
        else {
            Screen chosenScreen = AfterglowGame.screens.get(screenName);
            if (chosenScreen == null) {
                System.out.println("Tried to go to screen which doesn't exist: " + screenName); // To be used during development. Less severe.
                // throw new IllegalArgumentException("Tried to go to screen which doesn't exist: " + screenName);
            }
            ((Game) Gdx.app.getApplicationListener()).setScreen(chosenScreen);
            System.out.println("Changed to screen: " + screenName); // for debugging
        }
    }

    public void saveAndApplyPreferences(){

        // Resolution & fullscreen
        boolean resolutionChanged = applyResolution(getScreenWidth(), getScreenHeight());

        // Sound effect volume


        // Music volume

        pom.saveAllToFile();
        // Reloads the game after the values are saved, so it doesn't overwrite them before they're saved
        if(resolutionChanged){
            reloadGame();
        }
    }

    public String getNickname(){
        return pom.getNickname();
    }

    // Saves nickname to POM, and updates the options screen by calling its show() method, which contains ui updaters
    public void changeNickname(String newNickname){
        pom.saveNickname(newNickname);
        AfterglowGame.screens.get("Options").show();
    }

    // Called when user selects new resolution in drop-down menu. Doesn't actually apply resolution, just saves current choice to pom variable.
    public void selectResolution(String resolutionString){
        String[] trimmedString = resolutionString.split("x");
        int resolutionWidth = Integer.parseInt(trimmedString[0]);
        int resolutionHeight = Integer.parseInt(trimmedString[1]);
        pom.setScreenWidth(resolutionWidth);
        pom.setScreenHeight(resolutionHeight);
    }

    public void selectFullscreen(boolean fullscreen){
        pom.setFullscreen(fullscreen);
    }

    // Applies resolution stored in pom variables. Runs on resolution change and on game start.
    // Returns bool on whether resolution was actually changed.
    public boolean applyResolution(int newWidth, int newHeight){
        // Only changes resolution if the given resolution is actually different from the current one.
        if( AfterglowGame.CURRENT_RESOLUTION_WIDTH != newWidth || AfterglowGame.CURRENT_RESOLUTION_HEIGHT != newHeight || Gdx.graphics.isFullscreen() != pom.isFullscreen()){

            AfterglowGame.CURRENT_RESOLUTION_WIDTH = newWidth;
            AfterglowGame.CURRENT_RESOLUTION_HEIGHT = newHeight;

            if(pom.isFullscreen()){
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            else{
                Gdx.graphics.setWindowedMode(newWidth, newHeight);
            }
            return true;
        }
        else{
            return false;
        }
    }

    // Makes POM pull data from preferences into current variables
    public void loadPreferencesFromFile(){
        pom.loadAllFromFile();
    }

    public int getScreenWidth(){
        return pom.getScreenWidth();
    }

    public int getScreenHeight(){
        return pom.getScreenHeight();
    }

    public boolean isFullscreen(){
        return pom.isFullscreen();
    }

    // Can be used to reload the game to adapt UI to new resolution.
    public void reloadGame(){
        changeScreen("Loading");

        AfterglowGame.createScreens();

        changeScreen("Options");
    }

    public void selectMusicVolume(float volume){
        pom.setMusicLevel(volume);
    }

    public void selectEffectVolume(float volume){
        pom.setAudioEffectLevel(volume);
    }

    public float getMusicVolume(){
        return pom.getMusicLevel();
    }

    public float getEffectVolume(){
        return pom.getAudioEffectLevel();
    }
}
