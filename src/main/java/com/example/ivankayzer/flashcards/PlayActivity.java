package com.example.ivankayzer.flashcards;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    TextView playWord, playScore, playScoreText;
    EditText playTranslation;
    Button playSubmit, playSkip, back;

    Cursor allWords;

    String correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        playWord = (TextView) findViewById(R.id.playWord);
        playScore = (TextView) findViewById(R.id.playScore);
        playSubmit = (Button) findViewById(R.id.playSubmit);
        playTranslation = (EditText) findViewById(R.id.playTranslation);
        playScore = (TextView) findViewById(R.id.playScore);

        allWords = getAllWords();

        init();

        playSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int playScoreValue = Integer.parseInt(playScore.getText().toString());
                if(checkWord(playTranslation.getText().toString())) {
                    playScoreValue++;
                } else {
                    playScoreValue--;
                }
                playScore.setText(Integer.toString(playScoreValue));
                resetFields();
                init();
            }
        });

    }

    public Cursor getAllWords() {
        SQLiteDatabase wordsDatabase = openOrCreateDatabase("flashcards", MODE_PRIVATE, null);
        wordsDatabase.execSQL("create table if not exists WordsActivity(english varchar, polish varchar);");
        return wordsDatabase.rawQuery("select * from WordsActivity", null);
    }

    public int countWords() {
        return allWords.getCount();
    }

    public int randomWordNumber() {
        Random rnd = new Random();
        return rnd.nextInt(countWords() - 1) + 1;
    }

    public boolean checkWord(String input) {
        return input.equals(correct);
    }

    public void resetFields() {
        playWord.setText("");
        playTranslation.setText("");
    }

    public void init() {
        int random = randomWordNumber();
        allWords.move(random);
        playWord.setText(allWords.getString(0));
        correct = allWords.getString(1);
    }
}
