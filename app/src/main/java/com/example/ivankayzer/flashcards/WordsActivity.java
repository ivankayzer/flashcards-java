package com.example.ivankayzer.flashcards;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordsActivity extends AppCompatActivity {

    private ListView listView;
    ArrayAdapter<String> listAdapter;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        database = new Database(this);
        listView = (ListView) findViewById(R.id.list_words);
        showWords();
    }

    public void showWords() {
        SQLiteDatabase db = database.getReadableDatabase();
        ArrayList<String> wordsList =  new ArrayList<>();

        Cursor results = db.query("words", new String[]{"_id", "english", "polish"}, null, null, null, null, null);

        while (results.moveToNext()){
            wordsList.add(results.getString(1) + " - " + results.getString(2));
        }
        if (listAdapter == null) {
            listAdapter = new ArrayAdapter<>(this, R.layout.word, R.id.word_pair, wordsList);
            listView.setAdapter(listAdapter);
        } else {
            listAdapter.clear();
            listAdapter.addAll(wordsList);
            listAdapter.notifyDataSetChanged();
        }

        results.close();
        db.close();
    }

    public void deleteWord(View view) {

        SQLiteDatabase db = database.getWritableDatabase();
        View parent = (View) view.getParent();
        TextView wordsTextView = (TextView) parent.findViewById(R.id.word_pair);

        String words = String.valueOf(wordsTextView.getText());
        String[] wordsSplit = words.split("-");

        db.delete("words", "english = ?", new String[]{wordsSplit[0].trim()});
        db.close();

        showWords();
    }

}