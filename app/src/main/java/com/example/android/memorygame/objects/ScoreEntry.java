package com.example.android.memorygame.objects;

/**
 * Created by ferenckovacsx on 2018-01-27.
 */

public class ScoreEntry {

    public int rank;
    public String name;
    public int score;

    public ScoreEntry() {
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
