package com.example.android.memorygame.objects;

/**
 * Created by ferenckovacsx on 2018-01-24.
 */

public class Card {

    public int backgroundResId;
    public int foregroundResId;
    public int cardId;
    public int position;
    public boolean isMatched;

    public Card(int backgroundResId, int foregroundResId, int cardId, int position, boolean isMatched) {
        this.backgroundResId = backgroundResId;
        this.foregroundResId = foregroundResId;
        this.cardId = cardId;
        this.position = position;
        this.isMatched = isMatched;
    }

    @Override
    public String toString() {
        return "Card{" +
                "backgroundResId=" + backgroundResId +
                ", foregroundResId=" + foregroundResId +
                ", cardId=" + cardId +
                ", position=" + position +
                ", isMatched=" + isMatched +
                '}';
    }
}
