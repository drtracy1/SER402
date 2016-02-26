package ser402team.weallcode;

/**
 * Created by daniel on 2/26/2016.
 */
public class QuestionAnswer {
    private String question;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;

    public QuestionAnswer() {
        question = null;
        correctAnswer = null;
        wrongAnswer1 = null;
        wrongAnswer2 = null;
    }

    public QuestionAnswer(String question, String correctAnswer,
                          String wrongAnswer1, String wrongAnswer2) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }
}

