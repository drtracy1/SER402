package ser402team.weallcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        Button createAccountButt = (Button) findViewById(R.id.buttonDone);

        createAccountButt.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //get username, password and email from user
                        final EditText newName = (EditText) findViewById(R.id.editName);
                        final EditText newPwd = (EditText) findViewById(R.id.editPassword);
                        final EditText newEmail = (EditText) findViewById(R.id.editEmail);
                        String strNewName = newName.getText().toString();
                        String strPassword = newPwd.getText().toString();
                        String strEmail = newEmail.getText().toString();

                        Toast.makeText(getApplicationContext(), strNewName + " created",
                                    Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
