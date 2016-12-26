package com.example.ivankayzer.flashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button addWord;
    EditText englishWord;
    EditText polishWord;

    public void init() {
        addWord = (Button) findViewById(R.id.addWord);
        englishWord = (EditText) findViewById(R.id.englishWord);
        polishWord = (EditText) findViewById(R.id.polishWord);

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, words.class);
                intent.putExtra("english", englishWord.getText().toString());
                intent.putExtra("polish", polishWord.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
