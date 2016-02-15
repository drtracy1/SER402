package ser402team.weallcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

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

        //Return button functionality
        ImageButton returnButton = (ImageButton) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity.this, MainPageActivity.class);
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
}
