package ser402team.weallcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    private Firebase REF;
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory = new ArrayList<ChatMessage>();
    private String friendUsername = "";
    private String myUsername = "";
    private String chatroom = "";
    private ValueEventListener mConnectedListener;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //get username from SearchFriendActivity
        Bundle bund = getIntent().getExtras();
        friendUsername = bund.getString(FRIEND_USERNAME);
        myUsername = bund.getString(MY_USERNAME);

        chatroom = createChatRoomName(friendUsername, myUsername);

        //connect to firebase
        Firebase.setAndroidContext(this);
        REF = new Firebase(FIREBASE_URL).child(chatroom);

        initControls();
    }


    @Override
    public void onStart() {
        super.onStart();

        //indication of connection status ***************** (can be deleted later) ****************
        mConnectedListener = REF.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(ChatActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        REF.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
    }

    protected void initControls() {

        setLabels();

        //set up the adapter to make the text messages look a certain way
        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        adapter.setMyUsername(myUsername);
        messagesContainer.setAdapter(adapter);


        REF.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {

                if (dataSnapshot.getValue() != null) {
                    String str = dataSnapshot.getValue().toString();
                    String usr, msg, dte;

                    try {
                        JSONObject jObj = new JSONObject(str.trim());
                        usr = jObj.getString("author");
                        msg = jObj.getString("textMessage");
                        dte = jObj.getString("date");

                        ChatMessage chatMsg = new ChatMessage(usr, msg, dte);
                        //chatHistory.add(count, chatMsg);
                        //count++;

                        displayMessage(chatMsg);

                    } catch (JSONException jex) {
                        jex.printStackTrace();
                    }
                } else {
                    System.out.println("DATASNAPSHOT IS NULL");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //no need att
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //no need att
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //no need att
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //error
            }
        });

        //send message if there is text entered
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                String formatting = "MMM-d-yyyy--HH-mm-ss"; //JSON objects do not like commas or colons
                SimpleDateFormat sdf = new SimpleDateFormat(formatting, Locale.US);

                //create our ChatMessage model object
                ChatMessage newChatMessage = new ChatMessage(myUsername, messageText,
                        sdf.format(new Date()));

                //create a new, auto-generated child of that chat location, and save our chat data there
                REF.push().setValue(newChatMessage);
                messageET.setText(""); //reset edit text line
            }
        });
    }

    public void setLabels() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer); //allow to display all messages as they come
        messageET = (EditText) findViewById(R.id.messageEdit); //user types message before sending
        sendBtn = (Button) findViewById(R.id.chatSendButton); //send button

        TextView meLabel = (TextView) findViewById(R.id.meLbl);  //users side of the msg container
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel); //friend's side

        //chat room setup
        companionLabel.setText(friendUsername); //set friend username on chat room page
        meLabel.setText(myUsername); //set username of the user currently logged in
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() { messagesContainer.setSelection(messagesContainer.getCount() - 1); }

    private String createChatRoomName(String str1, String str2) {
        String chatroom = "";

        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        if (str2.compareToIgnoreCase(str1) < 0) {
            chatroom = str2 + str1;
        } else {
            chatroom = str1 + str2;
        }

        return chatroom;
    }
}