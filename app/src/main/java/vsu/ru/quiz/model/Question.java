package vsu.ru.quiz.model;

import java.io.Serializable;

/**
 * Created by Valya on 20.03.2018.
 */

public class Question implements Serializable {
    private String text;
    private boolean answer;
    private boolean isCheated;
    // private int scrore;

    public String getText() {
        return text;
    }

    public Question setText(String text) {
        this.text = text;
        return this;
    }

    public boolean getAnswer() {
        return answer;
    }

    public Question setAnswer(boolean answer) {
        this.answer = answer;
        return this;
    }

    public boolean isCheated() {
        return isCheated;
    }

    public Question setCheated(boolean cheated) {
        isCheated = cheated;
        return this;
    }

}
