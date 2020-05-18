package com.itzjacki.afterglow.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Song {

    private String name;

    // These might not be necessary to keep saved here, since PlayScreen also has them. Might be better to get them via a method from this class.
    private Color playerWedgeColor;
    private Color playerCircleColor;
    private Color backgroundColor;
    private Color textColor;
    private Color noteColor;
    private Color frameColor;

    // A song's individual base volume. To help adjust song files which are too loud or quiet.
    // Is multiplied with user's volume choice. 0.5 is default value.
    // (for now at least. (if you read this in the future (after 2020), that probably stayed as the default value)).
    private float baseVolume;

    // The time it takes for a bullet to spawn until it hits the middle in milliseconds.
    // Lower time alive means higher speed. A good default value is 600 ms.
    private float defaultTimeAlive;

    private List<int[]> noteList;

    public Song(){
        // TODO: Make these load dynamically from song's info file. These details are used for testing purposes only.
        this.name = "Grandma (Destruction)";
        this.baseVolume = 0.5f;
        this.defaultTimeAlive = 600;
        this.noteList = new ArrayList<>();
        // [time, type, state, timeActive, timeAlive]
        noteList.add(new int[]{3000, 1, 0, -1, -1});
        noteList.add(new int[]{4000, 1, 2, -1, -1});
        noteList.add(new int[]{5000, 1, 5, -1, -1});
        noteList.add(new int[]{6000, 2, 4, 1500, -1});
        noteList.add(new int[]{7000, 3, -1, -1, -1});
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

    public float getDefaultTimeAlive(){
        return defaultTimeAlive;
    }

    public List<int[]> getNoteList() {
        return noteList;
    }
}
