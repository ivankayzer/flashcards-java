package com.example.ivankayzer.flashcards;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WordsActivity extends AppCompatActivity {

    TextView words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        words = (TextView) findViewById(R.id.words);

        Cursor results = getAllWords();

        if(results.getCount() == 0) {
            words.setText("Nothing found!");
        } else {
            StringBuffer buffer = new StringBuffer();
            while (results.moveToNext()){
                buffer.append(results.getString(0) + " - ");
                buffer.append(results.getString(1) + "\n");
            }

            words.setText(buffer.toString());
        }

    }

    public Cursor getAllWords() {
        SQLiteDatabase wordsDatabase = openOrCreateDatabase("flashcards", MODE_PRIVATE, null);
        wordsDatabase.execSQL("create table if not exists WordsActivity(english varchar, polish varchar);");
        return wordsDatabase.rawQuery("select * from WordsActivity", null);
    }

}
