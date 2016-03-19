package ser402team.weallcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author JoCodes
 * Being used under the Code Project Open Licsense
 *
 * @update Kristel Basra 3/2016
 * Pushes messages up to Firebase db
 *
 */

public class ChatActivity extends AppCompatActivity {

    public static final String FIREBASE_URL = "https://weallcode-chat.firebaseio.com/";
    public static final String FRIEND_USERNAME = "ser402team.weallcode.FRIEND_USERNAME";
    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private String friendUsername = "";
    private String myUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //initControls_Old();

        //connect to firebase
        Firebase.setAndroidContext(this);
        Firebase REF = new Firebase(FIREBASE_URL).child("chat");

        //get username from SearchFriendActivity
        Bundle bund = getIntent().getExtras();
        friendUsername = bund.getString(FRIEND_USERNAME);
        myUsername = bund.getString(MY_USERNAME);


        initControls(REF);
    }

    protected void initControls(final Firebase REF) {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer); //allow to display all messages as they come
        messageET = (EditText) findViewById(R.id.messageEdit); //user types message before sending
        sendBtn = (Button) findViewById(R.id.chatSendButton); //send button

        TextView meLabel = (TextView) findViewById(R.id.meLbl);  //users side of the msg container
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel); //friend's side

        //chat room setup
        companionLabel.setText(friendUsername); //set friend username on chat room page
        meLabel.setText(myUsername); //set username of the user currently logged in
        loadDummyHistory();


        //send message if there is text entered
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                //create our ChatMessage model object
                ChatMessage chatMessage = new ChatMessage(messageText, true);
                //create a new, auto-generated child of that chat location, and save our chat data there
                REF.push().setValue(chatMessage);
                messageET.setText("");

                displayMessage(chatMessage);

            }
        });


    }


    private void initControls_Old() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("My Buddy");// Hard Coded
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                messageET.setText("");

                displayMessage(chatMessage);
            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory_Old(){

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }
}