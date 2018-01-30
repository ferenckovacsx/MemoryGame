package com.example.android.memorygame;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.memorygame.adapters.GridAdapter;
import com.example.android.memorygame.objects.Card;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView scoreTextView;
    ImageView highScoresBtn;
    RecyclerView gridRecyclerView;
    GridLayoutManager gridLayoutManager;

    ArrayList<Card> cards;
    ArrayList<Integer> orderedCards;
    ArrayList<Integer> matchedCardsID = new ArrayList<>();

    final String CURRENT_SCORE = "current_score";
    final String MATCHED_PAIR_COUNT = "matched_pair_count";
    final String MATCHED_CARDS_LIST = "matched_cards_list";
    final String ORDER_OF_CARDS = "order_of_cards";

    int scoreTextViewValue;
    int matchedPairCount;
    Parcelable savedRecyclerLayoutState;

    Card card_cc, card_cloud, card_console, card_multiscreen, card_remote, card_tablet, card_vr, card_tv, card_cc2, card_cloud2, card_console2, card_multiscreen2, card_remote2, card_tablet2, card_vr2, card_tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cards = initCardsList();

        if (savedInstanceState != null) {

            //Restore each cards state and position
            scoreTextViewValue = savedInstanceState.getInt(CURRENT_SCORE);
            matchedCardsID = savedInstanceState.getIntegerArrayList(MATCHED_CARDS_LIST);
            matchedPairCount = savedInstanceState.getInt(MATCHED_PAIR_COUNT);
            orderedCards = savedInstanceState.getIntegerArrayList(ORDER_OF_CARDS);
//            savedRecyclerLayoutState = savedInstanceState.getParcelable("RECYCLE");

            ArrayList<Card> orderedCardsList = new ArrayList<>();

            //Get original position of each card
            for (Integer position : orderedCards) {
                for (Card card : cards) {
                    if (card.position == position) {
                        orderedCardsList.add(card);
                    }
                }
            }

            //Set flipped/matched state of each card
            for (Card card : orderedCardsList) {
                for (Integer id : matchedCardsID) {
                    if (card.cardId == id) {
                        card.isMatched = true;
                    }
                }
            }

            startGame(false, orderedCardsList);

        } else {

            startGame(true, cards);
        }

        scoreTextView = findViewById(R.id.currentScoreTextView);
        highScoresBtn = findViewById(R.id.highScoresButton);

        scoreTextView.setText(String.valueOf(scoreTextViewValue));

        highScoresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HighScoresFragment fragment = new HighScoresFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragmentContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        Log.i("Main", "onSaveInstance score: " + scoreTextViewValue);
        state.putInt(CURRENT_SCORE, scoreTextViewValue);
        state.putInt(MATCHED_PAIR_COUNT, matchedPairCount);
        state.putIntegerArrayList(MATCHED_CARDS_LIST, matchedCardsID);
        state.putIntegerArrayList(ORDER_OF_CARDS, orderedCards);
