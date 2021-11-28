package com.example.assignment1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button mButton_A;
    private Button mButton_B;
    private Button mButton_C;
    private Button mButton_D;
    private Button mButton_E;

    private Button mNextButton;
    private Button mSubButton;
    private Button mPrevButton;

    private TextView mQuestionTextView;
    private TextView mStudentNameTextView;
    private TextView mStudentIdTextView;
    private TextView mOptionA;
    private TextView mOptionB;
    private TextView mOptionC;
    private TextView mOptionD;
    private TextView mOptionE;


    private ArrayList<Question> questions;
    //private ArrayList<String> answers;

    private String[] user_answers;
    private String email_address= "";

    private int questionIndex;

    //For rotations
    private final String State = "STATE";
    private final String State_key = "STATE_KEY";
    private final String TEXT_VIEW_KEY = "TEXT_KEY";
    private final String QUES_INDEX = "QUES_INDEX";
    private final String ANSWERS_KEY = "ANSWERS_KEY";
    private final String NAME_KEY = "NAME_KEY";
    private final String ID_KEY = "ID_KEY";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //set and inflate UI to manage

        mNextButton = (Button) findViewById(R.id.next_id);
        mSubButton = (Button) findViewById(R.id.submit_id);
        mPrevButton = (Button) findViewById(R.id.prev_id);

        mButton_A = (Button) findViewById(R.id.ButtonA_id);
        mButton_B = (Button) findViewById(R.id.ButtonB_id);
        mButton_C = (Button) findViewById(R.id.ButtonC_id);
        mButton_D = (Button) findViewById(R.id.ButtonD_id);
        mButton_E = (Button) findViewById(R.id.ButtonE_id);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_id);
        mOptionA = (TextView) findViewById(R.id.optionA_id);
        mOptionB = (TextView) findViewById(R.id.optionB_id);
        mOptionC = (TextView) findViewById(R.id.optionC_id);
        mOptionD = (TextView) findViewById(R.id.optionD_id);
        mOptionE = (TextView) findViewById(R.id.optionE_id);

        mStudentNameTextView = (TextView) findViewById(R.id.text_student_name);
        mStudentIdTextView = (TextView) findViewById(R.id.text_student_id);
        mQuestionTextView.setTextColor(Color.BLUE);

        questions = null;
        //answers = null;
        questionIndex = 0;
        //default text in textview id and textview name
        final String default_id = getResources().getString(R.string.text_id);
        final String default_name = getResources().getString(R.string.text_name);

        ArrayList<Question> parsedModel = null;
        ArrayList<String> parsedModel_two = null;
        //Parse exam xml file
        try {
            InputStream iStream = getResources().openRawResource(R.raw.comp2601exam);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
            parsedModel = Exam.pullParseFrom(bReader);
            email_address = Exam.getEmail_address();
            bReader.close();
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
        /*parse answers xml
		
        try {
            InputStream iStream = getResources().openRawResource(R.raw.answers);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
            parsedModel_two = Exam.pullAnswerParseFrom(bReader);
            bReader.close();
        }
        catch (java.io.IOException e){
            e.printStackTrace();

        }
		*/

        questions = parsedModel;

        /* used for debugging, check if the answer parser works
        answers = parsedModel_two;

        System.out.println("answers are : " + answers);
        */
        //this is the user_answers
        user_answers = new String[questions.size()];

        //set question text and options text
        if(questions != null && questions.size() > 0) {
            setText();
        }
/*
        mStudentNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onclick(View view) {
                if (mStudentNameTextView.getText().toString().equals(getResources().getString(R.string.text_name))){
                    mStudentNameTextView.setText(" ");
                }
            }
        });

 */
        //clear default text in student name field
        //so the student would not delete previous information in the textview
        mStudentNameTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mStudentNameTextView.getText().toString().equals(default_name)){
                    mStudentNameTextView.setText("");
                }
                return false;
            }
        });

        //clear default text in student id field
        //so the student would not delete previous information in the textview
        mStudentIdTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mStudentIdTextView.getText().toString().equals(default_id)){
                    mStudentIdTextView.setText("");
                }
                return false;
            }
        });


        //prev button, move to previous question
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mOptionA.setBackgroundColor(Color.WHITE);
                if(questionIndex > 0) questionIndex--;
                setColor(questionIndex);
                setText();
            }
        });

        //next button, move to next question
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(questionIndex < questions.size()-1) questionIndex++;
                setColor(questionIndex);
                setText();

            }

        });

        //color button A, clear colors on other buttons, update the user_answer.
        mButton_A.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clearColor();
                user_answers[questionIndex] = "A";
                mButton_A.setBackgroundColor(Color.RED);
            }

        });

        //color button B, clear colors on other buttons, update the user_answer.
        mButton_B.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clearColor();
                user_answers[questionIndex] = "B";
                mButton_B.setBackgroundColor(Color.RED);
            }

        });

        //color button C, clear colors on other buttons, update the user_answer.
        mButton_C.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clearColor();
                user_answers[questionIndex] = "C";
                mButton_C.setBackgroundColor(Color.RED);
            }

        });

        //color button D, clear colors on other buttons, update the user_answer.
        mButton_D.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clearColor();
                user_answers[questionIndex] = "D";
                mButton_D.setBackgroundColor(Color.RED);
            }

        });

        //color button E, clear colors on other buttons, update the user_answer.
        mButton_E.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clearColor();
                user_answers[questionIndex] = "E";
                mButton_E.setBackgroundColor(Color.RED);
            }

        });
        //submit button, submit answer when it is done;
        mSubButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //get the student name and student id
                String student_name = mStudentNameTextView.getText().toString();
                String student_id = mStudentIdTextView.getText().toString();
                Toast t;
                //check the student puts name & id or not
                if(student_name.equals("") || (student_name.equals(default_name))){
                    if(student_id.equals("") || (student_id.equals(default_id))){
                        t = Toast.makeText(MainActivity.this, "Please enter your name and id", Toast.LENGTH_SHORT);
                        t.show();
                        return;
                    }
                    t = Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                if(student_id.equals("") || (student_id.equals(default_id))){
                    t = Toast.makeText(MainActivity.this, "Please enter your id", Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                //check if the student has unselected question
                for (int i =0;i<questions.size();i++) {
                    if (user_answers[i]==null){
                        t = Toast.makeText(MainActivity.this, "You have unselected question, please double check", Toast.LENGTH_SHORT);
                        t.show();
                        return;
                    }
                }
                //create the xml for student's answers
                StringBuilder result = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n" + "<test>\n");
                final String student_open_tag = "    <student>\n";
                final String student_name_open_tag = "        <name>";
                final String student_name_close_tag = "</name>\n";
                final String student_id_open_tag = "        <id>";
                final String student_id_close_tag = "</id>\n";
                final String answer_open_tag = "        <answer id=\"question_";
                final String answer_close_tag = "</answer>\n";
                final String student_close_tag = "    </student>\n";
                final String test_close_tag = "</test>";
                result.append(student_open_tag).append(student_name_open_tag).append(student_name).append(student_name_close_tag).append(student_id_open_tag).append(student_id).append(student_id_close_tag);
                for (int i =0;i<questions.size();i++){
                    if (user_answers[i] != null)
                        result.append(answer_open_tag).append(i + 1).append("\">").append(user_answers[i]).append(answer_close_tag);
                    else
                        result.append(answer_open_tag).append(i + 1).append("\">").append(answer_close_tag);
                }
                result.append(student_close_tag).append(test_close_tag);
                //send the email
                String emailURI = "mailto:" + email_address;
                String emailSubject = "Submission of the test from student : " + student_name;
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(emailURI));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, result.toString());
                startActivity(Intent.createChooser(emailIntent, "Email Client ..."));
            }



        });

    }
    //set the question index, user_answers, student name and id
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mQuestionTextView.setText(savedInstanceState.getString(TEXT_VIEW_KEY));
        questionIndex = savedInstanceState.getInt(QUES_INDEX);
        user_answers = savedInstanceState.getStringArray(ANSWERS_KEY);
        setColor(questionIndex);
        mStudentNameTextView.setText(savedInstanceState.getString(NAME_KEY));
        mStudentIdTextView.setText(savedInstanceState.getString(ID_KEY));

    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    //save the question index, user_answers, student name and id
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(State, State_key);
        outState.putInt(QUES_INDEX,questionIndex);
        String test = mQuestionTextView.getText().toString();
        outState.putString(TEXT_VIEW_KEY,test);
        outState.putStringArray(ANSWERS_KEY,user_answers);
        outState.putString(NAME_KEY,mStudentNameTextView.getText().toString());
        outState.putString(ID_KEY,mStudentIdTextView.getText().toString());
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
    //helper function, get user's answer and color the correct button.
    public void setColor(int index){
        clearColor();
        if (user_answers[index]!=null){
            String tmp_case = user_answers[index];
            Log.i("COLOR","user's answer is saved as: " + tmp_case);
            switch(tmp_case){
                case "A":
                    mButton_A.setBackgroundColor(Color.RED);
                    break;
                case "B":
                    mButton_B.setBackgroundColor(Color.RED);
                    break;
                case "C":
                    mButton_C.setBackgroundColor(Color.RED);
                    break;
                case "D":
                    mButton_D.setBackgroundColor(Color.RED);
                    break;
                case "E":
                    mButton_E.setBackgroundColor(Color.RED);
                    break;
            }
        }
    }
    //helper function, reset all buttons to unselected.
    public void clearColor(){
        mButton_A.setBackgroundColor(Color.WHITE);
        mButton_B.setBackgroundColor(Color.WHITE);
        mButton_C.setBackgroundColor(Color.WHITE);
        mButton_D.setBackgroundColor(Color.WHITE);
        mButton_E.setBackgroundColor(Color.WHITE);
    }
    //helper function, set text for options and questions
    public void setText(){
        mQuestionTextView.setText("" + (questionIndex + 1) + ") " +
                questions.get(questionIndex).toString());
        //mQuestionTextView.setText(questions.get(questionIndex).toString());
        mOptionA.setText("A: " + questions.get(questionIndex).getmOptionA());
        mOptionB.setText("B: " + questions.get(questionIndex).getmOptionB());
        mOptionC.setText("C: " + questions.get(questionIndex).getmOptionC());
        mOptionD.setText("D: " + questions.get(questionIndex).getmOptionD());
        mOptionE.setText("E: " + questions.get(questionIndex).getmOptionE());
    }


}

