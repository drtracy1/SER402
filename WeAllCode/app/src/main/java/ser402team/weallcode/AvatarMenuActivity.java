package ser402team.weallcode;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KBasra on 4/4/16.
 */
public class AvatarMenuActivity  extends AppCompatActivity {

    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    public static String myUsername = "";
    public static final String MY_EMAIL = "ser402team.weallcode.MY_EMAIL";
    private static String email = "";
    public static final String FIREBASE_URL = "https://weallcode-users.firebaseio.com/";
    private static Firebase REF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_menu);

        Bundle bund = getIntent().getExtras();
        myUsername = bund.getString(MY_USERNAME);

        //connect to firebase
        Firebase.setAndroidContext(this);
        REF = new Firebase(FIREBASE_URL).child("userAccounts");
        //get username email address to find their gravatar
        getEmailAddress();

        Button avatarButton = (Button) findViewById(R.id.avatarCharacterButton);
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvatarMenuActivity.this, AvatarActivity.class);
                startActivity(intent);
            }
        });

        Button gravatarButton = (Button) findViewById(R.id.gravatarCharacterButton);
        gravatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvatarMenuActivity.this, GravatarActivity.class);
                intent.putExtra(MY_USERNAME, myUsername);
                intent.putExtra(MY_EMAIL, email);
                startActivity(intent);
            }
        });
    }

    protected void getEmailAddress() {
        //find username
        REF.child(myUsername.toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //account found
                if (dataSnapshot.getValue() != null) {
                    //get value, turn it into JSONObject, then separate values into string
                    String str = dataSnapshot.getValue().toString();
                    try {
                        JSONObject jObj = new JSONObject(str);
                        email = jObj.get("email").toString();

                    } catch (JSONException jex) {
                        jex.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //error
            }
        });
    }
}
