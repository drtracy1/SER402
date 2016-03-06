package ser402team.weallcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by daniel on 2/26/2016.
 */
public class QuestionAnswer {
    private String question;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private ArrayList<String> answerList;
    private boolean isUsed[];

    public QuestionAnswer() {
        question = null;
        correctAnswer = null;
        wrongAnswer1 = null;
        wrongAnswer2 = null;
        answerList = null;
    }

    public QuestionAnswer(String question, String correctAnswer,
                          String wrongAnswer1, String wrongAnswer2) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;

        answerList = new ArrayList<String>();
        answerList.add(this.correctAnswer);
        answerList.add(this.wrongAnswer1);
        answerList.add(this.wrongAnswer2);

        isUsed = new boolean[3];
        Arrays.fill(isUsed, Boolean.FALSE);
    }

    //Checks to see if all values in the boolean array are true or false
    public boolean isAllTrue(boolean[] arr) {
        for(int i=0; i<arr.length; i++) {
            if(arr[i] == false) {
                return false;
            }
        }
        return true;
    }
    //Returns an unique answer from the list
    public String getAnswerFromList() {
        int num = genRandInt(3);
        while(true) {
            if (isUsed[num] == false) {
                isUsed[num] = true;
                return answerList.get(num);
            }
            num = genRandInt(3);
        }
    }

    //Generates a random integer between 0 and max
    protected int genRandInt(int max) {
        Random rand = new Random();
        int num = rand.nextInt(max);
        return num;
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

