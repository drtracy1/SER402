package ser402team.weallcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    public static String myUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle bund = getIntent().getExtras();
        myUsername = bund.getString(MY_USERNAME);

        Button changePasswordButton = (Button) findViewById(R.id.changePasswordButton);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChangePassword();
            }
        });

    }

    protected void goToChangePassword() {
        Intent intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
        intent.putExtra(MY_USERNAME, myUsername);
        startActivity(intent);
    }
}
