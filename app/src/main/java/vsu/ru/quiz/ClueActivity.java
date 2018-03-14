package vsu.ru.quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static vsu.ru.quiz.OuizActivity.EXTRA_ANSWER_IS_TRUE;

public class ClueActivity extends AppCompatActivity {
    private boolean isCorrect;
    private TextView clueTextView;
    static final String EXTRA_HAS_TAPPED = "vsu.ru.quiz.has_tapped";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue);

        isCorrect = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        Button clueButton = findViewById(R.id.clue_button);
        clueTextView=findViewById(R.id.clue_tv);
        clueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrect) clueTextView.setText(R.string.yes);
                else clueTextView.setText(R.string.no);
                setIntent();
            }
        });
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, ClueActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    private  void setIntent() {
        Intent i = new Intent();
        i.putExtra(EXTRA_HAS_TAPPED,true);
        setResult(RESULT_OK, i);
    }
}
