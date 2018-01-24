package com.example.android.memorygame;

/**
 * Created by ferenckovacsx on 2018-01-24.
 */

public class CardModel {

    int backgroundResId;
    int foregroundResId;
    int id;

    public CardModel(int backgroundResId, int foregroundResId, int id) {
        this.backgroundResId = backgroundResId;
        this.foregroundResId = foregroundResId;
        this.id = id;
    }
}
