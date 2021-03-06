package com.milapnaik.tradermathtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.view.KeyEvent;

/**
 * Created by MilapNaik on 5/2/16.
 */
public class CountDown extends AppCompatActivity{
    public final static String DIFFICULTY = "com.milapnaik.mentalmathworkout.MESSAGE";
    CountDownTimer countDownTimer;
    TextView timer, test, questions;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    String Difficulty;
    int Questions;

    StringBuilder testdifficultydisplay = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cd);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        final String testorpractice = extras.getString("TESTORPRACTICE");
        final String mathorseq = extras.getString("MATHORSEQ");

        // Find what difficulty from the preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Difficulty = sharedpreferences.getString("PREF_DIFFICULTY", "Easy");
        Questions = sharedpreferences.getInt("NUM_QUESTIONS", 5);


        // Display difficulty, practice/test, # of questions, and countdown
        timer = (TextView) findViewById(R.id.timer);
        test = (TextView) findViewById(R.id.test);
        questions = (TextView) findViewById(R.id.questions);

        timer.setTextSize(100);
        timer.setTextColor(Color.rgb(25, 4, 4));

        test.setTextSize(32);
        test.setTextColor(Color.rgb(25, 4, 4));

        questions.setTextSize(28);
        questions.setTextColor(Color.rgb(25, 4, 4));


        if (mathorseq.equals("Seqtest")) {
            if(testorpractice.equals("Test")) {
                test.setText("Sequence Test");
                testdifficultydisplay.append("50 ");
                testdifficultydisplay.append(Difficulty);
                testdifficultydisplay.append(" Questions");
                questions.setText(testdifficultydisplay);
            }
            else {
                test.setText("Sequence Practice");
                testdifficultydisplay.append(Questions);
                testdifficultydisplay.append(" ");
                testdifficultydisplay.append(Difficulty);
                testdifficultydisplay.append(" Questions");
                questions.setText(testdifficultydisplay);
            }
        }
        else{
            if(testorpractice.equals("Test")) {
                test.setText("Math Test");
                testdifficultydisplay.append("80 ");
                testdifficultydisplay.append(Difficulty);
                testdifficultydisplay.append(" Questions");
                questions.setText(testdifficultydisplay);

            }
            else {
                test.setText("Math Practice");
                testdifficultydisplay.append(Questions);
                testdifficultydisplay.append(" ");
                testdifficultydisplay.append(Difficulty);
                testdifficultydisplay.append(" Questions");
                questions.setText(testdifficultydisplay);
            }

        }



        //Count down timer 3, 2, 1
        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf((int) millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                switch (mathorseq) {
                    case "Seqtest":
                        if (testorpractice.equals("Test")) {
                            Intent intent = new Intent(CountDown.this, SeqTest.class);
                            startActivity(intent); //Start Sequence test
                            finish();
                        } else {
                            Intent intent = new Intent(CountDown.this, SeqPractice.class);
                            startActivity(intent); //Start Sequence practice
                            finish();
                        }

                        break;
                    case "Mathtest":
                        if (testorpractice.equals("Test")) {
                            Intent intent = new Intent(CountDown.this, MathTest.class);
                            startActivity(intent); //Start Math test
                            finish();
                        } else {
                            Intent intent = new Intent(CountDown.this, MathPractice.class);
                            startActivity(intent); //Start Math practice
                            finish();

                        }
                        break;
                    default:
                        Intent intent = new Intent(CountDown.this, MainActivity.class);
                        startActivity(intent); //Go back to main activity

                        break;
                }
                //timer.setText("GO");
            }
        }.start();


    }


    // Kill activity if countdown starts but back button is pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            countDownTimer.cancel();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
