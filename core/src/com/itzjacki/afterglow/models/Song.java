package com.itzjacki.afterglow.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.itzjacki.afterglow.screens.PlayScreen;

import java.util.ArrayList;
import java.util.List;

public class Song {
    // Triggers if a necessary config field doesn't exist. Makes the song non-selectable from the menu.
    // TODO: Ensure that there are default values everywhere it makes sense
    // TODO: Make sure song is displayed as erroneous and can't be played
    private boolean hasConfigError = false;

    // Will probably often be the same in practice, but this allows for different values if needed.
    private String directoryName;
    private String name;

    private PlayScreen screen;

    // A song's individual base volume. To help adjust song files which are too loud or quiet.
    // Is multiplied with user's volume choice. 0.5 is default value.
    // (for now at least. (if you read this in the future (after 2020), that probably stayed as the default value)).
    private float baseVolume;

    // The time it takes for a bullet to spawn until it hits the middle in milliseconds.
    // Lower time alive means higher speed. A good default value is 600 ms.
    private float defaultTimeAlive;

    private List<int[]> noteList;

    public Song(String directoryName, PlayScreen screen) {
        this.directoryName = directoryName;

        this.screen = screen;

        String rawString = readInfoFile();
        String[] basicTrim = splitAndTrim(rawString, "}");
        for (String s : basicTrim) {
            setBaseInfo(s);
            setDefaultTheme(s);
        }
    }

