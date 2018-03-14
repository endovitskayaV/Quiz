package vsu.ru.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static vsu.ru.quiz.ClueActivity.EXTRA_HAS_TAPPED;


public class OuizActivity extends AppCompatActivity {

    private final static String QUESTION_COUNTER_KEY = "questionCounter";
    private final static String TAG = "OuizActivity";
    static final String EXTRA_IS_TRUE = "vsu.ru.quiz.answer_is_true";
    private final int SMTH=9;

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button previousButton;
    private Button clueButton;
    private static List<String> questions;
    private static List<int[]> answers;
    private TextView questionTv;
    private int questionCounter = 0;
    private Intent i;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==SMTH){
           i=data;
        }
      //  super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouiz);

        Log.i(TAG, "on create");

        if (savedInstanceState != null && savedInstanceState.containsKey(QUESTION_COUNTER_KEY))
            questionCounter = savedInstanceState.getInt(QUESTION_COUNTER_KEY, 0);

        System.out.println(questionCounter);
        questions = Arrays.asList(getResources().getStringArray(R.array.questions));
        answers = Arrays.asList(getResources().getIntArray(R.array.answers));
        questionTv = findViewById(R.id.question_tv);
        questionTv.setText(questions.get(questionCounter));
        trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i.getBooleanExtra(EXTRA_HAS_TAPPED, false))
                Toast.makeText(OuizActivity.this, R.string.tapped, Toast.LENGTH_SHORT).show();
               else {
                    if (answers.get(0)[questionCounter] == 1)
                        Toast.makeText(OuizActivity.this, R.string.true_button_answer, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(OuizActivity.this, R.string.false_button_answer, Toast.LENGTH_SHORT).show();
                }
            }
        });


        falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i.getBooleanExtra(EXTRA_HAS_TAPPED, false))
                    Toast.makeText(OuizActivity.this, R.string.tapped, Toast.LENGTH_SHORT).show();
                else {
                    if (answers.get(0)[questionCounter] == 0)
                        Toast.makeText(OuizActivity.this, R.string.true_button_answer, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(OuizActivity.this, R.string.false_button_answer, Toast.LENGTH_SHORT).show();
                }
            }
        });


        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionCounter + 1 < questions.size()) {
                    questionCounter++;
                    questionTv.setText(questions.get(questionCounter));
                } else
                    Toast.makeText(OuizActivity.this, R.string.final_answer, Toast.LENGTH_LONG).show();
            }
        });


        previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (questionCounter - 1 >= 0) {
                    questionCounter--;
                    questionTv.setText(questions.get(questionCounter));
                } else
                    Toast.makeText(OuizActivity.this, R.string.first_answer, Toast.LENGTH_LONG).show();
            }
        });

        clueButton = findViewById(R.id.show_clue_button);
        clueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ClueActivity.newIntent(OuizActivity.this, answers.get(0)[questionCounter]);
                intent.putExtra(EXTRA_IS_TRUE, answers.get(0)[questionCounter]);
                startActivityForResult(intent, SMTH);
               // startActivity(intent);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(QUESTION_COUNTER_KEY, questionCounter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "on start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "on  pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "on  stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "on  destroy");
    }


}
