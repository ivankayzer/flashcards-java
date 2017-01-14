package com.example.ivankayzer.flashcards;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;

import org.w3c.dom.Text;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    TextView playWord, playScore;
    EditText playTranslation;
    Button playSubmit, playSkip, back;
    private Database database;
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
        playSkip = (Button) findViewById(R.id.playSkip);
        back = (Button) findViewById(R.id.back);

        database = new Database(this);

        SQLiteDatabase db = database.getReadableDatabase();
        allWords = db.rawQuery("select * from words", null);

        init();

        playSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int playScoreValue = Integer.parseInt(playScore.getText().toString());
                if(checkWord(playTranslation.getText().toString())) {
                    playScoreValue++;
                    paintGreen();
                } else {
                    playScoreValue--;
                    paintRed();
                }

                checkNegative(playScoreValue);
                playScore.setText(Integer.toString(playScoreValue));
                resetFields();
                init();
            }
        });

        playSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int playScoreValue = Integer.parseInt(playScore.getText().toString());
                playScoreValue--;
                checkNegative(playScoreValue);
                playScore.setText(Integer.toString(playScoreValue));
                resetFields();
                init();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(main);
            }
        });

    }

    public int countWords() {
        return allWords.getCount();
    }

    public int randomWordNumber() {
        Random rnd = new Random();
        return rnd.nextInt(countWords());
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
        allWords.moveToPosition(random);
        playWord.setText(allWords.getString(1));
        correct = allWords.getString(2);
    }

    public void checkNegative(int value) {
        if(value < 0) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("You Lost!");
            dlgAlert.setTitle("");
            dlgAlert.setPositiveButton("Back to main menu",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent main = new Intent(PlayActivity.this, MainActivity.class);
                            startActivity(main);
                        }
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
    }

    public void paintRed() {
        playWord.setTextColor(Color.RED);
    }

    public void paintGreen() {
        playWord.setTextColor(Color.GREEN);
    }

}