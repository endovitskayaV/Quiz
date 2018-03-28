package vsu.ru.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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


public class QuizActivity extends AppCompatActivity {

    private final static String QUESTION_COUNTER_KEY = "questionCounter";
    private final static String QUESTION_LIST = "questionList";
    private final static String ANSWER_TEXT_VIEW = "answerTextView";
    private int questionCounter = 0;
    private final int REQUEST_CODE = 972;
    static final String EXTRA_ANSWER_IS_TRUE = "vsu.ru.quiz.answer_is_true";
    private TextView questionTextView;
    private TextView answerTextView;
    private ArrayList<Question> questions;
    private Button trueButton;
    private Button falseButton;
    private Boolean isUserAnswerCorrect;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE && intent != null)
            questions.get(questionCounter).setCheated(intent.getBooleanExtra(EXTRA_HAS_TAPPED, false));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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
            isUserAnswerCorrect = (Boolean) savedInstanceState.getSerializable(ANSWER_TEXT_VIEW);
        }

        answerTextView = findViewById(R.id.answer_tv);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        questionTextView = findViewById(R.id.question_tv);
        questionTextView.setText(questions.get(questionCounter).getText());

        if (isUserAnswerCorrect != null) {
            setAnswer(isUserAnswerCorrect);
            setAnswerButtonsAbility(false);
        }



        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswerButtonsAbility(false);
                falseButton.setEnabled(false);
                if (questions.get(questionCounter).isCheated())
                    Toast.makeText(QuizActivity.this, R.string.cheated, Toast.LENGTH_SHORT).show();
                if (questions.get(questionCounter).getAnswer()) {
                    isUserAnswerCorrect = true;
                    setAnswer(isUserAnswerCorrect);
                } else {
                    isUserAnswerCorrect = false;
                    setAnswer(isUserAnswerCorrect);
                }
            }
        });


        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswerButtonsAbility(false);
                if (questions.get(questionCounter).isCheated())
                    Toast.makeText(QuizActivity.this, R.string.cheated, Toast.LENGTH_SHORT).show();
                if (!questions.get(questionCounter).getAnswer()) {
                    isUserAnswerCorrect = true;
                    setAnswer(isUserAnswerCorrect);
                } else {
                    isUserAnswerCorrect = false;
                    setAnswer(isUserAnswerCorrect);
                }
            }
        });


        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });


        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();

            }
        });

        Button previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetView();

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
                        QuizActivity.this, questions.get(questionCounter).getAnswer());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void resetView() {
        answerTextView.setVisibility(View.INVISIBLE);
        setAnswerButtonsAbility(true);
        isUserAnswerCorrect = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(QUESTION_COUNTER_KEY, questionCounter);
        outState.putSerializable(QUESTION_LIST, questions);
        outState.putSerializable(ANSWER_TEXT_VIEW, isUserAnswerCorrect);
    }

    private void setAnswer(boolean isCorrect) {
        if (isCorrect) {
            answerTextView.setVisibility(View.VISIBLE);
            answerTextView.setText(R.string.correct);
            answerTextView.setTextColor(ContextCompat.getColor(this, R.color.green));
        } else {
            answerTextView.setVisibility(View.VISIBLE);
            answerTextView.setText(R.string.incorrect);
            answerTextView.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
    }

    private void setAnswerButtonsAbility(boolean enabled) {
        trueButton.setEnabled(enabled);
        falseButton.setEnabled(enabled);
    }


    private void goNext() {
        resetView();

        if (questionCounter + 1 < questions.size()) questionCounter++;
        else questionCounter = 0;
        questionTextView.setText(questions.get(questionCounter).getText());
    }
}

