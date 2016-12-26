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
    EditText englishWord;
    EditText polishWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addWord = (Button) findViewById(R.id.addWord);
        englishWord = (EditText) findViewById(R.id.englishWord);
        polishWord = (EditText) findViewById(R.id.polishWord);

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, words.class);
                databaseSave(englishWord.getText().toString(), polishWord.getText().toString());
                startActivity(intent);
            }
        });
    }

    SQLiteDatabase databaseConnect() {
        SQLiteDatabase wordsDatabase = openOrCreateDatabase("flashcards", MODE_PRIVATE, null);
        wordsDatabase.execSQL("create table if not exists words(english varchar, polish varchar);");
        return wordsDatabase;
    }

    private void databaseSave(String english, String polish) {
        SQLiteDatabase db = databaseConnect();
        ContentValues values = new ContentValues();
        values.put("english", english);
        values.put("polish", polish);
        db.insert("words", null, values);
    }
}
