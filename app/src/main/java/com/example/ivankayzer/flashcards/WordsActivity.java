package com.example.ivankayzer.flashcards;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class WordsActivity extends AppCompatActivity {

    TextView words;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        Cursor results = getAllWords();

        listView = (ListView) findViewById(R.id.list_words);

        if(results.getCount() == 0) {
            words.setText("Nothing found!");
        } else {

            ArrayList<String> wordsList =  new ArrayList<>();

            while (results.moveToNext()){
                wordsList.add(results.getString(1) + " - " + results.getString(2));
            }
            ArrayAdapter<String> arr = new ArrayAdapter<>(this, R.layout.word, R.id.task_title, wordsList);

            listView.setAdapter(arr);

        }

    }

    public Cursor getAllWords() {
        SQLiteDatabase wordsDatabase = openOrCreateDatabase("flashcards", MODE_PRIVATE, null);
        wordsDatabase.execSQL("create table if not exists WordsActivity(_id INTEGER PRIMARY KEY AUTOINCREMENT, english VARCHAR, polish VARCHAR);");
        return wordsDatabase.rawQuery("select * from WordsActivity", null);
    }

    public void deleteWord(View view) {
        SQLiteDatabase wordsDatabase = openOrCreateDatabase("flashcards", MODE_PRIVATE, null);
        View parent = (View) view.getParent();
        TextView wordsTextView = (TextView) parent.findViewById(R.id.task_title);
        String words = String.valueOf(wordsTextView.getText());
        String[] wordsSplit = words.split("-");

        wordsDatabase.execSQL("delete from WordsActivity where english = '" + wordsSplit[0].trim() + "';");
    }

}