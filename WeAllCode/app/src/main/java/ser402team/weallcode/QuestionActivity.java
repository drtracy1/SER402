package ser402team.weallcode;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/*
 *@author daniel tracy
 */

public class QuestionActivity extends AppCompatActivity {

     private ArrayList<String> questionList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Here, you should populate the question list
        addQuestionToList("Sample Question 1");
        addQuestionToList("Sample Question 2");
        addQuestionToList("Sample Question 3");

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

        //Next question functionality
        Button nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = genRandInt();
                TextView questionText = (TextView)findViewById(R.id.questionTextField);
                questionText.setText(questionList.get(index));
            }
        });

    }

    //Add the string to the list
    protected void addQuestionToList(String question) {
        questionList.add(question);
    }

    //Use the size of the question list to return a random integer index
    protected int genRandInt() {
        Random rand = new Random();
        int num = rand.nextInt(questionList.size());
        return num;
    }

    protected void startTimer() {
        final EditText timerTextField = (EditText) findViewById(R.id.timer);

        new CountDownTimer(31000, 1000) {
            
            public void onTick(long millisUntilFinished) {
                timerTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                if((millisUntilFinished / 1000) < 5) {
                    timerTextField.setBackgroundColor(Color.RED);
                }
            }

            public void onFinish() {
                timerTextField.setText("done!");
            }
        }.start();
    }
}
