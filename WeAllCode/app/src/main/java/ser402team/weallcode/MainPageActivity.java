package ser402team.weallcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //get username from LoginActivity
        // NOTE: **** if pushed back button from another page, then issues may arise ****
        Bundle bund = getIntent().getExtras();
        myUsername = bund.getString(MY_USERNAME);

        TextView welcomeMsg = (TextView) findViewById(R.id.mainPageUsernameView);
        String welcomeUser = "Welcome, "+myUsername;
        welcomeMsg.setText(welcomeUser);


        //Changes the current activity to the Question page
        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, QuestionActivity.class);
                intent.putExtra(MY_USERNAME, myUsername);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void goToAboutUs(View v){
        Intent intent = new Intent(MainPageActivity.this, AboutUsActivity.class);
        startActivity(intent);
    }

    public void goToAvatar(View v){
        Intent intent = new Intent(MainPageActivity.this, AvatarMenuActivity.class);
        startActivity(intent);
    }

    public void goToSettings(View v){
        Intent intent = new Intent(MainPageActivity.this, SettingsActivity.class);
        intent.putExtra(MY_USERNAME, myUsername);
        startActivity(intent);
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
}
