package ser402team.weallcode;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*
 *@author daniel tracy
 * Timer by Kristel
 */

public class QuestionActivity extends AppCompatActivity {

     private ArrayList<QuestionAnswer> questionList = new ArrayList<QuestionAnswer>();
     private boolean[] isUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //add timer text
        startTimer();


        //Return button functionality
        ImageButton returnButton = (ImageButton) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        //Return button functionality
        ImageButton chatButton = (ImageButton) findViewById(R.id.chatIconButton);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        //Generate question list
        generateQAList();
        //Create an isUsed list to determine which questions have been used
        isUsed = new boolean[questionList.size()];
        Arrays.fill(isUsed, Boolean.FALSE);

        //Next question functionality
        Button nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = 0;
                //Check to see if all the questions have been used or not
                if(isAllTrue(isUsed) == false) {
                    index = genRandInt();
                    while (isUsed[index] == true) {
                        index = genRandInt();
                    }
                }
                TextView questionText = (TextView)findViewById(R.id.questionTextField);
                if(isAllTrue(isUsed) == false) {
                    questionText.setText(questionList.get(index).getQuestion());
                } else {
                    questionText.setText("End of Questions");
                }
                isUsed[index] = true;
                //resets the timer for new question.
                startTimer();
            }
        });

    }

    //Here you should populate the QA list
    protected void generateQAList() {
        QuestionAnswer qA1 = new QuestionAnswer("1 kilobyte is equal to: ", "1024 bytes", "1000 bytes, duh", "1064 bytes");
        QuestionAnswer qA2 = new QuestionAnswer("What do you use to repeat segments of code?", "Loops", "Rings", "Repeaters");
        QuestionAnswer qA3 = new QuestionAnswer("What do strings represent?", "Character sequences", "Cotton", "Integers");
        QuestionAnswer qA4 = new QuestionAnswer("What does CPU stand for?", "Central Processing Unit", "Cat Plays Undertale", "Computer Processor Unit");
        QuestionAnswer qA5 = new QuestionAnswer("What are Window's and Mac OSX's software called?", "Operating Systems", "Computers", "Nouns");
        QuestionAnswer qA6 = new QuestionAnswer("Java is considered a what?", "Programming Language", "Delicious Coffee", "Operating System");
        QuestionAnswer qA7 = new QuestionAnswer("A GPU's purpose is to: ", "Process Graphics", "Go Fast", "Save your Data");

        addQuestionToList(qA1);
        addQuestionToList(qA2);
        addQuestionToList(qA3);
        addQuestionToList(qA4);
        addQuestionToList(qA5);
        addQuestionToList(qA6);
        addQuestionToList(qA7);
    }

    //Add the string to the list
    protected void addQuestionToList(QuestionAnswer qA) {questionList.add(qA);}

    //Use the size of the question list to return a random integer index
    protected int genRandInt() {
        Random rand = new Random();
        int num = rand.nextInt(questionList.size());
        return num;
    }

    //Check if the boolean array is all true or not
    protected boolean isAllTrue(boolean[] arr) {
        for(int i=0; i<arr.length; i++) {
            if(arr[i] == false) {
                return false;
            }
        }
        return true;
    }

    protected void startTimer() {
        final EditText timerTextField = (EditText) findViewById(R.id.timer);
        timerTextField.setBackgroundColor(Color.TRANSPARENT);
        timerTextField.setTypeface(Typeface.DEFAULT);

        new CountDownTimer(31000, 1000) {
            
            public void onTick(long millisUntilFinished) {
                timerTextField.setText(" Time remaining: " + millisUntilFinished / 1000 + " ");
                if((millisUntilFinished / 1000) == 5) {
                    timerTextField.setBackgroundColor(Color.RED);
                }else if ((millisUntilFinished / 1000) == 4){
                    timerTextField.setBackgroundColor(Color.TRANSPARENT);
                }else if ((millisUntilFinished / 1000) == 3){
                    timerTextField.setBackgroundColor(Color.RED);
                }else if ((millisUntilFinished / 1000) == 2) {
                    timerTextField.setBackgroundColor(Color.TRANSPARENT);
                }else if ((millisUntilFinished / 1000) <= 1){
                    timerTextField.setBackgroundColor(Color.RED);
                }
            }
            public void onFinish() {
                timerTextField.setText(" Times Up! ");
                timerTextField.setTypeface(Typeface.DEFAULT_BOLD);

            }
        }.start();
    }
}
