package com.example.android.memorygame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.memorygame.R;
import com.example.android.memorygame.objects.ScoreEntry;

import java.util.ArrayList;

/**
 * Created by ferenckovacsx on 2018-01-28.
 */

public class HighScoresAdapter extends ArrayAdapter<ScoreEntry> {

    private ArrayList<ScoreEntry> scoreEntries;

    private static class ViewHolder {
        TextView rankTextView;
        TextView nameTextView;
        TextView scoreTextView;
    }

    public HighScoresAdapter(ArrayList<ScoreEntry> scoreEntries, Context context) {
        super(context, R.layout.highscore_entry_item, scoreEntries);
        this.scoreEntries = scoreEntries;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ScoreEntry scoreEntry = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.highscore_entry_item, parent, false);
            viewHolder.rankTextView = convertView.findViewById(R.id.rankTextView);
            viewHolder.nameTextView = convertView.findViewById(R.id.nameTextView);
            viewHolder.scoreTextView = convertView.findViewById(R.id.scoreTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.rankTextView.setText(String.valueOf(position + 1));
        viewHolder.nameTextView.setText(scoreEntry.name);
        viewHolder.scoreTextView.setText(String.valueOf(scoreEntry.score));

        return convertView;
    }
}