package com.itzjacki.afterglow.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

public class Song {

    private String name;

    // These might not be necessary to keep saved here, since PlayScreen also has them. Might be better to get them via a method from this class.
    private Color playerWedgeColor;
    private Color playerCircleColor;
    private Color backgroundColor;
    private Color textColor;
    private Color bulletColor;
    private Color frameColor;

    // A song's individual base volume. To help adjust song files which are too loud or quiet.
    // Is multiplied with user's volume choice. 0.5 is default value.
    // (for now at least. (if you read this in the future (after 2020), that probably stayed as the default value)).
    private float baseVolume;

    public Song(){
        // TODO: Make these load dynamically from song's info file. These details are used for testing purposes only.
        this.name = "Grandma (Destruction)";
        this.baseVolume = 0.5f;
    }

    public float getBaseVolume(){
        return baseVolume;
    }

    public String getFileDirectory(){
        return "songs/" + name + "/";
    }

    public FileHandle getMusicFile(){
        return Gdx.files.internal(getFileDirectory() + "music.mp3");
    }


}
