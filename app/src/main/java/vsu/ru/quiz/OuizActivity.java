package vsu.ru.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vsu.ru.quiz.model.Question;

import static vsu.ru.quiz.ClueActivity.EXTRA_HAS_TAPPED;


public class OuizActivity extends AppCompatActivity {

    private final static String QUESTION_COUNTER_KEY = "questionCounter";
    private final static String QUESTION_LIST = "questionList";
    private int questionCounter = 0;
    private final int REQUEST_CODE = 972;
    static final String EXTRA_ANSWER_IS_TRUE = "vsu.ru.quiz.answer_is_true";
    private TextView questionTextView;
    private TextView answerTextView;
    private ArrayList<Question> questions;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE && intent != null)
            questions.get(questionCounter).setCheated(intent.getBooleanExtra(EXTRA_HAS_TAPPED, false));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouiz);

        List<String> texts = Arrays.asList(getResources().getStringArray(R.array.questions));

        boolean[] answers = new boolean[]{true, false, false, false, true};
        questions = new ArrayList<>();

        for (int i = 0; i < texts.size(); i++) {
            questions.add(new Question()
                    .setText(texts.get(i))
                    .setAnswer(answers[i])
                    .setCheated(false));
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(QUESTION_COUNTER_KEY)) {
            questionCounter = savedInstanceState.getInt(QUESTION_COUNTER_KEY, 0);
            questions = (ArrayList<Question>) savedInstanceState.getSerializable(QUESTION_LIST);
        }

        questionTextView = findViewById(R.id.question_tv);
        questionTextView.setText(questions.get(questionCounter).getText());

        answerTextView = findViewById(R.id.answer_tv);

        final Button trueButton = findViewById(R.id.true_button);
        final Button falseButton = findViewById(R.id.false_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
                if (questions.get(questionCounter).isCheated())
                    Toast.makeText(OuizActivity.this, R.string.cheated, Toast.LENGTH_SHORT).show();
                if (questions.get(questionCounter).getAnswer()) setAnswer(true);
                else setAnswer(false);
            }
        });


        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
                if (questions.get(questionCounter).isCheated())
                    Toast.makeText(OuizActivity.this, R.string.cheated, Toast.LENGTH_SHORT).show();
                if (!questions.get(questionCounter).getAnswer()) setAnswer(true);
                else setAnswer(false);
            }
        });


        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerTextView.setVisibility(View.INVISIBLE);
                trueButton.setEnabled(true);
                falseButton.setEnabled(true);

                if (questionCounter + 1 < questions.size()) questionCounter++;
                else questionCounter = 0;
                questionTextView.setText(questions.get(questionCounter).getText());
            }
        });


        Button previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerTextView.setVisibility(View.INVISIBLE);
                trueButton.setEnabled(true);
                falseButton.setEnabled(true);

                if (questionCounter - 1 >= 0) questionCounter--;
                else questionCounter = questions.size() - 1;
                questionTextView.setText(questions.get(questionCounter).getText());
            }
        });

        Button clueButton = findViewById(R.id.show_clue_activity_button);
        clueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ClueActivity.newIntent(
                        OuizActivity.this, questions.get(questionCounter).getAnswer());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(QUESTION_COUNTER_KEY, questionCounter);
        outState.putSerializable(QUESTION_LIST, questions);
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