    // TODO: This constructor should be completely replaced by one that dynamically loads everything from directoryName
    // This constructor is only used during development. Remove.
    public Song() {
        this.directoryName = "Grandma (Destruction)";
        this.baseVolume = 0.5f;
        this.defaultTimeAlive = 600;
        this.noteList = new ArrayList<>();
        // [time, type, state, timeActive, timeAlive]
        noteList.add(new int[]{1867, 1, 0, -1, -1});
        noteList.add(new int[]{2100, 1, 0, -1, -1});
        noteList.add(new int[]{3083, 1, 2, -1, -1});
        noteList.add(new int[]{3250, 1, 2, -1, -1});
        noteList.add(new int[]{5067, 1, 0, -1, -1});
        noteList.add(new int[]{5250, 1, 0, -1, -1});
        noteList.add(new int[]{6317, 1, 6, -1, -1});
        noteList.add(new int[]{6500, 1, 6, -1, -1});
        noteList.add(new int[]{8350, 1, 2, -1, -1});
        noteList.add(new int[]{8533, 1, 2, -1, -1});
        noteList.add(new int[]{9567, 1, 6, -1, -1});
        noteList.add(new int[]{9717, 1, 6, -1, -1});
        noteList.add(new int[]{11567, 1, 4, -1, -1});
        noteList.add(new int[]{11750, 1, 4, -1, -1});
        noteList.add(new int[]{12783, 1, 0, -1, -1});
        noteList.add(new int[]{12950, 1, 0, -1, -1});
        noteList.add(new int[]{14883, 1, 0, -1, -1});
        noteList.add(new int[]{15167, 1, 2, -1, -1});
        noteList.add(new int[]{15667, 1, 6, -1, -1});
        noteList.add(new int[]{16067, 1, 4, -1, -1});
        noteList.add(new int[]{16500, 1, 4, -1, -1});
        noteList.add(new int[]{16850, 1, 6, -1, -1});
        noteList.add(new int[]{17283, 1, 2, -1, -1});
        noteList.add(new int[]{17633, 1, 0, -1, -1});
        noteList.add(new int[]{18067, 1, 0, -1, -1});
        noteList.add(new int[]{18500, 1, 2, -1, -1});
        noteList.add(new int[]{18917, 1, 6, -1, -1});
        noteList.add(new int[]{19317, 1, 4, -1, -1});
        noteList.add(new int[]{19683, 1, 4, -1, -1});
        noteList.add(new int[]{20067, 1, 6, -1, -1});
        noteList.add(new int[]{20500, 1, 2, -1, -1});
        noteList.add(new int[]{20917, 1, 0, -1, -1});
        noteList.add(new int[]{21317, 1, 0, -1, -1});
        noteList.add(new int[]{21683, 1, 2, -1, -1});
        noteList.add(new int[]{22067, 1, 6, -1, -1});
        noteList.add(new int[]{22533, 1, 0, -1, -1});
        noteList.add(new int[]{22917, 1, 0, -1, -1});
        noteList.add(new int[]{23317, 1, 6, -1, -1});
        noteList.add(new int[]{23750, 1, 6, -1, -1});
        noteList.add(new int[]{24117, 1, 4, -1, -1});
        noteList.add(new int[]{24533, 1, 4, -1, -1});
        noteList.add(new int[]{24933, 1, 2, -1, -1});
        noteList.add(new int[]{25317, 1, 2, -1, -1});
        noteList.add(new int[]{25683, 1, 0, -1, -1});
        noteList.add(new int[]{26117, 1, 6, -1, -1});
        noteList.add(new int[]{26517, 1, 4, -1, -1});
        noteList.add(new int[]{26917, 1, 2, -1, -1});
        noteList.add(new int[]{27333, 1, 0, -1, -1});
        noteList.add(new int[]{27767, 2, 0, 1700, -1});
        noteList.add(new int[]{27767, 1, 0, -1, -1});
        noteList.add(new int[]{29767, 1, 6, -1, -1});
        noteList.add(new int[]{30200, 1, 0, -1, -1});
        noteList.add(new int[]{30567, 1, 2, -1, -1});
        noteList.add(new int[]{31000, 1, 0, -1, -1});
        noteList.add(new int[]{31400, 1, 6, -1, -1});
        noteList.add(new int[]{31850, 1, 4, -1, -1});
        noteList.add(new int[]{32217, 1, 2, -1, -1});
        noteList.add(new int[]{32633, 2, 0, 900, -1});
        noteList.add(new int[]{32633, 1, 0, -1, -1});
        noteList.add(new int[]{33783, 1, 6, -1, -1});
        noteList.add(new int[]{34267, 2, 2, 1600, -1});
        noteList.add(new int[]{34267, 1, 2, -1, -1});
        noteList.add(new int[]{36283, 1, 2, -1, -1});
        noteList.add(new int[]{36700, 1, 0, -1, -1});
        noteList.add(new int[]{37117, 1, 6, -1, -1});
        noteList.add(new int[]{37517, 2, 4, 700, -1});
        noteList.add(new int[]{37517, 1, 4, -1, -1});
        noteList.add(new int[]{38317, 2, 2, 1700, -1});
        noteList.add(new int[]{38317, 1, 2, -1, -1});
        noteList.add(new int[]{40376, 1, 4, -1, -1});
        noteList.add(new int[]{40783, 2, 0, 1700, -1});
        noteList.add(new int[]{40783, 1, 0, -1, -1});
        noteList.add(new int[]{42817, 1, 6, -1, -1});
        noteList.add(new int[]{43217, 1, 2, -1, -1});
        noteList.add(new int[]{43617, 1, 6, -1, -1});
        noteList.add(new int[]{44000, 1, 2, -1, -1});
        noteList.add(new int[]{44417, 1, 0, -1, -1});
        noteList.add(new int[]{44800, 1, 6, -1, -1});
        noteList.add(new int[]{45217, 1, 4, -1, -1});
        noteList.add(new int[]{45583, 2, 2, 800, -1});
        noteList.add(new int[]{45583, 1, 2, -1, -1});
        noteList.add(new int[]{46800, 1, 6, -1, -1});
        noteList.add(new int[]{47200, 1, 0, -1, -1});
        noteList.add(new int[]{49200, 1, 2, -1, -1});
        noteList.add(new int[]{49633, 1, 0, -1, -1});
        noteList.add(new int[]{50067, 1, 6, -1, -1});
        noteList.add(new int[]{50500, 2, 0, 500, -1});
        noteList.add(new int[]{50500, 1, 0, -1, -1});
        noteList.add(new int[]{51217, 2, 2, 2000, -1});
        noteList.add(new int[]{51217, 1, 2, -1, -1});
        noteList.add(new int[]{53700, 2, 0, 650, -1});
        noteList.add(new int[]{53700, 1, 0, -1, -1});
        noteList.add(new int[]{54550, 2, 2, 500, -1});
        noteList.add(new int[]{54550, 1, 2, -1, -1});
        noteList.add(new int[]{55250, 2, 4, 500, -1});
        noteList.add(new int[]{55250, 1, 4, -1, -1});
        noteList.add(new int[]{55967, 1, 6, -1, -1});
        noteList.add(new int[]{56167, 2, 0, 400, -1});
        noteList.add(new int[]{56167, 1, 0, -1, -1});
        noteList.add(new int[]{56733, 1, 2, -1, -1});
        noteList.add(new int[]{56950, 2, 0, 650, -1});
        noteList.add(new int[]{56950, 1, 0, -1, -1});
        noteList.add(new int[]{57800, 2, 4, 550, -1});
        noteList.add(new int[]{57800, 1, 4, -1, -1});
        noteList.add(new int[]{58550, 2, 6, 400, -1});
        noteList.add(new int[]{58550, 1, 6, -1, -1});
        noteList.add(new int[]{59183, 1, 6, -1, -1});
        noteList.add(new int[]{59400, 2, 0, 350, -1});
        noteList.add(new int[]{59400, 1, 0, -1, -1});
        noteList.add(new int[]{59950, 1, 2, -1, -1});
        noteList.add(new int[]{60167, 2, 0, 600, -1});
        noteList.add(new int[]{60167, 1, 0, -1, -1});
        noteList.add(new int[]{61017, 2, 2, 550, -1});
        noteList.add(new int[]{61017, 1, 2, -1, -1});
        noteList.add(new int[]{61767, 2, 4, 450, -1});
        noteList.add(new int[]{61767, 1, 4, -1, -1});
        noteList.add(new int[]{62450, 1, 6, -1, -1});
        noteList.add(new int[]{62600, 1, 0, -1, -1});
        noteList.add(new int[]{63033, 1, 2, -1, -1});
        noteList.add(new int[]{63467, 2, 4, 2800, -1});
        noteList.add(new int[]{63467, 1, 4, -1, -1});
        noteList.add(new int[]{66650, 1, 0, -1, -1});

    }

