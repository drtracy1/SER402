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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*
 *@author daniel tracy
 * Timer and retrieve DB data methods by Kristel
 * Timer updated by Crystal
 */

public class QuestionActivity extends AppCompatActivity {

    private ArrayList<QuestionAnswer> questionList = new ArrayList<QuestionAnswer>();
    private boolean[] isUsed;
    private QuestionAnswer current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //connect to firebase
        Firebase.setAndroidContext(this);
        final Firebase REF = new Firebase("https://weallcode-questions.firebaseio.com/");

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
        generateQAList(REF);

        final TextView answerButton1 = (TextView) findViewById(R.id.answer1);
        final TextView answerButton2 = (TextView) findViewById(R.id.answer2);
        final TextView answerButton3 = (TextView) findViewById(R.id.answer3);

        //Create an isUsed list to determine which questions have been used
        isUsed = new boolean[questionList.size()];
        Arrays.fill(isUsed, Boolean.FALSE);

        //Next question functionality
        Button nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Clear the correct/incorrect imageview
                ImageView img = (ImageView) findViewById(R.id.correct);
                img.setImageResource(0);

                int index = 0;

                /* Firebase has been a bit of a pain, for some reason android would not complete
                 * generateQAList method before it sets up the isUsed, so it was always empty  */
                if (isUsed.length == 0) {
                    //Create an isUsed list to determine which questions have been used
                    isUsed = new boolean[questionList.size()];
                    Arrays.fill(isUsed, Boolean.FALSE);
                }

                //Check to see if all the questions have been used or not
                if (isAllTrue(isUsed) == false) {    //if not all the questions have been touched
                    index = genRandInt();           //get random element number
                    while (isUsed[index] == true) { //if that random number is already used
                        index = genRandInt();       //get another
                    }
                }

                TextView questionText = (TextView) findViewById(R.id.questionTextField);

                if (isAllTrue(isUsed) == false) {     //if not all questions have been used
                    questionText.setText(questionList.get(index).getQuestion()); //get index to question from last if block
                } else {                            //all questions have been used
                    questionText.setText("End of Questions");
                }
                isUsed[index] = true;

                //resets the timer for new question.
                startTimer();
                current = questionList.get(index); //Get the current QuestionAnswer in the list

                //Initialize unique answers for every questions
                answerButton1.setText(current.getAnswerFromList());
                answerButton2.setText(current.getAnswerFromList());
                answerButton3.setText(current.getAnswerFromList());
            }
        });

        //The first answer button in the UI
        answerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Used stringbuilder to convert textview into a string
                final StringBuilder sb = new StringBuilder(answerButton1.getText().length());
                sb.append(answerButton1.getText());
                String answer1 = sb.toString();

                //If textview is equal to the correct answer, change the imageview
                if(answer1.equals(current.getCorrectAnswer())) {
                    ImageView img = (ImageView) findViewById(R.id.correct);
                    img.setImageResource(R.drawable.correct);
                } else {
                    ImageView img = (ImageView) findViewById(R.id.correct);
                    img.setImageResource(R.drawable.incorrect);
                }
            }
        });

        //The second answer button in the UI
        answerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Used stringbuilder to convert textview into a string
                final StringBuilder sb = new StringBuilder(answerButton2.getText().length());
                sb.append(answerButton2.getText());
                String answer2 = sb.toString();

                //If textview is equal to the correct answer, change the imageview
                if(answer2.equals(current.getCorrectAnswer())) {
                    ImageView img = (ImageView) findViewById(R.id.correct);
                    img.setImageResource(R.drawable.correct);
                } else {
                    ImageView img = (ImageView) findViewById(R.id.correct);
                    img.setImageResource(R.drawable.incorrect);
                }
            }
        });

        //The third answer button in the UI
        answerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Used stringbuilder to convert textview into a string
                final StringBuilder sb = new StringBuilder(answerButton3.getText().length());
                sb.append(answerButton3.getText());
                String answer3 = sb.toString();

                //If textview is equal to the correct answer, change the imageview
                if(answer3.equals(current.getCorrectAnswer())) {
                    ImageView img = (ImageView) findViewById(R.id.correct);
                    img.setImageResource(R.drawable.correct);
                } else {
                    ImageView img = (ImageView) findViewById(R.id.correct);
                    img.setImageResource(R.drawable.incorrect);
                }
            }
        });
    }

    //Here you should populate the QA list
    protected void generateQAList(Firebase REF) {

        //set 5 questions to the list --> this logic can be changed later
        for(int i = 0; i < 5; i++) {
            setQuestion(REF);
        }

        /*
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
        */
    }

    //Add the string to the list
    protected void addQuestionToList(QuestionAnswer qA) {questionList.add(qA);}

    //Use the size of the question list to return a random integer index
    protected int genRandInt() {
        Random rand = new Random();
        int num = rand.nextInt(questionList.size());
        return num;
    }

    //Generates a random integer between 0 and max
    protected int genRandInt(int max) {
        Random rand = new Random();
        int num = rand.nextInt(max);
        return num;
    }

    //Use the size of the question list to return a random integer index
    protected String getRandomInt(long max) {
        Random rand = new Random();
        int maxQuestions = 0;

        try {
            maxQuestions = (int) max;
            maxQuestions -= 1; //account for 0 but not for the number of questions
            maxQuestions = rand.nextInt(maxQuestions);
        } catch (ArithmeticException ae) {
            //just in case there is an overflow from long to int, get random number
            maxQuestions = rand.nextInt(11); //<-- need to change that number eventually
        }
        //if there is no children in the firebase db --> send an error message
        if(maxQuestions <0) { return "Error"; }

        //turn into string for easy search in firebase db
        return Integer.toString(maxQuestions);
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

    protected void setQuestion(Firebase REF) {
        //get random question and answer from firebase
        REF.child("questionAnswer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long maxQuestions = dataSnapshot.getChildrenCount();
                //System.out.println("NUMBER OF QUESTIONS: "+maxQuestions);
                String randomNumberQuestion = getRandomInt(maxQuestions);

                //no error coming from random generator and answer/question set found
                if (!randomNumberQuestion.equalsIgnoreCase("Error") || dataSnapshot.child(randomNumberQuestion).getValue() != null) {
                    //get value, turn it into JSONObject, then separate values into string
                    String str = dataSnapshot.child(randomNumberQuestion).getValue().toString();

                    try {
                        //make sure the string is seen as a JSONObject
                        Object json = new JSONTokener(str).nextValue();
                        if(json instanceof JSONObject) {
                            JSONObject jObj = new JSONObject(str);

                            String question = jObj.get("Question").toString();
                            String answer = jObj.get("Answer").toString();
                            String wrongAnswer1 = jObj.get("Wrong1").toString();
                            String wrongAnswer2 = jObj.get("Wrong2").toString();

                            setAnswerQuestion(question, answer, wrongAnswer1, wrongAnswer2);
                        }
                        //typically caused by randomNumberQuestion, but it is fixed (keeping just in case)
                        else {
                            System.out.println("Something went wrong");
                        }

                    } catch (JSONException jex) {
                        jex.printStackTrace();
                    }
                }
                //question not found or error in random number generator
                else {
                    Toast.makeText(getApplicationContext(), "Error retrieving question",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("There was an error");
            }
        });
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

    protected void setAnswerQuestion(String q, String a, String w1, String w2) {
        QuestionAnswer qa = new QuestionAnswer(q, a, w1, w2);
        addQuestionToList(qa);
    }
}
