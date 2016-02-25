package ser402team.weallcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public static final String USERNAME = "ser402team.weallcode.USERNAME";
    private static String usernameLowercase = "";
    private static String username = "";
    private static String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //connect to firebase
        Firebase.setAndroidContext(this);
        final Firebase REF = new Firebase("https://weallcode-users.firebaseio.com/");

        //Return button functionality
        ImageButton returnButton = (ImageButton) findViewById(R.id.returnToMainPageButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //sign in button
        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        //get username and password from username
                        final EditText lName = (EditText) findViewById(R.id.loginName);
                        final EditText pwd = (EditText) findViewById(R.id.password);
                        setUsername(lName.getText().toString());
                        setUsernameLowercase();
                        setPassword(pwd.getText().toString());

                        //make sure the user enters something for username and password
                        if(getUsername().length() == 0 || getPassword().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Please enter a username and password",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            authenticateLogin(REF);
                        }
                    }
                }
        );
    }

    //allow user to log in after credentials have been authenticated
    public void allowLogin() {
        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        intent.putExtra(USERNAME, getUsername());
        startActivity(intent);
    }

    //setters and getters
    private void setUsername(String un) { username = un; }
    private void setUsernameLowercase() { usernameLowercase = getUsername().toLowerCase(); }
    private void setPassword(String pw) { password = pw; }
    private String getUsername() { return username; }
    private String getUsernameLowercase() { return usernameLowercase; }
    private String getPassword() { return password; }

    //authentication method
    public void authenticateLogin(Firebase REF) {

        //find username
        REF.child("userAccounts").child(getUsernameLowercase()).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //account found
                if(dataSnapshot.getValue() != null) {
                    //get value, turn it into JSONObject, then separate values into string
                    String str = dataSnapshot.getValue().toString();
                    try {
                        JSONObject jObj = new JSONObject(str);
                        String strUsername = jObj.get("username").toString();
                        String strPassword = jObj.get("password").toString();

                        //confirm username and password match records
                        if (strUsername.equalsIgnoreCase(getUsername()) &&
                                strPassword.equals(getPassword())) {
                            allowLogin();
                        }
                        //username and password do not match records together
                        else {
                            Toast.makeText(getApplicationContext(), "Unknown username or password",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException jex) {
                        jex.printStackTrace();
                    }
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
