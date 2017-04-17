package com.example.android.makeachangequizzapp;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private int currentQuestion = 0;
    private int previousQuestion = 0;

    private String initialQuestionNumberText = "Welcome to the Next Change in your Life";

    private String[] questionText = {"Ready to make a change?\\n(Press \"Start\" to begin)", "What do you want to change in your life?",
            "What skills do you need?", "How much time a week are you willing to dedicate?",
            "What days of the week?", "At what hour?", "How will you know when you're ready?",
            "By what date do you think you'll be ready if you dedicate this time?", "Click on \"Add to Calendar\" to add a reminder " +
            "to achieve your objective!"};

    private TextView questionNumberView;
    private TextView questionTextView;


    public void onCreate(Bundle savedInstanceState) {
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

    /**
     * This method calls other methods to update the screen step by step
     */
    private void updateScreen(){
        //Get the next question number to be displayed and display it
        String nextQuestionNumberText = getQuestionNumberText();
        displayText (questionNumberView, nextQuestionNumberText);

        //Get the next question to be displayed and display it
        String nextQuestionText = getQuestion();
        displayText(questionTextView, nextQuestionText);

        //Get the content of the previous question and hide it
        if (previousQuestion != 0 && previousQuestion != questionText.length-1){
            String previousAnswerContent = getAnswerContent(previousQuestion);
            int previousAnswerId = getResources().getIdentifier(previousAnswerContent, "id", this.getPackageName());
            hideContent (findViewById(previousAnswerId));
        };

        //Get the content to be shown to the user to answer the question and display it
        if (currentQuestion != questionText.length-1) {
            String nextAnswerContent = getAnswerContent(currentQuestion);
            int answerId = getResources().getIdentifier(nextAnswerContent, "id", this.getPackageName());
            displayContent(findViewById(answerId));
        };

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

    /**
     * This method gets the text to be displayed in the field with id question_number
     * @return the text to be displayed
     */
    private String getQuestionNumberText() {
        String questionNumberText= initialQuestionNumberText;

        if (currentQuestion == questionText.length-1){
            questionNumberText = "Congratulations!";
        }
        else if (currentQuestion != 0) {
            questionNumberText = "Question " + currentQuestion;
        }
        return questionNumberText;
    };

    /**
     * This method gets the text to be displayed in the field with id question_text
     * @return the text to be displayed
     */
    private String getQuestion(){
        return questionText[currentQuestion];
    };

    /**
     * This method displays the text received in the indicated TextView
     * @param textview to display the text
     * @param text text to be displayed in the TextView
     */
    private void displayText(TextView textview, String text) {
        textview.setText(text);
    }

    /**
     * This method retrieves the id to be used of the indicated answer possible content displayed
     * @param question the number of the indicated question
     * @return returns the id string for the indicated answer field being displayed
     */
    private String getAnswerContent(int question){
        String answerId = "answer_"+question;
        return answerId;
    }

    /**
     * Makes an element of the layout become visible for the user
     * @param answerContent is the indicated view to become visible
     */
    private void displayContent(View answerContent){
        answerContent.setVisibility(View.VISIBLE);
        };

    /**
     * Makes an element of the layout become gone for the user
     * @param answerContent is the indicated view to become gone
     */
    private void hideContent(View answerContent){
        answerContent.setVisibility(View.GONE);
    };

    /**
     * This method retrieves the id for the buttons to be used on the displayed screen
     * @param question indicates the number of the question that is going to be displayed
     * @return returns the id of the button to be displayed
     */
    private String getButtons(int question){
        String buttonId;
        if (question == 0){
            buttonId = "buttons_section_start";
        }
        else if(question >= questionText.length-1){
            buttonId = "buttons_section_add_to_calendar";
        }
        else{
            buttonId = "buttons_section_process";
        }
        return buttonId;
    };

    /**
     * This method calls a method to retrieve all the user's answers and then calls the method to create a calendar intent
     * @param view
     */
    public void addToCalendar(View view) {
        ArrayList userAnswers = getUserAnswers();
        addEventToCalendar(userAnswers);
    };

    /**
     * This method retrieves all of the user answers into an ArrayList
     * @return an ArrayList with all of the user's answers
     */

    private ArrayList getUserAnswers (){
        ArrayList userAnswers = new ArrayList();

        String answer1 = ((EditText) findViewById(R.id.answer_1_text)).getText().toString();
        userAnswers.add(answer1);

        String answer2 = ((EditText) findViewById(R.id.answer_2_text)).getText().toString();
        userAnswers.add(answer2);

        int selectedRadioButton = ((RadioGroup) findViewById(R.id.answer_3)).getCheckedRadioButtonId();
        String answer3 = ((RadioButton) findViewById(selectedRadioButton)).getText().toString();
        userAnswers.add(answer3.split("")[0]);

        int answer4 = 0;
        String weekdayContent;
        for (int i=1;i<=7;i++){
            weekdayContent = "weekday_"+i;
            int weekdayId = getResources().getIdentifier(weekdayContent, "id", this.getPackageName());
            if (((CheckBox) findViewById(weekdayId)).isChecked()){
                answer4+=1;
            }
        }
        userAnswers.add(answer4);

        ArrayList answer5 = new ArrayList();
        int answer5_hours = ((TimePicker) findViewById(R.id.answer_5)).getHour();
        answer5.add(answer5_hours);

        int answer5_minutes = ((TimePicker) findViewById(R.id.answer_5)).getMinute();
        answer5.add(answer5_minutes);
        SimpleDateFormat answer5b;

        userAnswers.add(answer5);

        String answer6 = ((EditText) findViewById(R.id.answer_6)).getText().toString();
        userAnswers.add(answer6);

        int answer7_day = ((DatePicker) findViewById(R.id.answer_7)).getDayOfMonth();

        int answer7_month = ((DatePicker) findViewById(R.id.answer_7)).getMonth();

        int answer7_year = ((DatePicker) findViewById(R.id.answer_7)).getYear();

        String answer7 = answer7_day+"-"+answer7_month+"-"+answer7_year;

        userAnswers.add(answer7);

        return userAnswers;
    };

    /**
     * This method creates an Intent with the user's answers to open up in the calendar
     * @param userAnswers ArrayList with user's answers
     */
    private void addEventToCalendar(ArrayList userAnswers){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.RRULE,"FREQ=DAILY;INTERVAL="+(7-Integer.parseInt(userAnswers.get(3).toString()))+";UNTIL="+userAnswers.get(6).toString())
                .putExtra(CalendarContract.Events.TITLE, userAnswers.get(0).toString())
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, formattedDate)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, userAnswers.get(6).toString())
                .putExtra(CalendarContract.Events.HAS_ALARM,true)
                .putExtra(CalendarContract.Events.DURATION,userAnswers.get(2).toString())
                .putExtra(CalendarContract.Events.DESCRIPTION,userAnswers.get(1).toString()+"."+userAnswers.get(5).toString());


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    };
};