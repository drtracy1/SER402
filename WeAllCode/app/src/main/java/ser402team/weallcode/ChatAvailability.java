package ser402team.weallcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ChatAvailability extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_availability);

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
        startActivity(intent);
    }
}
