package ser402team.weallcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

//import org.json.JSONException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
        /*
        *
        * @author Wesley Coomber Wesley.Coomber@asu.edu
        *
        * @version Feburary 2016
        */

public class MainActivity extends AppCompatActivity {

    private ImageView logoView1;
    private ImageView fbPic1;

    private TextView textViewT;

    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;

    public static final String FIREBASE_URL = "https://weallcode-users.firebaseio.com/";

    private String email1 = "d@default.com";
    private String name1 = "jon doe";
    private String nameLast = "doe";
    private String nameFirst = "john";
    private String gender1 = "man";
    private String locale1 = "NYC";


    private static UserInformation ui = null;


    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    private static String usernameLowercase = "";
    private static String username = "";
    private static String password = "";
    private static String email = "";
    private static String userID = "-1";

    private String hashDev = "5iQu58JfnNr+GkLxJ+XlGjvgSKw=";

     private static boolean  ret = false;

    private static Bitmap bmpFB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        //connect to firebase
        Firebase.setAndroidContext(this);
        final Firebase REF = new Firebase(FIREBASE_URL);

        final ProfilePictureView fbIcon = (ProfilePictureView) findViewById(R.id.fbPic1);


        fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);

        //There are other potentially useful read permissions but we have to go through an official facebook.com registration and review process.
        //If we have time we should look into doing that, but for testing and prototyping purposes--is not necessary.
        fbLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));


        // THIS IS THE CALLback that actually does an async network call to get the facebook data from the login.
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(getApplicationContext(), "FACEBOOK LOGIN SUCCESS!",
                        Toast.LENGTH_SHORT).show();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                try {

                                    //This is just a sample of how to get info from the fb return object. We can get id, gender, age, locale etc. from the facebook login feature.
                                    email1 = object.getString("email"); // eg. wcoomber@asu.edu
                                    name1 = object.getString("name"); // fullname eg. Wesley Coomber
                                    nameLast = object.getString("last_name");
                                    nameFirst = object.getString("first_name");
                                    gender1 = object.getString("gender");
                                    locale1 = object.getString("locale");
                                   // textViewT.setText("Hi, " + name1 + ", " + email1 + ", " + gender1 + ", " + locale1);
                                    //System.out.println("Hi2, " + email1 + name1);

                                    //Integer tempI = Integer.parseInt(object.getString("id"));]
                                    Integer tempI = object.getInt("id");
                                    String tempP = nameLast + (tempI % 1000);
                                    password = tempP;

                                    setEmail(email1);
                                    setUsername(nameFirst + nameLast);
                                    setUsernameLowercase();
                                    setPassword(tempP);

                                    //Picasso.with(MainActivity.this).load("https://graph.facebook.com/" + userID+ "/picture?type=large").into(fbPic1);
                                    //android.util.Log.w(getClass().getSimpleName(), "https://graph.facebook.com/" + userID+ "/picture?type=large");

                                    //use the nifty new ProfilePictureView to display user's facebook icon.
                                    fbIcon.setProfileId(object.getString("id"));
                                    android.util.Log.w(getClass().getSimpleName(), "pPV: " + object.getString("id"));

                                    //gather user information into one object
                                    ui = new UserInformation(getUsername(), getEmail(), getPassword());

                                    //translate fbImage into bitmap -> bmpFB
                                    ImageView fbImage = ( ( ImageView)fbIcon.getChildAt( 0));
                                        bmpFB  = ( (BitmapDrawable) fbImage.getDrawable()).getBitmap();


                                    if( authenticateLogin(REF)){

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
                                                            "Account already exists. You have used Facebook to login before.",
                                                            Toast.LENGTH_LONG).show();
                                                }
                                                //did not find username
                                                else {
                                                    //System.out.println("ACCOUNT NOT FOUND");
                                                    Firebase user_account = REF.child("userAccounts").child(getUsernameLowercase());
                                                    user_account.setValue(ui);
                                                    allowLogin();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(FirebaseError firebaseError) {
                                                System.out.println("There was an error2");
                                            }
                                        });
                                    }

                                    Toast.makeText(getApplicationContext(), ("Hi, " + name1 + ", USERNAME: " + nameFirst+nameLast + ", PASSWORD: " + tempP),
                                            Toast.LENGTH_LONG).show();



                                } catch (JSONException ex) {
                                    System.out.println("ERROR GETTING FACEBOOK DATA!");
                                }


                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, name, last_name, email,gender, age_range, picture, locale");
                request.setParameters(parameters);
                request.executeAsync();
            }




            @Override
            public void onCancel() {
                //Log.v("LoginActivity", "cancel");
                Toast.makeText(getApplicationContext(), "FACEBOOK LOGIN CANCELED!",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "FACEBOOK LOGIN ERROR!",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }



    //setters and getters
    private void setUsername(String un) { username = un; }
    private void setUsernameLowercase() { usernameLowercase = getUsername().toLowerCase(); }
    private void setPassword(String pw) { password = pw; }
    private void setEmail(String em) { email = em; }
    private String getUsername() { return username; }
    private String getUsernameLowercase() { return usernameLowercase; }
    private String getPassword() { return password; }
    private String getEmail() { return email; }

    //authentication method
    public boolean authenticateLogin(Firebase REF) {
        ret = false;
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
                                    //resetFields();
                                    ret = true;
                                    allowLogin();

                                }
                                //username and password do not match records together
                                else {
                                    //Toast.makeText(getApplicationContext(), "Unknown username or password1",Toast.LENGTH_SHORT).show();
                                    ret = false;
                                }

                            } catch (JSONException jex) {
                                jex.printStackTrace();
                            }
                        }
                        //account not found
                        else {
                            //Toast.makeText(getApplicationContext(), "Unknown username or password1", Toast.LENGTH_SHORT).show();
                            ret = false;
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("There was an error1");
                        ret = false;
                    }
                });
        return ret;
    }

    //allow user to log in after credentials have been authenticated
    public void allowLogin() {
        Intent intent = new Intent(MainActivity.this, MainPageActivity.class);

        android.util.Log.w(getClass().getSimpleName(), "allowLogin() Start" );

        //convert the bitmap version of the fb profile pic into a byte stream because bitmap is not serializable.
        if (bmpFB != null){
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            bmpFB.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byte[] byteArrayFB = bStream.toByteArray();

            intent.putExtra("imgFB", byteArrayFB);
            android.util.Log.w(getClass().getSimpleName(), "toBit: allowLogin() Success" );

        }
        else{
            android.util.Log.w(getClass().getSimpleName(), "toBit: allowLogin() FAILED" );
        }


        intent.putExtra(MY_USERNAME, getUsername());
          startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.callbackManager.onActivityResult(requestCode,
                resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToLogin(View v){
        //android.util.Log.w(getClass().getSimpleName(), "goToDialog");
        final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        v.startAnimation(animTranslate);
        //Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);

        //textViewT.setText("Hi, " + email1 + name1);
        //System.out.println("Hi2, " + email1 + name1);



        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    public void goToCreateAcc(View v){
        //android.util.Log.w(getClass().getSimpleName(), "goToDialog");
        final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        v.startAnimation(animTranslate);
        Intent intent = new Intent(MainActivity.this, CreateAccActivity.class);
        startActivity(intent);
    }
}
