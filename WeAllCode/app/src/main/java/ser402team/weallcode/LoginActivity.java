package ser402team.weallcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public static String loginName;
    public static String password;

    private static void setLoginName(String ln) {
        loginName = ln;
    }
    private static void setPassword(String pwd) {
        password = pwd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signInButton = (Button) findViewById(R.id.signInButton);

        signInButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        final EditText lName = (EditText) findViewById(R.id.loginName);
                        final EditText pwd = (EditText) findViewById(R.id.password);
                        setLoginName(lName.getText().toString());
                        setPassword(pwd.getText().toString());
                    }
                }
        );
    }
}
