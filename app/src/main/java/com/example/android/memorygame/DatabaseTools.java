package com.example.android.memorygame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.memorygame.objects.ScoreEntry;

import java.util.ArrayList;

/**
 * Created by ferenckovacsx on 2018-01-27.
 */

public class DatabaseTools extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "highscoresdb";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_SCORE = "Score";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + DATABASE_NAME + "(Rank INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT NULL, Score INTEGER NULL)";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME;

    DatabaseTools(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    void insertNewScore(String name, int score) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(COLUMN_NAME, name);
            contentValues.put(COLUMN_SCORE, score);
            db.insert(DATABASE_NAME, null, contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    ArrayList<ScoreEntry> getAllScores() {
        ArrayList<ScoreEntry> scoreEntryList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DATABASE_NAME + " ORDER BY Score DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ScoreEntry scoreEntry = new ScoreEntry();
                scoreEntry.setRank(Integer.parseInt(cursor.getString(0)));
                scoreEntry.setName(cursor.getString(1));
                scoreEntry.setScore(Integer.parseInt(cursor.getString(2)));
                scoreEntryList.add(scoreEntry);

            } while (cursor.moveToNext());

        }

        db.close();
        cursor.close();
        return scoreEntryList;
    }
}
