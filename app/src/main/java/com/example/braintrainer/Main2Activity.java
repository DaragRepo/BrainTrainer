package com.example.braintrainer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutput;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    private int firstRandomNumber = 5;

    private int secondRandomNumber = 2;

    private int result;

    private Button timer;

    private boolean timeIsOut = false;

    private int questionsCounter = 0;

    private int rightCounter = 0;

    private Button questionsButtonCounter;

    private  TextView answer;

    private CountDownTimer counter;

    private Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        timer = findViewById(R.id.timer);

        questionsButtonCounter = findViewById(R.id.counter);

        questionsButtonCounter.setText(0 + "/" + String.valueOf(questionsCounter));



       counter  = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf((int)millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                timeIsOut = true;

                playAgain = findViewById(R.id.playAgain);
                playAgain.setVisibility(View.VISIBLE);

                answer.setText("Done!");

            }
        }.start();

        if (timeIsOut == false) {
            startGame();
        } else {
            Toast.makeText(this, "Time is out", Toast.LENGTH_SHORT).show();
        }
    }


    public void startGame () {


        TextView TwoRandomNumbers = findViewById(R.id.randomNumbers);

        generateTwoRandomNumbers();

        TwoRandomNumbers.setText(String.valueOf(firstRandomNumber) + "+" + String.valueOf(secondRandomNumber) );

        result = getSum();

        final GridLayout gridLayout = (GridLayout) findViewById(R.id.grid);

        Random randomNumberGenerator = new Random();
        int correctAnswerIndex = randomNumberGenerator.nextInt(4);

        Button correctAnswerButton = (Button) gridLayout.getChildAt(correctAnswerIndex);

        correctAnswerButton.setText(String.valueOf(result));

        int wrongAnswer = 0;


        for (int i = 0 ; i < gridLayout.getChildCount(); i++) {

            Button wrongAnswersButtons = (Button) gridLayout.getChildAt(i);

            int wrongAnswerIncreasingAmount = randomNumberGenerator.nextInt(100);


            wrongAnswer = result + wrongAnswerIncreasingAmount;;

            if (wrongAnswersButtons.getText().toString().isEmpty()) {

                wrongAnswer += wrongAnswerIncreasingAmount;

                wrongAnswersButtons.setText(String.valueOf(wrongAnswer));
            }
        }
    }


    public void RightOrWrong(View view) {

        if (timeIsOut == false) {

            Button result = findViewById(R.id.grid).findViewWithTag(view.getTag());

            answer = findViewById(R.id.answer);

            String rightOrWrong = "";

            if (result.getText().toString().equals(String.valueOf(getSum()))) {
                rightCounter++;
                rightOrWrong += "You're Right";

            } else {
                rightOrWrong += "You're Wrong";
            }

            answer.setText(rightOrWrong);

            questionsCounter++;
            updateQuestionsAndAnswersCounter();
            startGame();
        } else {
            Toast.makeText(this, "Time is out", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateQuestionsAndAnswersCounter () {
        questionsButtonCounter.setText(String.valueOf(rightCounter) +  "/" + String.valueOf(questionsCounter));
    }


    private void generateTwoRandomNumbers() {

        Random rand = new Random();

        firstRandomNumber = rand.nextInt(100);

        secondRandomNumber = rand.nextInt(100);
    }

    private int getSum() {
        return  firstRandomNumber + secondRandomNumber;
    }


    public void playAgain(View view) {
        answer.setText("");
        rightCounter = 0;
        questionsCounter = 0;
        updateQuestionsAndAnswersCounter();
        timeIsOut = false;
        playAgain.setVisibility(View.INVISIBLE);
        startGame();
        counter.start();
    }
}
