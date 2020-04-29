package com.itzjacki.afterglow.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.itzjacki.afterglow.AfterglowGame;

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
        ((Game) Gdx.app.getApplicationListener()).setScreen(AfterglowGame.screens.get(screenName));
        System.out.println("Changed to screen " + screenName); // for debugging
    }

    public void saveAndApplyPreferences(){

        // Resolution & fullscreen
        //TODO: get input from list and save to variable here
        if(pom.isFullscreen()){
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
        else{
            Gdx.graphics.setWindowedMode(pom.getScreenWidth(), pom.getScreenHeight());
        }

        // Nickname


        // Sound effect volume


        // Music volume

        pom.saveAllToFile();
    }
}
