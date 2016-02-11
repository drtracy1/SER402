package ser402team.weallcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {

    public final static String USERNAME = "ser402team.weallcode.USERNAME";
    private static int indexWhereUsernameIs = 0;

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

                        //check username and password
                        if(authenticateLogin(strLoginName, strPassword)) {
                            allowLogin(strLoginName);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Unknown username or password",
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

    public boolean authenticateLogin(String strUsername, String strPassword) {

        boolean allowLogin = false;
        String strFromFile = "";
        String uppercaseUsername = strUsername.toUpperCase();

        try {
            //read from json file
            InputStream in = getResources().openRawResource(R.raw.users);
            int size = in.available();
            byte [] buffer = new byte[size];
            in.read(buffer);
            in.close();

            //turn buffer from file into string
            strFromFile = new String(buffer, "UTF-8");

            //find out if username and password match or not
            indexWhereUsernameIs = findUsernameIndex(strFromFile, uppercaseUsername);

            //if username was found check password, ask again if not
            if(indexWhereUsernameIs != -1) {
                if (passwordCorrect(strFromFile, strPassword)){
                    //allow user to log in
                    allowLogin = true;
                }
                else {
                    //ask to reenter username and password
                    allowLogin =  false;
                }
            }
            else {
                //ask to reenter username and password
                allowLogin = false;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return allowLogin;
    }

    //find if the username exists, if so get the index number
    public int findUsernameIndex(String strFromFile, String strUsername) {
        try {
            JSONArray array = new JSONArray(strFromFile);

            //get each JSON object in JSON array
            for(int i = 0; i < array.length(); i++)
            {
                JSONObject obj = array.getJSONObject(i);
                String str = obj.getString("Username");

                //looking for username entered by user with JSON records
                if(str.compareToIgnoreCase(strUsername) == 0) {
                    System.out.println("Found user in json file: "+strUsername);
                    //return the index where the username exists
                    return i;
                }
            }

        } catch (JSONException je) {
            je.printStackTrace();
        }
        System.out.println("Did not find user in json file");
        return -1;
    }

    //see if the username matches the password entered
    public boolean passwordCorrect(String strFromFile, String strPassword) {

        boolean correctPassword = false;

        try {
            //get object with username found
            JSONArray array = new JSONArray(strFromFile);
            JSONObject obj = array.getJSONObject(indexWhereUsernameIs);
            String str = obj.getString("Password");

            //password entered must match password on record exactly
            if(str.equals(strPassword)) {
                System.out.println("Password matches Username in json");
                correctPassword =  true;
            }
            else {
                System.out.println("Password did not match username in json");
                correctPassword = false;
            }

        } catch (JSONException je) {
            je.printStackTrace();
        }
        return correctPassword;
    }
}
