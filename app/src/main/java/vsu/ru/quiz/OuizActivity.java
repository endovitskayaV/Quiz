package vsu.ru.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OuizActivity extends AppCompatActivity {

    private final static String QUESTION_COUNTER_KEY = "questionCounter";

    private Button trueButton;
    private Button falseButton;

    private Button nextButton;
    private Button previousButton;

    private static List<String> questions;

    private TextView questionTv;

    private int questionCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouiz);

        if (savedInstanceState != null && savedInstanceState.containsKey(QUESTION_COUNTER_KEY))
            questionCounter = savedInstanceState.getInt(QUESTION_COUNTER_KEY, 0);

        System.out.println(questionCounter);
        questions = Arrays.asList(getResources().getStringArray((R.array.questions)));
        questionTv = findViewById(R.id.question_tv);
        questionTv.setText(questions.get(questionCounter));
        trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OuizActivity.this, R.string.true_button_answer, Toast.LENGTH_SHORT).show();
            }
        });


        falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OuizActivity.this, R.string.false_button_answer, Toast.LENGTH_SHORT).show();
            }
        });


        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (questionCounter+1 < questions.size()) {
                    questionCounter++;
                    questionTv.setText(questions.get(questionCounter));
                }
                else
                    Toast.makeText(OuizActivity.this, R.string.final_answer, Toast.LENGTH_LONG).show();
            }
        });


        previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (questionCounter-1 >= 0) {
                    questionCounter--;
                    questionTv.setText(questions.get(questionCounter));
                }
                else
                    Toast.makeText(OuizActivity.this, R.string.first_answer, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(QUESTION_COUNTER_KEY, questionCounter);
    }
}
