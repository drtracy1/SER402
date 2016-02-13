package ser402team.weallcode;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                        final EditText newPassword = (EditText) findViewById(R.id.editPassword);
                        final EditText newEmail = (EditText) findViewById(R.id.editEmail);
                        String strUsername = newUsername.getText().toString();
                        String strPassword = newPassword.getText().toString();
                        String strEmail = newEmail.getText().toString();

                        Context context = getBaseContext();
                        JsonHandler jh = new JsonHandler();

                        //check for already used username
                        if(!jh.usernameExists(context, filename ,strUsername)){
                            //add new username
                            jh.createUsername(context, filename, strUsername, strPassword, strEmail);
                            allowLogin(strUsername);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), strUsername + " already exists",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );

    }

    private void allowLogin(String userName) {
        Intent intent = new Intent(CreateAccActivity.this, MainPageActivity.class);
        intent.putExtra(USERNAME, userName);
        startActivity(intent);
    }
}
