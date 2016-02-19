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

public class CreateAccActivity extends AppCompatActivity {

    public final static String USERNAME = "ser402team.weallcode.USERNAME";
    private static final String filename = "userInfo.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        Button createAccountButt = (Button) findViewById(R.id.buttonDone);
        createAccountButt.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //get username, password and email from user
                        final EditText newUsername = (EditText) findViewById(R.id.editName);
                        final EditText newPassword1 = (EditText) findViewById(R.id.editPassword1);
                        final EditText newPassword2 = (EditText) findViewById(R.id.editPassword2);
                        final EditText newEmail = (EditText) findViewById(R.id.editEmail);
                        String strUsername = newUsername.getText().toString();
                        String strPassword1 = newPassword1.getText().toString();
                        String strPassword2 = newPassword2.getText().toString();
                        String strEmail = newEmail.getText().toString();

                        Context context = getBaseContext();
                        JsonHandler jh = new JsonHandler();

                        //check if username entered is valid
                        if (!isValid(strUsername) || strUsername.length() == 0) {
                            Toast.makeText(getApplicationContext(),
                                    "Username is not valid. Please no special characters or spaces.",
                                    Toast.LENGTH_LONG).show();
                        }
                        //check if email is valid
                        else if(!isEmailValid(strEmail)) {
                            Toast.makeText(getApplicationContext(),
                                    "Email is not valid. Please enter a valid email address.",
                                    Toast.LENGTH_LONG).show();
                        }
                        //check if passwords match
                        else if(!doPasswordsMatch(strPassword1, strPassword2)) {
                            Toast.makeText(getApplicationContext(),
                                    "Passwords do not match. Please try again.",
                                    Toast.LENGTH_LONG).show();
                        }
                        //check if username exists
                        else if(jh.usernameExists(context, filename, strUsername)) {
                            Toast.makeText(getApplicationContext(), strUsername + " already exists",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //if everything validates, allow account to be created and log in
                        else {
                            jh.createUsername(context, filename, strUsername, strPassword1, strEmail);
                            allowLogin(strUsername);
                        }
                    }
                }
        );

    }

    private boolean isValid(String str) {
        return str.matches("[a-zA-Z0-9]*");
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean doPasswordsMatch(String pwd1, String pwd2) {
        return pwd1.equals(pwd2);
    }

    private void allowLogin(String userName) {
        Intent intent = new Intent(CreateAccActivity.this, MainPageActivity.class);
        intent.putExtra(USERNAME, userName);
        startActivity(intent);
    }
}
