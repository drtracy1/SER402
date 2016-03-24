package ser402team.weallcode;

import android.content.Intent;
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

//import org.json.JSONException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

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

    private TextView textViewT;

    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;

    private String email1 = "d@default.com";
    private String name1 = "jon doe";

    private String hashDev = "5iQu58JfnNr+GkLxJ+XlGjvgSKw=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewT = (TextView) findViewById(R.id.textView3);

        //FacebookSdk.sdkInitialize(getApplicationContext());

        fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);

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
                                    email1 = object.getString("email"); // eg. wcoomber@asu.edu
                                    name1 = object.getString("name"); // fullname eg. Wesley Coomber
                                    textViewT.setText("Hi, " + name1 + ", " + email1);
                                    //System.out.println("Hi2, " + email1 + name1);

                                } catch (JSONException ex) {
                                    System.out.println("ERROR GETTING FACEBOOK DATA!");
                                }


                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, name, last_name, email,gender, age_range, locale");
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

        textViewT.setText("Hi, " + email1 + name1);
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
