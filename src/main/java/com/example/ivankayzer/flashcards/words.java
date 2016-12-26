package com.example.ivankayzer.flashcards;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class words extends AppCompatActivity {

    TextView words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        words = (TextView) findViewById(R.id.words);

    }



}
