package ser402team.weallcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


/**
 * Created by CrystalGutierrez on 2/20/2016.
 */
public class AvatarActivity extends AppCompatActivity {

    int avatarSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_activity);
    }

    public void selectAvatar(View v){
        int id = v.getId();

        if (id == R.id.imageView1) {
            Toast.makeText(getApplicationContext(), "Selected",
                    Toast.LENGTH_SHORT).show();
            avatarSelected = 1;
        }
        else if (id == R.id.imageView2) {
            Toast.makeText(getApplicationContext(), "Selected",
                    Toast.LENGTH_SHORT).show();
            avatarSelected = 2;
        }
        else if (id == R.id.imageView3) {
            Toast.makeText(getApplicationContext(), "Selected",
                    Toast.LENGTH_SHORT).show();
            avatarSelected = 3;
        }
        else if (id == R.id.imageView4) {
            Toast.makeText(getApplicationContext(), "Selected",
                    Toast.LENGTH_SHORT).show();
            avatarSelected = 4;
        }
        else{
            Toast.makeText(getApplicationContext(), "Error.",
                    Toast.LENGTH_SHORT).show();
        }

    }


}
