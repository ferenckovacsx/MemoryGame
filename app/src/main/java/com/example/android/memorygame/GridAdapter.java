package com.example.android.memorygame;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by ferenckovacsx on 2018-01-24.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CustomViewHolder> {

    Context context;
    ArrayList<CardModel> cards;

    public GridAdapter(Context context, ArrayList<CardModel> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View gridView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, null);
        CustomViewHolder customViewHolder = new CustomViewHolder(gridView);

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        holder.cardImageView.setImageResource(cards.get(position).foregroundResId);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView cardImageView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.card_imageview);
        }
    }
}
