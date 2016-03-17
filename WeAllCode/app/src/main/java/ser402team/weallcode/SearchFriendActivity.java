package ser402team.weallcode;

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

    private static final int EMPTY = 0;
    private static String usernameLowercase = "";
    private static String usernameStr = "";
    private static Button selectButton = null;
    private static TextView showFoundUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        //connect to firebase
        Firebase.setAndroidContext(this);
        final Firebase REF = new Firebase("https://weallcode-users.firebaseio.com/");

        //hide add/select button until a username has been searched
        selectButton = (Button) findViewById(R.id.continueButton);
        selectButton.setVisibility(View.INVISIBLE);

        //when search button is clicked, look for username
        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchingUsername = (EditText) findViewById(R.id.searchUsername);
                //username was entered, set up string and search for username
                if(searchingUsername.length() != EMPTY) {
                    setUsername(searchingUsername.getText().toString());
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
    private void setUsername(String un) { usernameStr = un; }
    private void setUsernameLowercase() { usernameLowercase = getUsername().toLowerCase(); }
    private String getUsername() { return usernameStr; }
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
}
