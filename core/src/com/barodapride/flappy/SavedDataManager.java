package com.barodapride.flappy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

// Singleton class, code copied from http://www.javaworld.com/article/2073352/core-java/simply-singleton.html
// A singleton is just a class that can only be instantiated once
public class SavedDataManager {

    private static final String KEY_PREFERENCES = "prefs";
    private static final String KEY_HIGH_SCORE = "high score";

    private static SavedDataManager instance = null;
    private static int highScore;

    protected SavedDataManager(){
        // Exists only to defeat instantiation.
    }

    public static SavedDataManager getInstance(){
        if (instance == null){
            instance = new SavedDataManager();
        }
        return instance;
    }

    /**
     * Initialize all the saved values from the Preferences
     */
    public void load(){
        Preferences prefs = Gdx.app.getPreferences(KEY_PREFERENCES);
        highScore = prefs.getInteger(KEY_HIGH_SCORE, 0);
    }

    /**
     * Save all the values to Preferences
     */
    public void save(){
        Preferences prefs = Gdx.app.getPreferences(KEY_PREFERENCES);
        prefs.putInteger(KEY_HIGH_SCORE, highScore);

        prefs.flush();
    }





    public void setHighScore(int score){
        if (score > highScore)
            highScore = score;
    }

    public int getHighScore(){
        return highScore;
    }



}
