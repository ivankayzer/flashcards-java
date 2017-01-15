package com.example.ivankayzer.flashcards;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    String wordPair;
    int playScoreValue;
    private Handler timer;
    ProgressBar loader;

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
        timer = new Handler();
        database = new Database(this);
        loader = (ProgressBar) findViewById(R.id.loader);

        SQLiteDatabase db = database.getReadableDatabase();
        allWords = db.rawQuery("select * from words", null);

        init();

        playSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playScoreValue = Integer.parseInt(playScore.getText().toString());
                if(checkWord(playTranslation.getText().toString())) {
                    playScoreValue++;
                    showWordPair(Color.GREEN);
                } else {
                    playScoreValue--;
                    showWordPair(Color.RED);
                }

                checkNegative(playScoreValue);
                hideButtons();
                loader.setVisibility(View.VISIBLE);

                timer.postDelayed(new Runnable() {
                    public void run() {
                        loader.setVisibility(View.INVISIBLE);
                        resetFields();
                        init();
                        showButtons();
                    }
                }, 2500);

            }
        });

        playSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int playScoreValue = Integer.parseInt(playScore.getText().toString());
                playScoreValue--;
                checkNegative(playScoreValue);
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
        playWord.setTextColor(Color.WHITE);
        playWord.setText("");
        playTranslation.setText("");
    }

    public void init() {
        if (countWords() > 0) {
            int random = randomWordNumber();
            allWords.moveToPosition(random);
            playWord.setText(allWords.getString(1));
            correct = allWords.getString(2);
            wordPair = allWords.getString(1) + " - " + correct;
        } else {
            AlertDialog.Builder alert = dialog("Add words to list first", "Back to main menu");
            alert.create().show();
        }
    }

    public void checkNegative(int value) {
        if(value < 0) {
            AlertDialog.Builder alert = dialog("You lose!", "Back to main menu");
            alert.create().show();
        } else {
            checkWin();
            playScore.setText(Integer.toString(playScoreValue));
        }
    }

    public void showWordPair(int color) {
        playWord.setTextColor(color);
        playWord.setText(wordPair);
    }

    public void hideButtons() {
        playSubmit.setVisibility(View.INVISIBLE);
        playSkip.setVisibility(View.INVISIBLE);
    }

    public void showButtons() {
        playSubmit.setVisibility(View.VISIBLE);
        playSkip.setVisibility(View.VISIBLE);
    }

    public void checkWin() {
        if (playScoreValue > countWords()) {
            AlertDialog.Builder alert = dialog("You win!", "Back to main menu");
            alert.create().show();
        }
    }

    AlertDialog.Builder dialog(String message, String buttonText) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setTitle("");
        alert.setPositiveButton(buttonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent main = new Intent(PlayActivity.this, MainActivity.class);
                        startActivity(main);
                    }
                });
        alert.setCancelable(false);
        return alert;
    }

}