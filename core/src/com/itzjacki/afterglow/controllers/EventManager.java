package com.itzjacki.afterglow.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.itzjacki.afterglow.AfterglowGame;
import com.itzjacki.afterglow.models.Song;
import com.itzjacki.afterglow.screens.PlayScreen;

public class EventManager {

    // EventManager is a singleton
    private static final EventManager INSTANCE = new EventManager();
    private PlayerOptionManager pom;
    // im will hold the currently active instance manager when a song is being played.
    private PlayScreen songInstance;

    private EventManager(){
        pom = new PlayerOptionManager();
    }

    public static EventManager getInstance(){
        return INSTANCE;
    }

    // Takes in wedge state (int 0-8) and returns true if the arrow(s) corresponding to the state is being pressed.
    // Also works with the space bar for the circle state (state 8).
    // Is also used by the player wedge when it's drawn, and doesn't do anything dynamic. Therefore static and public.
    public boolean isArrowPressed(int state){
        switch (state){
            case 0:
                return Gdx.input.isKeyPressed(Input.Keys.UP);
            case 1:
                return Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT);
            case 2:
                return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
            case 3:
                return Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT);
            case 4:
                return Gdx.input.isKeyPressed(Input.Keys.DOWN);
            case 5:
                return Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT);
            case 6:
                return Gdx.input.isKeyPressed(Input.Keys.LEFT);
            case 7:
                return Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT);
            case 8:
                return Gdx.input.isKeyPressed(Input.Keys.SPACE);
            default:
                throw new IllegalArgumentException("Couldn't check for wedge state " + state + " because it does not exist.");
        }
    }

    // Finds screen among the game's default screens
    public void changeScreen(String screenName){
        if(screenName.equals("Loading")){
            changeScreen(AfterglowGame.loadingScreen);
            System.out.println("Changed to loading screen");
        }
        else {
            Screen chosenScreen = AfterglowGame.screens.get(screenName);
            if (chosenScreen == null) {
                System.out.println("Tried to go to screen which doesn't exist: " + screenName); // To be used during development. Less severe.
                // throw new IllegalArgumentException("Tried to go to screen which doesn't exist: " + screenName);
            }
            changeScreen(chosenScreen);
            System.out.println("Changed to screen: " + screenName); // for debugging
        }
    }
    // Changes to screen explicitly passed in parameter
    public void changeScreen(Screen screen){
        ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
    }

    public void createSongInstance(Song song){
        songInstance = new PlayScreen(song);
        changeScreen(songInstance);
    }

    public void endSongInstance(boolean songWasBeaten, int finalScore, int highestCombo){
        System.out.println("Song ended");
        // TODO: Save if high score. Prompt to upload to online high scores. Send to end screen.
    }

    public float getScoreModifier(){
        // TODO: Implement modifier system with modifier screen. This method should return what the score modifier is based on which modifiers are active. (ie. 0x with invincibility active)
        return 1f;
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
