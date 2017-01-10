package com.example.ivankayzer.flashcards;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.*;


public class MainActivity extends AppCompatActivity {

    Button addWord;
    Button allWords;
    Button learn;
    EditText englishWord;
    EditText polishWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addWord = (Button) findViewById(R.id.addWord);
        englishWord = (EditText) findViewById(R.id.englishWord);
        polishWord = (EditText) findViewById(R.id.polishWord);
        allWords = (Button) findViewById(R.id.viewAll);
        learn = (Button) findViewById(R.id.learnButton);

        final Intent wordsIntent = new Intent(MainActivity.this, WordsActivity.class);
        final Intent playIntent = new Intent(MainActivity.this, PlayActivity.class);

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseSave(englishWord.getText().toString(), polishWord.getText().toString());
                startActivity(wordsIntent);
            }
        });

        allWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(wordsIntent);
            }
        });

        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(playIntent);
            }
        });
    }

    SQLiteDatabase databaseConnect() {
        SQLiteDatabase wordsDatabase = openOrCreateDatabase("flashcards", MODE_PRIVATE, null);
        wordsDatabase.execSQL("create table if not exists WordsActivity(english varchar, polish varchar);");
        return wordsDatabase;
    }

    private void databaseSave(String english, String polish) {
        if(english.length() != 0 && polish.length() != 0) {
            SQLiteDatabase db = databaseConnect();
            ContentValues values = new ContentValues();
            values.put("english", english);
            values.put("polish", polish);
            db.insert("WordsActivity", null, values);
            clearFields();
        }
    }

    public void clearFields() {
        englishWord.setText("");
        polishWord.setText("");
    }
}