package com.example.ivankayzer.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class Database extends SQLiteOpenHelper{

    private static final String DB_NAME = "flashcards";


    private class Word implements BaseColumns {

        private static final String TABLE = "words";

    }

    public Database(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Word.TABLE + " (" + Word._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " english VARCHAR NOT NULL, polish VARCHAR NOT NULL);";

        db.execSQL(createTable);
    }

    public void onUpgrade(SQLiteDatabase db, int a, int b) {}


}