    private String readInfoFile() {
        FileHandle infoFile = Gdx.files.local(getFileDirectory() + "song_map.txt");
        return infoFile.readString();
    }

    // Helper method that splits a String on a character, then trims all items in the resulting array
    private String[] splitAndTrim(String input, String splitDivider) {
        String[] splitString = input.split(splitDivider);
        String[] result = splitString.clone();
        for (int i = 0; i < splitString.length; i++) {
            result[i] = splitString[i].trim();
        }
        return result;
    }

    // Reads the value of a color and returns it as a Color object
    private Color colorFromConfigString(String[] configArray, String configKey){
        return new Color(Color.valueOf(getNextAfterMatch(configArray, configKey)));
    }

    // Sets basic info of song on creation
    private void setBaseInfo(String inputString) {
        if (inputString.startsWith("baseInfo{")) {
            String trimmedString = inputString.substring("baseInfo{".length());
            String[] configArray = splitAndTrim(trimmedString, "'");
            this.name = getNextAfterMatch(configArray, "name=");
            this.baseVolume = Float.parseFloat(getNextAfterMatch(configArray, "baseVolume="));
            this.defaultTimeAlive = Integer.parseInt(getNextAfterMatch(configArray, "defaultTimeAlive="));
        }
    }

    // Sets visual theme info of song on creation (does not handle visual events, only used on creation of Song)
    private void setDefaultTheme(String inputString) {
        if (inputString.startsWith("defaultTheme{")) {
            String trimmedString = inputString.substring("defaultTheme{".length());
            String[] configArray = splitAndTrim(trimmedString, "'");
            // Colors are given in RGBA hex format.
            this.screen.setPlayerWedgeColor(colorFromConfigString(configArray, "playerWedge="));
            this.screen.setPlayerCircleColor(colorFromConfigString(configArray, "playerCircle="));
            this.screen.setBackgroundColor(colorFromConfigString(configArray, "background="));
            this.screen.setTextColor(colorFromConfigString(configArray, "textColor="));
            this.screen.setNoteColor(colorFromConfigString(configArray, "noteColor="));
            this.screen.setFrameColor(colorFromConfigString(configArray, "frameColor="));
        }
    }

    // Returns the element that comes next in the list after the one that matches
    private String getNextAfterMatch(String[] configArray, String configKey) {
        boolean returnNext = false;
        for (String s : configArray) {
            if (returnNext) {
                return s;
            }
            if (s.equals(configKey)) {
                returnNext = true;
            }
        }
        hasConfigError = true;
        return "";
    }

    public float getBaseVolume() {
        return baseVolume;
    }

    public String getFileDirectory() {
        return "songs/" + directoryName + "/";
    }

    public FileHandle getMusicFile() {
        return Gdx.files.internal(getFileDirectory() + "music.mp3");
    }

    public float getDefaultTimeAlive() {
        return defaultTimeAlive;
    }

    public List<int[]> getNoteList() {
        return noteList;
    }
}
