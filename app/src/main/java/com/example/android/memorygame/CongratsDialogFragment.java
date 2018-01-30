package com.example.android.memorygame;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ferenckovacsx on 2018-01-29.
 */

public class CongratsDialogFragment extends DialogFragment {

    EditText nameEditText;
    Button submitButton;
    Button cancelButton;
    TextView validationRulesTextView;
    TextView yourScoreTextView;

    int score;
    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
        getDialog().getWindow().requestFeature(STYLE_NO_FRAME);

        View rootView = inflater.inflate(R.layout.fragment_dialog_congrats, container, false);

        score = getArguments().getInt("score");

        submitButton = rootView.findViewById(R.id.submitNameButton);
        cancelButton = rootView.findViewById(R.id.cancelButton);
        nameEditText = rootView.findViewById(R.id.nameEditText);
        validationRulesTextView = rootView.findViewById(R.id.validationRulesTextView);
        yourScoreTextView = rootView.findViewById(R.id.yourScoreTextview);

        yourScoreTextView.setText(String.format(getString(R.string.your_score_is), String.valueOf(score)));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = nameEditText.getText().toString();

                if (name.length() < 4 || name.length() > 10 || name.matches(".*[^A-Za-záÁéÉíÍóÓöÖőŐúÚüÜűŰõÕôÔûÛ].*")) {
                    nameEditText.setBackgroundColor(getResources().getColor(R.color.errorHintTextColor));
                    validationRulesTextView.setVisibility(View.VISIBLE);

                    nameEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {
                        }

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() == 4 || s.length() == 10) {
                                nameEditText.setBackgroundColor(Color.TRANSPARENT);
                                validationRulesTextView.setVisibility(View.GONE);
                            }
                        }
                    });


                } else {

                    //insert new score to the database
                    DatabaseTools dbTools = new DatabaseTools(getContext());
                    dbTools.insertNewScore(name, score);

                    //dismiss prompt dialog
                    dismiss();

                    //open highscores fragment
                    HighScoresFragment fragment = new HighScoresFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(R.id.fragmentContainer, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Are you sure?");
                alertDialogBuilder
                        .setMessage("If you quit, your score will not be included in the leaderboard.")
                        .setCancelable(false)
                        .setPositiveButton("I am sure", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dismiss();
                            }
                        })
                        .setNegativeButton("Go back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return rootView;
    }
}
