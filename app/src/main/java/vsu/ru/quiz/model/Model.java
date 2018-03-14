package vsu.ru.quiz.model;

/**
 * Created by Valya on 14.03.2018.
 */

public class Model {
    private boolean[] answers;

    public Model(){
        setAnswers();
    }
    public boolean[] getAnswers() {
        return answers;
    }

    private void setAnswers() {
        answers=new boolean[]{true,false, false,false, true};
    }
}
