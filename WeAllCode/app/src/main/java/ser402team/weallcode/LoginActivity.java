package ser402team.weallcode;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    public static final String USERNAME = "ser402team.weallcode.USERNAME";
    private static final String filename = "userInfo.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //get username and password from user
                        final EditText lName = (EditText) findViewById(R.id.loginName);
                        final EditText pwd = (EditText) findViewById(R.id.password);
                        String strLoginName = lName.getText().toString();
                        String strPassword = pwd.getText().toString();

                        Context context = getBaseContext();
                        JsonHandler jh = new JsonHandler();
                        File file = context.getFileStreamPath(filename);

                        if(file.exists()) {
                            //check username and password
                            boolean nameAndPasswordMatch = jh.authenticateLogin(context, filename, strLoginName, strPassword);

                            if (nameAndPasswordMatch) {
                                allowLogin(strLoginName);
                            } else {
                                Toast.makeText(getApplicationContext(), "Unknown username or password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            //do not look through a non existent file
                            // need to create an account for a file to exist
                            Toast.makeText(getApplicationContext(), "Need to create first account",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void allowLogin(String userName) {
        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        intent.putExtra(USERNAME, userName);
        startActivity(intent);
    }
}
