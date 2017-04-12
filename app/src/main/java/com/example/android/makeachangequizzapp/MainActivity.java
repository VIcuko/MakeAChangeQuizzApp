package com.example.android.makeachangequizzapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int currentQuestion = 0;
    String initialQuestionNumberText = "Welcome to the Next Change in your Life";

    String[] questionText = {"Ready to make a change?", "What do you want to change in your life?",
            "What skills do you need? (Introduce at least 2)", "How much time a week are you willing to dedicate?",
            "What days of the week?", "At what hour?", "How will you know when you're ready?",
            "By what date do you think you'll be ready if you dedicate this time?"};

    String[] questionAnswers;

    TextView questionNumber;
    TextView questionTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionNumber = (TextView) findViewById(R.id.question_number);
        questionTextView = (TextView) findViewById(R.id.question_text);
        displayQuestionNumberText();
        displayQuestion(questionText[0]);
    }


    /**
     * This method moves the user to the next question when clicking on button
     *
     * @param view
     */

    public void nextQuestion(View view) {
        if (currentQuestion >= questionTextView.length() - 1) {
            finishQuiz(questionAnswers);
            return;
        } else {
            currentQuestion += 1;
            displayQuestion(questionText[currentQuestion + 1]);
        }
    };

    /**
     * This method returns the user to the previous question when clicking on button
     *
     * @param view
     */

    public void previousQuestion(View view) {
        if (currentQuestion <= 1) {
            Toast.makeText(this, "Sorry, there aren't any previous questions", Toast.LENGTH_SHORT).show();
            return;
        }
        currentQuestion -= 1;
        displayQuestion(questionText[currentQuestion - 1]);
    };

    public void displayQuestionNumberText() {
        String questionNumberText;
        if (currentQuestion == 0) {
            questionNumberText = initialQuestionNumberText;
        } else {
            questionNumberText = "Question " + currentQuestion;
        }
        questionNumber.setText(questionNumberText);
    };


    public void displayQuestion(String question) {
        questionTextView.setText(question);
    }

    public void finishQuiz(String[] questionAnswers) {

    };
};