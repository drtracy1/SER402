package ser402team.weallcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class ChatAvailability extends AppCompatActivity {

    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    public static String myUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_availability);

        Bundle bund = getIntent().getExtras();
        myUsername = bund.getString(MY_USERNAME);

        //add friend
        ImageView addButton = (ImageView) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchFriend();
            }
        });
    }

    protected void goToSearchFriend() {
        Intent intent = new Intent(ChatAvailability.this, SearchFriendActivity.class);
        intent.putExtra(MY_USERNAME, myUsername);
        startActivity(intent);
    }
}
