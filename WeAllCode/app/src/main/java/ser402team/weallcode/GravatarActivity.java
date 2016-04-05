package ser402team.weallcode;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class GravatarActivity extends AppCompatActivity {

    private final String ABS_PATH = "ABS_PATH";
    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    public static final String MY_EMAIL = "ser402team.weallcode.MY_EMAIL";
    private static String email = "";
    private static String myUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravatar);

        //get the user's email address to connect with Gravatar
        Bundle bund = getIntent().getExtras();
        email = bund.getString(MY_EMAIL);
        myUsername = bund.getString(MY_USERNAME);

        List<User> users = new ArrayList<User>(1);
        final User user = new User(myUsername, email);
        users.add(user);

        GravatarAdapter adapter = new GravatarAdapter(this);
        adapter.updateUsers(users);

        ListView theList = (ListView) findViewById(R.id.gravList);

        theList.setAdapter(adapter);

        theList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView iv = (ImageView) view.findViewById(R.id.user_avatar);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) iv.getDrawable();
                Bitmap image = bitmapDrawable.getBitmap();

                String path = saveToInternalStorage(image);
                Intent intent = new Intent(GravatarActivity.this, MainPageActivity.class);
                intent.putExtra(ABS_PATH, path);
                intent.putExtra(MY_USERNAME, myUsername);
                startActivity(intent);
            }
        });
    }


    protected String saveToInternalStorage(Bitmap bitmap) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myPath = new File(directory, myUsername + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return directory.getAbsolutePath();
    }

    static final class User {
        String name;
        String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }
}
