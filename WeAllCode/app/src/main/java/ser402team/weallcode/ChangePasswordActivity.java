package ser402team.weallcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends AppCompatActivity {

    public static final String FIREBASE_URL = "https://weallcode-users.firebaseio.com/";
    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    public static Firebase REF;
    public static String myUsername = "";
    private static String currentPassword = "";
    private static String newPassword1 = "";
    private static String newPassword2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Bundle bund = getIntent().getExtras();
        myUsername = bund.getString(MY_USERNAME);

        //connect to firebase
        Firebase.setAndroidContext(this);
        REF = new Firebase(FIREBASE_URL).child("userAccounts");

        Button done = (Button) findViewById(R.id.doneChangePasswordButton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPasswordInformation();
            }
        });

    }

    protected void getPasswordInformation() {
        //assign edit text lines
        EditText currPass = (EditText) findViewById(R.id.currentPassword);
        EditText newPass1 = (EditText) findViewById(R.id.newPassword1);
        EditText newPass2 = (EditText) findViewById(R.id.newPassword2);
        currentPassword = currPass.getText().toString();
        newPassword1 = newPass1.getText().toString();
        newPassword2 = newPass2.getText().toString();

        //get values from the EditText lines (make sure they are not blank)
        if(currentPassword.length() == 0 || newPassword1.length() == 0 || newPassword2.length() == 0) {
            Toast.makeText(ChangePasswordActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            checkCurrentPassword();
        }
    }

    protected void checkCurrentPassword() {
        REF.child(myUsername.toLowerCase()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null) {
                            //get value, turn it into JSONObject, then separate values into string
                            String str = dataSnapshot.getValue().toString();
                            try {
                                JSONObject jObj = new JSONObject(str);
                                String strPassword = jObj.get("password").toString();

                                //confirm username and password match records
                                if (strPassword.equals(currentPassword)) {
                                    if(newPassword1.equals(newPassword2)) {
                                        changePassword();
                                    }
                                    else {
                                        Toast.makeText(ChangePasswordActivity.this, "New passwords do not match",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //username and password do not match records together
                                else {
                                    Toast.makeText(getApplicationContext(), "Incorrect current password",
                                            Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException jex) {
                                jex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
    }

    protected void changePassword() {
        REF.child(myUsername.toLowerCase()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null) {
                            REF.child(myUsername.toLowerCase()).child("password").setValue(newPassword1);
                            Intent intent = new Intent(ChangePasswordActivity.this, Confirmation.class);
                            intent.putExtra(MY_USERNAME, myUsername);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ChangePasswordActivity.this, "Error with database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        //error
                    }
                });
    }
}
