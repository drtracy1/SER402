package ser402team.weallcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class SearchFriendActivity extends AppCompatActivity {

    public static final String FRIEND_USERNAME = "ser402team.weallcode.FRIEND_USERNAME";
    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    private static final int EMPTY = 0;
    private static String usernameLowercase = "";
    private static String friendUsernameStr = "";
    private static String myUsername = "";
    private static Button selectButton = null;
    private static TextView showFoundUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        //connect to firebase
        Firebase.setAndroidContext(this);
        final Firebase REF = new Firebase("https://weallcode-users.firebaseio.com/");

        //get current logged in username
        Bundle bund = getIntent().getExtras();
        myUsername = bund.getString(MY_USERNAME);

        //hide add/select button until a username has been searched
        selectButton = (Button) findViewById(R.id.continueButton);
        selectButton.setVisibility(View.INVISIBLE);

        //when search button is clicked, look for username
        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchingUsername = (EditText) findViewById(R.id.searchUsername);

                //if user entered own username, send message
                if(searchingUsername.getText().toString().equalsIgnoreCase(myUsername)) {
                    Toast.makeText(getApplicationContext(), "Please enter a different username",
                            Toast.LENGTH_SHORT).show();
                }
                //username was entered, set up string and search for username
                else if(searchingUsername.length() != EMPTY) {
                    friendUsernameStr = searchingUsername.getText().toString();
                    setUsernameLowercase();
                    findUsername(REF);
                }
                //user did not add anything to the search line
                else {
                    Toast.makeText(getApplicationContext(), "Please enter a username",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //setters and getters
    private void setUsernameLowercase() { usernameLowercase = getUsername().toLowerCase(); }
    private String getUsername() { return friendUsernameStr; }
    private String getUsernameLowercase() { return usernameLowercase; }

    protected void findUsername(Firebase REF) {

        //find username
        REF.child("userAccounts").child(getUsernameLowercase()).
                addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //account found
                if(dataSnapshot.getValue() != null) {
                    //show button to allow user to select searched username
                    selectButton.setVisibility(View.VISIBLE);

                    //allow confirmation
                    showFoundUsername = (TextView) findViewById(R.id.showUsername);
                    String strMsg = "Send a message to "+getUsername();
                    showFoundUsername.setText(strMsg);

                    //set up the continue button to allow user to send message to the searched username
                    Button continueButton = (Button) findViewById(R.id.continueButton);
                    continueButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //intent
                            goToChatActivity();
                        }
                    });

                }
                //account not found
                else {
                    Toast.makeText(getApplicationContext(), "Unknown username or password",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("There was an error");
            }
        });
    }

    //send user to the chat activity page
    public void goToChatActivity() {
        Intent intent = new Intent(SearchFriendActivity.this, ChatActivity.class);
        intent.putExtra(FRIEND_USERNAME, getUsername());
        intent.putExtra(MY_USERNAME, myUsername);
        startActivity(intent);
    }
}
