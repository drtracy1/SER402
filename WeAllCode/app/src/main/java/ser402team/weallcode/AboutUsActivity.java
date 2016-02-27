package ser402team.weallcode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void showInfo(View v){
        int id = v.getId();

        if (id == R.id.imageView1) {
            /*Toast.makeText(getApplicationContext(), "wcoomber@asu.edu",
                    Toast.LENGTH_SHORT).show();
                    */

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"wcoomber@asu.edu"});
            i.putExtra(Intent.EXTRA_SUBJECT, "#WeAllCode Questions");
            i.putExtra(Intent.EXTRA_TEXT, "Great Job on the App!");
            try {
                startActivity(Intent.createChooser(i, "Sending email."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "Please install an email app onto your device.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.imageView2) {
            /*
            Toast.makeText(getApplicationContext(), "cgutier9@asu.edu",
                    Toast.LENGTH_SHORT).show();
                    */

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"cgutier9@asu.edu"});
            i.putExtra(Intent.EXTRA_SUBJECT, "#WeAllCode Questions");
            i.putExtra(Intent.EXTRA_TEXT, "Great Job on the App!");
            try {
                startActivity(Intent.createChooser(i, "Sending email."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "Please install an email app onto your device.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.imageView3) {
            /*
            Toast.makeText(getApplicationContext(), "klicata@asu.edu",
                    Toast.LENGTH_SHORT).show();
                    */

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"klicata@asu.edu"});
            i.putExtra(Intent.EXTRA_SUBJECT, "#WeAllCode Questions");
            i.putExtra(Intent.EXTRA_TEXT, "Great Job on the App!");
            try {
                startActivity(Intent.createChooser(i, "Sending email."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "Please install an email app onto your device.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.imageView4) {
            /*
            Toast.makeText(getApplicationContext(), "edressle@asu.edu",
                    Toast.LENGTH_SHORT).show();
                    */

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"edressle@asu.edu"});
            i.putExtra(Intent.EXTRA_SUBJECT, "#WeAllCode Questions");
            i.putExtra(Intent.EXTRA_TEXT, "Great Job on the App!");
            try {
                startActivity(Intent.createChooser(i, "Sending email."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "Please install an email app onto your device.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.imageView5) {
            /*
            Toast.makeText(getApplicationContext(), "alandry1@asu.edu",
                    Toast.LENGTH_SHORT).show();
                    */

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"alandry1@asu.edu"});
            i.putExtra(Intent.EXTRA_SUBJECT, "#WeAllCode Questions");
            i.putExtra(Intent.EXTRA_TEXT, "Great Job on the App!");
            try {
                startActivity(Intent.createChooser(i, "Sending email."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "Please install an email app onto your device.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.imageView6) {
            /*
            Toast.makeText(getApplicationContext(), "drtracy1@asu.edu",
                    Toast.LENGTH_SHORT).show();
                    */

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"drtracy1@asu.edu"});
            i.putExtra(Intent.EXTRA_SUBJECT, "#WeAllCode Questions");
            i.putExtra(Intent.EXTRA_TEXT, "Great Job on the App!");
            try {
                startActivity(Intent.createChooser(i, "Sending email."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "Please install an email app onto your device.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.imageButton) {
            String url = "https://weallcode.wordpress.com/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(Intent.createChooser(i, "Going to #WeAllCode Website."));
        }
        else{
            Toast.makeText(getApplicationContext(), "Error.",
                    Toast.LENGTH_SHORT).show();


        }

    }

}