//        state.putParcelable("RECYCLE", gridRecyclerView.getLayoutManager().onSaveInstanceState());


    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);


        if (state != null) {
            scoreTextViewValue = state.getInt(CURRENT_SCORE);
            matchedPairCount = state.getInt(MATCHED_PAIR_COUNT);
            matchedCardsID = state.getIntegerArrayList(MATCHED_CARDS_LIST);
            orderedCards = state.getIntegerArrayList(ORDER_OF_CARDS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        scoreTextView.setText(String.valueOf(scoreTextViewValue));
        gridRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);

    }

    //This method measures screen height and weight and sets the size of the cards accordingly (width/4)
    int getImageViewSize() {

        int cardImageViewSize;
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            cardImageViewSize = outMetrics.widthPixels / 4;
        } else {
            cardImageViewSize = (outMetrics.heightPixels - getStatusBarHeight() - 10) / 4;
        }
        return cardImageViewSize;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



    ArrayList<Card> initCardsList() {

        card_cc = instantiateCard(R.drawable.card_cc, 1, 1);
        card_cloud = instantiateCard(R.drawable.card_cloud, 2, 2);
        card_console = instantiateCard(R.drawable.card_console, 3, 3);
        card_multiscreen = instantiateCard(R.drawable.card_multiscreen, 4, 4);
        card_remote = instantiateCard(R.drawable.card_remote, 5, 5);
        card_tablet = instantiateCard(R.drawable.card_tablet, 6, 6);
        card_vr = instantiateCard(R.drawable.card_vr, 7, 7);
        card_tv = instantiateCard(R.drawable.card_tv, 8, 8);
        card_cc2 = instantiateCard(R.drawable.card_cc, 1, 9);
        card_cloud2 = instantiateCard(R.drawable.card_cloud, 2, 10);
        card_console2 = instantiateCard(R.drawable.card_console, 3, 11);
        card_multiscreen2 = instantiateCard(R.drawable.card_multiscreen, 4, 12);
        card_remote2 = instantiateCard(R.drawable.card_remote, 5, 13);
        card_tablet2 = instantiateCard(R.drawable.card_tablet, 6, 14);
        card_vr2 = instantiateCard(R.drawable.card_vr, 7, 15);
        card_tv2 = instantiateCard(R.drawable.card_tv, 8, 16);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card_cc);
        cards.add(card_cloud);
        cards.add(card_console);
        cards.add(card_multiscreen);
        cards.add(card_remote);
        cards.add(card_tablet);
        cards.add(card_vr);
        cards.add(card_tv);
        cards.add(card_cc2);
        cards.add(card_cloud2);
        cards.add(card_console2);
        cards.add(card_multiscreen2);
        cards.add(card_remote2);
        cards.add(card_tablet2);
        cards.add(card_vr2);
        cards.add(card_tv2);

        return cards;
    }


    static Card instantiateCard(int foregroundResId, int cardId, int position) {
        return new Card(R.drawable.card_background, foregroundResId, cardId, position, false);
    }

    void startGame(boolean isNewGame, final ArrayList<Card> cards) {

        orderedCards = new ArrayList<>();

        if (isNewGame) {
            //shuffle Cards. Save the order of the cards for retaining instance.
            Collections.shuffle(cards);
            orderedCards = new ArrayList<>();
            for (Card card : cards) {
                orderedCards.add(card.position);
            }
        }

        //Set up Recyleview, GridLayoutManager and adapter
        gridRecyclerView = findViewById(R.id.grid_recyclerview);
        gridRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 4);
        gridRecyclerView.setLayoutManager(gridLayoutManager);
        final GridAdapter gridAdapter = new GridAdapter(cards, scoreTextViewValue, matchedPairCount, getImageViewSize(), isNewGame);
        gridRecyclerView.setAdapter(gridAdapter);


        gridAdapter.setOnScoreChangeListener(new GridAdapter.OnScoreChangeListener() {
            @Override
            public void onScoreChanged(int score) {

                scoreTextViewValue = score;
                scoreTextView.setText(String.valueOf(score));

            }

            @Override
            public void onFinalScore(int score) {

                Bundle bundle = new Bundle();
                bundle.putInt("score", score);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                CongratsDialogFragment dialogFragment = new CongratsDialogFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.setCancelable(false);
                dialogFragment.show(ft, "dialog");

                matchedCardsID.clear();

                startGame(true, cards);
                scoreTextViewValue = 0;
                scoreTextView.setText(String.valueOf(0));

            }

            //save paired cards for restoring instance
            @Override
            public void onPairMatched(int cardIdFromListener, int matchedPairCountFromListener) {

                for (Card card : cards) {
                    if (card.cardId == cardIdFromListener) {
                        matchedCardsID.add(card.cardId);
                        card.isMatched = true;
                    }
                }
                matchedPairCount = matchedPairCountFromListener;
            }
        });
    }

}
