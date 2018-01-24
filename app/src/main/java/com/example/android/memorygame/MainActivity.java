package com.example.android.memorygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView gridRecyclerView;
    GridLayoutManager gridLayoutManager;
    ArrayList<CardModel> cards;

    private int imageResId[] = {R.drawable.card_background, R.drawable.card_cc, R.drawable.card_cloud, R.drawable.card_console,
            R.drawable.card_multiscreen, R.drawable.card_remote, R.drawable.card_tablet, R.drawable.card_vr, R.drawable.card_tv,
            R.drawable.card_cc, R.drawable.card_cloud, R.drawable.card_console,
            R.drawable.card_multiscreen, R.drawable.card_remote, R.drawable.card_tablet, R.drawable.card_vr, R.drawable.card_tv};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardModel card_cc = instantiateCardModel(R.drawable.card_cc, 1);
        CardModel card_cloud = instantiateCardModel(R.drawable.card_cloud, 1);
        CardModel card_console = instantiateCardModel(R.drawable.card_console, 1);
        CardModel card_multiscreen = instantiateCardModel(R.drawable.card_multiscreen, 1);
        CardModel card_remote = instantiateCardModel(R.drawable.card_remote, 1);
        CardModel card_tablet = instantiateCardModel(R.drawable.card_tablet, 1);
        CardModel card_vr = instantiateCardModel(R.drawable.card_vr, 1);
        CardModel card_tv = instantiateCardModel(R.drawable.card_tv, 1);

        cards = new ArrayList<>();
        cards.add(card_cc);
        cards.add(card_cloud);
        cards.add(card_console);
        cards.add(card_multiscreen);
        cards.add(card_remote);
        cards.add(card_tablet);
        cards.add(card_vr);
        cards.add(card_tv);

        gridRecyclerView = findViewById(R.id.grid_recyclerview);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 4);
        gridRecyclerView.setLayoutManager(gridLayoutManager);
        GridAdapter gridAdapter = new GridAdapter(MainActivity.this, cards);
        gridRecyclerView.setAdapter(gridAdapter);
    }

    static CardModel instantiateCardModel (int foregroundResId, int cardId){

        return new CardModel(R.drawable.card_background, foregroundResId, cardId);

    }


}
