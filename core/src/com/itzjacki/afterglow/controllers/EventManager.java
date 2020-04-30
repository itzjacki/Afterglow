package com.itzjacki.afterglow.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.itzjacki.afterglow.AfterglowGame;
import com.itzjacki.afterglow.screens.OptionsScreen;

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

    // Uses the libgdx screen system to change the active screen
    public void changeScreen(String screenName){
        Screen chosenScreen = AfterglowGame.screens.get(screenName);
        if(chosenScreen == null){
            throw new IllegalArgumentException("Tried to go to screen which doesn't exist: " + screenName);
        }
        ((Game) Gdx.app.getApplicationListener()).setScreen(chosenScreen);
        System.out.println("Changed to screen " + screenName); // for debugging
    }

    public void saveAndApplyPreferences(){

        // Resolution & fullscreen
        if(pom.isFullscreen()){
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
        else{
            Gdx.graphics.setWindowedMode(pom.getScreenWidth(), pom.getScreenHeight());
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
}
