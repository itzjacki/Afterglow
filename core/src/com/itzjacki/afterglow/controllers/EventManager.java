package com.itzjacki.afterglow.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.itzjacki.afterglow.AfterglowGame;
import com.itzjacki.afterglow.screens.MainMenuScreen;
import com.itzjacki.afterglow.screens.OptionsScreen;
import com.itzjacki.afterglow.screens.PlayScreen;

public class EventManager {

    // EventManager is a singleton
    private static final EventManager INSTANCE = new EventManager();
    private PlayerOptionManager pom;

    private EventManager(){
        pom = new PlayerOptionManager();
    }

    public static EventManager getInstance(){
        return INSTANCE;
    }

    // Uses the LibGDX screen system to change the active screen
    public void changeScreen(String screenName){
        if(screenName == "Loading"){
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
        if(Gdx.graphics.getWidth() != pom.getScreenWidth() || Gdx.graphics.getHeight() != pom.getScreenHeight()){
            applyResolution();
        }
        else{
            System.out.println("did not change resolution, as it was already on the target resolution.");
        }

        // Sound effect volume


        // Music volume

        pom.saveAllToFile();
    }

    public String getNickname(){
        return pom.getNickname();
    }

    // Saves nickname to POM, and updates the options screen by calling its show() method, which contains ui updaters
    public void changeNickname(String newNickname){
        pom.saveNickname(newNickname);
        AfterglowGame.screens.get("Options").show();
    }

    public void changeResolution(String resolutionString){
        String[] trimmedString = resolutionString.split("x");
        int resolutionWidth = Integer.parseInt(trimmedString[0]);
        int resolutionHeight = Integer.parseInt(trimmedString[1]);
        pom.setScreenWidth(resolutionWidth);
        pom.setScreenHeight(resolutionHeight);
    }

    // Applies resolution stored in pom variables. Runs on resolution change and on game start.
    public void applyResolution(){
        if(pom.isFullscreen()){
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
        else{
            Gdx.graphics.setWindowedMode(pom.getScreenWidth(), pom.getScreenHeight());
        }
        reloadGame();
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

    // Can be used to reload the game to adapt UI to new resolution.
    public void reloadGame(){
        changeScreen("Loading");

        AfterglowGame.createScreens();

        changeScreen("Options");
    }
}
