package ser402team.weallcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

/**
 * Created by CrystalGutierrez on 2/3/2016.
 * Modified by DanielTracy
 *
 * @update Kristel Basra
 * username now accessed to this page
 */
public class MainPageActivity extends AppCompatActivity {

    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    public static String myUsername = "";
    public static String absolute_path = "";
    public static final String ABS_PATH_BEGIN = "/data/data/";
    public static final String ABS_PATH_END = "/app_imageDir";

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "naxf6CjkvdAcmwUMT4arT2n32";
    private static final String TWITTER_SECRET = "4GeK4IkXjp4yDl86vVQaueMhadSncLWlhReATS1ZKUYWF5qlOo ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new TweetComposer());

        //get username from LoginActivity
        // NOTE: **** if pushed back button from another page, then issues may arise ****
        Bundle bund = getIntent().getExtras();
        myUsername = bund.getString(MY_USERNAME);

        TextView welcomeMsg = (TextView) findViewById(R.id.mainPageUsernameView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/CHERI___.TTF");
        welcomeMsg.setTypeface(custom_font);
        String welcomeUser = "Welcome, "+myUsername;
        welcomeMsg.setText(welcomeUser);


        absolute_path = ABS_PATH_BEGIN + getApplicationContext().getPackageName() + ABS_PATH_END;
        loadImageFromStorage();

        //Changes the current activity to the Question page
        ImageView playButton = (ImageView) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, QuestionActivity.class);
                intent.putExtra(MY_USERNAME, myUsername);
                startActivity(intent);
            }
        });

        ImageView avatarButton = (ImageView) findViewById(R.id.avatarButton);
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, AvatarActivity.class);
                startActivity(intent);
            }
        });


        ImageView aboutButton = (ImageView) findViewById(R.id.aUbutton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        ImageView logButton = (ImageView) findViewById(R.id.logOutButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text("This is a fun app.");
        builder.show();


        //ImageView tweetButton = (ImageView) findViewById(R.id.tweet_Button);
        //tweetButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        TweetComposer.Builder builder = new TweetComposer.Builder(this)
        //                .text("This is a fun app.");
        //        builder.show();


        //    }
        //});
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    protected void loadImageFromStorage() {
        try {
            File file = new File(absolute_path, myUsername + ".jpg");
            if(file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                ImageView imageView = (ImageView) findViewById(R.id.avatarImageView);
                imageView.setImageBitmap(bitmap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
