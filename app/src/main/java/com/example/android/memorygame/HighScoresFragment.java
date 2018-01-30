package com.example.android.memorygame;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.android.memorygame.adapters.HighScoresAdapter;
import com.example.android.memorygame.objects.ScoreEntry;

import java.util.ArrayList;


public class HighScoresFragment extends Fragment {

    ArrayList<ScoreEntry> scoreEntries;
    ListView highScoresListView;
    HighScoresAdapter highScoresAdapter;
    FrameLayout emptyListFrameLayout;

    private OnFragmentInteractionListener mListener;

    public HighScoresFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View highScoresView = inflater.inflate(R.layout.fragment_high_scores, container, false);
        emptyListFrameLayout = highScoresView.findViewById(R.id.emptyListFrameLayout);
        highScoresListView = highScoresView.findViewById(R.id.highScoresListView);

        //get scores from database
        DatabaseTools dbTools = new DatabaseTools(getContext());
        scoreEntries = dbTools.getAllScores();

        if (scoreEntries.size() == 0) {
            emptyListFrameLayout.setVisibility(View.VISIBLE);

        } else {
            emptyListFrameLayout.setVisibility(View.GONE);
            highScoresListView.addHeaderView(getLayoutInflater().inflate(R.layout.listview_header, highScoresListView, false));
            highScoresAdapter = new HighScoresAdapter(scoreEntries, getContext());
            highScoresListView.setAdapter(highScoresAdapter);
        }

        return highScoresView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
