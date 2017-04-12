package com.example.android.makeachangequizzapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int currentQuestion = 0;
    int previousQuestion = 0;

    String initialQuestionNumberText = "Welcome to the Next Change in your Life";

    String[] questionText = {"Ready to make a change?\\n(Press \"Start\" to begin)", "What do you want to change in your life?",
            "What skills do you need?", "How much time a week are you willing to dedicate?",
            "What days of the week?", "At what hour?", "How will you know when you're ready?",
            "By what date do you think you'll be ready if you dedicate this time?", "Click on \"Add to Calendar\" to add a reminder" +
            "to achieve your objective!"};


    String[] questionAnswers;

    TextView questionNumberView;
    TextView questionTextView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionNumberView = (TextView) findViewById(R.id.question_number);
        questionTextView = (TextView) findViewById(R.id.question_text);
    }


    /**
     * This method moves the user to the next question when clicking on button
     *
     * @param view
     */

    public void nextQuestion(View view) {
        if (currentQuestion >= questionText.length - 1) {
            Toast.makeText(this, "Sorry, there are no more questions available", Toast.LENGTH_SHORT).show();
            return;
        }
        currentQuestion += 1;
        updateScreen();
        previousQuestion += 1;
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
        updateScreen();
        previousQuestion -= 1;
    };

    public void updateScreen(){
        //Get the next question number to be displayed and display it
        String nextQuestionNumberText = getQuestionNumberText();
        displayText (questionNumberView, nextQuestionNumberText);

        //Get the next question to be displayed and display it
        String nextQuestionText = getQuestion();
        displayText(questionTextView, nextQuestionText);

        //Get the content of the previous question and hide it
        if (previousQuestion != 0){
        String previousAnswerContent = getAnswerContent(previousQuestion);
        int previousAnswerId = getResources().getIdentifier(previousAnswerContent, "id", this.getPackageName());
        hideContent (findViewById(previousAnswerId));
        }

        //Get the content to be shown to the user to answer the question and display it
        String nextAnswerContent = getAnswerContent(currentQuestion);
        int answerId = getResources().getIdentifier(nextAnswerContent, "id", this.getPackageName());
        displayContent (findViewById(answerId));

        //Get which buttons should be displayed at the bottom of the screen see if they are the same ones as before
        // and display new ones if necessary
        String nextButtonContent = getButtons(currentQuestion);
        String previousButtonContent = getButtons(previousQuestion);
        if (nextButtonContent != previousButtonContent){
            int previousButtonId = getResources().getIdentifier(previousButtonContent, "id", this.getPackageName());
            hideContent (findViewById(previousButtonId));
            int buttonId = getResources().getIdentifier(nextButtonContent, "id", this.getPackageName());
            displayContent (findViewById(buttonId));
        }
    }

    public String getQuestionNumberText() {
        String questionNumberText= initialQuestionNumberText;

        if (currentQuestion == questionText.length-1){
            questionNumberText = "Congratulations!";
        }
        else if (currentQuestion != 0) {
            questionNumberText = "Question " + currentQuestion;
        }
        return questionNumberText;
    };

    public String getQuestion(){
        return questionText[currentQuestion];
    };

    public void displayText(TextView textview, String text) {
        textview.setText(text);
    }

    public String getAnswerContent(int question){
        String answerId = "answer_"+question;
        return answerId;
    }

    public void displayContent(View answerContent){
        answerContent.setVisibility(View.VISIBLE);
        };

    public void hideContent(View answerContent){
        answerContent.setVisibility(View.GONE);
    };

    public String getButtons(int question){
        String buttonId;
        if (question == 0){
            buttonId = "buttons_section_start";
        }
        else if(question >= questionText.length){
            buttonId = "buttons_section_add_to_calendar";
        }
        else{
            buttonId = "buttons_section_process";
        }
        return buttonId;
    };

    public void finishQuiz(String[] questionAnswers) {

    };
};