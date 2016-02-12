package ser402team.weallcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class CreateAccActivity extends AppCompatActivity {

    public final static String USERNAME = "ser402team.weallcode.USERNAME";

    //FUTURE NOTE: implement serialization to forward hashmap with intent
    HashMap<String, Object> allUsers = new HashMap<>();

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

                        //check for already used username
                        if(!usernameExists(strUsername)){
                            usersInformation newUser = new usersInformation(strUsername, strPassword, strEmail);
                            allUsers.put(strUsername, newUser);
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

    private boolean usernameExists(String strUsr) {
        //if the hashmap is empty then username does not exsits yet
        if(allUsers.isEmpty()) {
            return false;
        }

        //go through hashmap to find if username exists
        for(int i = 0; i < allUsers.size(); i++) {
            if (allUsers.containsKey(strUsr)) {
                //username exists
                return true;
            }
        }
        //username does not exist
        return false;
    }

    private void allowLogin(String userName) {
        Intent intent = new Intent(CreateAccActivity.this, MainPageActivity.class);
        intent.putExtra(USERNAME, userName);
        startActivity(intent);
    }
}
