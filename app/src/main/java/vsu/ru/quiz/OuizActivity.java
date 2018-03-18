package vsu.ru.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import vsu.ru.quiz.model.Model;

import static vsu.ru.quiz.ClueActivity.EXTRA_HAS_TAPPED;


public class OuizActivity extends AppCompatActivity {

    private final static String QUESTION_COUNTER_KEY = "questionCounter";
    private final static String HAS_TAPPED_KEY = "hasTapped";
    private int questionCounter = 0;
    private boolean hasTapped;
    private final int QUIZ_ACTIVITY_ID = 972;
    static final String EXTRA_ANSWER_IS_TRUE = "vsu.ru.quiz.answer_is_true";
    private Model model;
    private List<String> questions;
    private TextView questionTextView;
    private TextView answerTextView;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUIZ_ACTIVITY_ID && data != null)
            hasTapped = data.getBooleanExtra(EXTRA_HAS_TAPPED, false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouiz);

        if (savedInstanceState != null && savedInstanceState.containsKey(QUESTION_COUNTER_KEY)) {
            questionCounter = savedInstanceState.getInt(QUESTION_COUNTER_KEY, 0);
            hasTapped = savedInstanceState.getBoolean(HAS_TAPPED_KEY, false);
        }
        model = new Model();
        questions = Arrays.asList(getResources().getStringArray(R.array.questions));

        questionTextView = findViewById(R.id.question_tv);
        questionTextView.setText(questions.get(questionCounter));

        answerTextView = findViewById(R.id.answer_tv);

        final Button trueButton = findViewById(R.id.true_button);
        final Button falseButton = findViewById(R.id.false_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
                if (hasTapped)
                    Toast.makeText(OuizActivity.this, R.string.cheated, Toast.LENGTH_SHORT).show();
                    if (model.getAnswers()[questionCounter]) setAnswer(true);
                    else setAnswer(false);
            }
        });



        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
                if (hasTapped)
                    Toast.makeText(OuizActivity.this, R.string.cheated, Toast.LENGTH_SHORT).show();
                    if (!model.getAnswers()[questionCounter]) setAnswer(true);
                    else setAnswer(false);
            }
        });


        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasTapped = false;
                answerTextView.setVisibility(View.INVISIBLE);
                trueButton.setEnabled(true);
                falseButton.setEnabled(true);

                if (questionCounter + 1 < questions.size()) questionCounter++;
                else questionCounter = 0;
                questionTextView.setText(questions.get(questionCounter));
            }
        });


        Button previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasTapped = false;
                answerTextView.setVisibility(View.INVISIBLE);
                trueButton.setEnabled(true);
                falseButton.setEnabled(true);

                if (questionCounter - 1 >= 0) questionCounter--;
                else questionCounter = questions.size() - 1;
                questionTextView.setText(questions.get(questionCounter));
            }
        });

        Button clueButton = findViewById(R.id.show_clue_activity_button);
        clueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ClueActivity.newIntent(OuizActivity.this, model.getAnswers()[questionCounter]);
                startActivityForResult(intent, QUIZ_ACTIVITY_ID);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(QUESTION_COUNTER_KEY, questionCounter);
        outState.putBoolean(HAS_TAPPED_KEY, hasTapped);
    }

    private void setAnswer(boolean isCorrect) {
        if (isCorrect) {
            answerTextView.setVisibility(View.VISIBLE);
            answerTextView.setText(R.string.correct);
            answerTextView.setTextColor(Color.GREEN);
        } else {
            answerTextView.setVisibility(View.VISIBLE);
            answerTextView.setText(R.string.incorrect);
            answerTextView.setTextColor(Color.RED);
        }
    }
}

