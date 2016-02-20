package ser402team.weallcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
        /*
        *
        * @author Wesley Coomber Wesley.Coomber@asu.edu
        *
        * @version Feburary 2016
        */

public class MainActivity extends AppCompatActivity {

    private ImageView logoView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //logoView1 = (ImageView) findViewById(R.id.logoView1);
       // logoView1.setImageResource(R.drawable.logo);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
/*
        Button buttonLog = (Button)findViewById(R.id.button_Login);

        buttonLog.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }});
            */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
