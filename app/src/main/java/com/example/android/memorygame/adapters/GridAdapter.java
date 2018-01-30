package com.example.android.memorygame.adapters;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.memorygame.R;
import com.example.android.memorygame.objects.Card;

import java.util.ArrayList;

/**
 * Created by ferenckovacsx on 2018-01-24.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CustomViewHolder> {

    private final ArrayList<Card> cards;
    private int firstFlippedCardPosition = -1;
    private int flipCount;
    private int firstCardId;
    private int secondCardId;
    private int score;
    private int matchedPairCount;
    static int cardImageViewSize;
    private boolean isNewGame;
    private boolean isWinner = false;

    private OnScoreChangeListener mOnScoreChangeListener;

    public GridAdapter(ArrayList<Card> cards, int score, int matchedPairCount, int cardImageViewSize, boolean isNewGame) {

        this.cards = cards;
        this.score = score;
        this.matchedPairCount = matchedPairCount;
        GridAdapter.cardImageViewSize = cardImageViewSize;
        this.isNewGame = isNewGame;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View gridView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, null);

        return new CustomViewHolder(gridView);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int pos) {

        final int position = holder.getAdapterPosition();

        //If this is a new game, reset all data. If not a new game (restored), keep old data.
        if (isNewGame) {
            cards.get(position).isMatched = false;
            matchedPairCount = 0;
            score = 0;
        }

        //If a card was previously MATCHED, make it INVISIBLE. If not matched yet, show its background.
        if (!cards.get(position).isMatched) {
            holder.cardImageView.setImageResource(cards.get(position).backgroundResId);
        } else if (cards.get(position).isMatched) {
            holder.cardImageView.setVisibility(View.INVISIBLE);
        }


        //On item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                isNewGame = false;

                if (!cards.get(position).isMatched && position != firstFlippedCardPosition) {

                    flipCount++;

                    switch (flipCount) {
                        //First card is flipped
                        case 1:
                            firstFlippedCardPosition = position;
                            firstCardId = cards.get(position).cardId;
                            holder.cardImageView.setImageResource(cards.get(position).foregroundResId);

                            Log.i("1st flip", "card id: " + firstCardId);

                            break;

                        //Second card is flipped. Check if it's a match, calculate score. Reset flipcount.
                        case 2:
                            secondCardId = cards.get(position).cardId;
                            holder.cardImageView.setImageResource(cards.get(position).foregroundResId);

                            Log.i("2nd flip", "card 1 id: " + firstCardId);
                            Log.i("2nd flip", "card 2 id: " + secondCardId);

                            //If the first flipped card and the second flipped card are identical, it's a match.
                            if (firstCardId == secondCardId) {
                                score += 5;
                                matchedPairCount += 1;
                                Log.i("card matching", "IT'S A MATCH! :) New score: " + score);
                                Log.i("card matching", "IT'S A MATCH! :) Matched pairs: " + matchedPairCount);

                                //Set isMatched property to true
                                for (Card card : cards) {
                                    if (firstCardId == card.cardId) {
                                        card.isMatched = true;
                                    }
                                }

                                //Notifiy onPairMatched listener about the new match.
                                mOnScoreChangeListener.onPairMatched(cards.get(position).cardId, matchedPairCount);

                                //If all 8 pairs are matched, notify OnScoreChangeListener.onFinalScore
                                if (matchedPairCount == 8) {
                                    isWinner = true;
                                    Log.i("CONGRATS", "YOU HAVE WON. SCORE: " + score);
                                    if (mOnScoreChangeListener != null) {
                                        mOnScoreChangeListener.onFinalScore(score);
                                    }
                                }

                            } else {
                                score -= 2;
                                Log.i("card matching", "NOT A MATCH :(. New score: " + score);
                                cards.get(position).isMatched = false;
                            }

                            firstFlippedCardPosition = -1;

                            //implement 1 second pause to allow player to see the second card
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    flipCount = 0;
                                    notifyDataSetChanged();

                                    //notify OnScoreChangeListener about the new score
                                    if (!isWinner) {
                                        if (mOnScoreChangeListener != null) {
                                            mOnScoreChangeListener.onScoreChanged(score);
                                        }
                                    }

                                }
                            }, 1000);

                            break;

                        default:
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView cardImageView;

        CustomViewHolder(View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.card_imageview);

            //Set proper size of the cardImageView. "cardImageViewSize" was calculated in the MainActivity based on the actual screen size.
            android.view.ViewGroup.LayoutParams layoutParams = cardImageView.getLayoutParams();
            layoutParams.width = cardImageViewSize;
            layoutParams.height = cardImageViewSize;
            cardImageView.setLayoutParams(layoutParams);
            cardImageView.requestLayout();

        }
    }

    //Listener for score change
    public interface OnScoreChangeListener {
        void onScoreChanged(int score);

        void onFinalScore(int score);

        void onPairMatched(int cardId, int matchedPairCount);
    }

    public void setOnScoreChangeListener(OnScoreChangeListener onScoreChangeListener) {
        this.mOnScoreChangeListener = onScoreChangeListener;
    }

}
