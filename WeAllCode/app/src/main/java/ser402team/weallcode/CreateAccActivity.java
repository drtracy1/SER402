package ser402team.weallcode;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.util.Patterns;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateAccActivity extends AppCompatActivity {

    public final static String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    private static String usernameLowercase = "";
    private static String username = "";
    private static String password1 = "";
    private static String password2 = "";
    private static String email = "";
    private static UserInformation ui = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        //connect to firebase
        Firebase.setAndroidContext(this);
        final Firebase REF = new Firebase("https://weallcode-users.firebaseio.com/");

        Button createAccountButt = (Button) findViewById(R.id.buttonDone);
        createAccountButt.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //get username, password and email from user
                        final EditText newUsername = (EditText) findViewById(R.id.editName);
                        final EditText newPassword1 = (EditText) findViewById(R.id.editPassword1);
                        final EditText newPassword2 = (EditText) findViewById(R.id.editPassword2);
                        final EditText newEmail = (EditText) findViewById(R.id.editEmail);

                        setUsername(newUsername.getText().toString());
                        setUsernameLowercase();
                        setPassword1(newPassword1.getText().toString());
                        setPassword2(newPassword2.getText().toString());
                        setEmail(newEmail.getText().toString());

                        //gather user information into one object
                        ui = new UserInformation(getUsername(), getEmail(), getPassword1());

                        //check if username entered is valid
                        if (!isValid(getUsername()) || getUsername().length() == 0) {
                            Toast.makeText(getApplicationContext(),
                                    "Username is not valid. Please no special characters or spaces.",
                                    Toast.LENGTH_LONG).show();
                        }
                        //check if email is valid
                        else if(!isEmailValid(getEmail())) {
                            Toast.makeText(getApplicationContext(),
                                    "Email is not valid. Please enter a valid email address.",
                                    Toast.LENGTH_LONG).show();
                        }
                        //check if passwords match
                        else if(!doPasswordsMatch(getPassword1(), getPassword2())) {
                            Toast.makeText(getApplicationContext(),
                                    "Passwords do not match. Please try again.",
                                    Toast.LENGTH_LONG).show();
                        }
                        //create user if it does not exist
                        else {
                            REF.child("userAccounts").child(getUsernameLowercase()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    //found account
                                    if (dataSnapshot.getValue() != null) {
                                        //System.out.println("ACCOUNT FOUND");
                                        Toast.makeText(getApplicationContext(),
                                                "Account already exists. Please try another username.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    //did not find username
                                    else {
                                        //System.out.println("ACCOUNT NOT FOUND");
                                        Firebase user_account = REF.child("userAccounts").child(getUsernameLowercase());
                                        user_account.setValue(ui);
                                        allowLogin(getUsername());
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    System.out.println("There was an error");
                                }
                            });
                        }
                    }
                }
        );

    }

    //setters and getters
    private void setUsername(String un) { username = un; }
    private void setUsernameLowercase() { usernameLowercase = getUsername().toLowerCase(); }
    private void setPassword1(String pw) { password1 = pw; }
    private void setPassword2(String pw) { password2 = pw; }
    private void setEmail(String em) { email = em; }
    private String getUsername() { return username; }
    private String getUsernameLowercase() { return usernameLowercase; }
    private String getPassword1() { return password1; }
    private String getPassword2() { return password2; }
    private String getEmail() { return email; }

    private boolean isValid(String str) {
        return str.matches("[a-zA-Z0-9]*");
    }

    private boolean isEmailValid(String email) { return Patterns.EMAIL_ADDRESS.matcher(email).matches(); }

    private boolean doPasswordsMatch(String pwd1, String pwd2) {
        return pwd1.equals(pwd2);
    }

    private void allowLogin(String userName) {
        Intent intent = new Intent(CreateAccActivity.this, MainPageActivity.class);
        intent.putExtra(MY_USERNAME, userName);
        startActivity(intent);
    }
}
