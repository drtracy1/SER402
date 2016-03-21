package ser402team.weallcode;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
    private ArrayList<ChatMessage> chatHistory;
    private String friendUsername = "";
    private String myUsername = "";
    private String chatroom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //initControls_Old();

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

    protected void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer); //allow to display all messages as they come
        messageET = (EditText) findViewById(R.id.messageEdit); //user types message before sending
        sendBtn = (Button) findViewById(R.id.chatSendButton); //send button

        TextView meLabel = (TextView) findViewById(R.id.meLbl);  //users side of the msg container
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel); //friend's side

        //chat room setup
        companionLabel.setText(friendUsername); //set friend username on chat room page
        meLabel.setText(myUsername); //set username of the user currently logged in

        //LOAD INITIAL CONVERSATION BETWEEN BOTH PARTIES
        DownloadTextMessages dtm = new DownloadTextMessages();
        dtm.execute();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDummyHistory();
            }
        }, 2000);


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

                displayMessage(newChatMessage);
            }
        });


    }

/*
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

                displayMessage_Old(chatMessage);
            }
        });
    }
    */

            public void displayMessage(ChatMessage message) {
                adapter.add(message);
                adapter.notifyDataSetChanged();
                scroll();
            }

            private void scroll() {
                messagesContainer.setSelection(messagesContainer.getCount() - 1);
            }

    /*
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
            displayMessage_Old(message);
        }
    }
*/

    private void loadDummyHistory() {

        //set up the adapter for the chatroom
        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        adapter.setMyUsername(myUsername);
        messagesContainer.setAdapter(adapter);

        //add messages from the chatHistory list (getting data from FB is backwards)
        for (int i = chatHistory.size() - 1; i >= 0; i--) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

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

    private class DownloadTextMessages extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            //just get the data from firebase into the chatHistory list
            REF.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<ChatMessage> chat_History = new ArrayList<ChatMessage>();

                    if (dataSnapshot.getValue() != null) {
                        //get value, turn it into JSONObject, then separate values into string
                        String str = dataSnapshot.getValue().toString();
                        String usr, msg, dte;
                        try {
                            JSONObject jObj = new JSONObject(str.trim());
                            Iterator<?> keys = jObj.keys();

                            //first get the object from the key, then break apart what is under the key,
                            //      get each important element from the skinny object
                            while (keys.hasNext()) {
                                String key = (String) keys.next();
                                Object obj = jObj.get(key);
                                JSONObject skinnyObj = new JSONObject(obj.toString().trim());

                                usr = skinnyObj.getString("author");
                                msg = skinnyObj.getString("textMessage");
                                dte = skinnyObj.getString("date");

                                ChatMessage chatMsg = new ChatMessage(usr, msg, dte);
                                chat_History.add(chatMsg);
                            }
                            chatHistory = new ArrayList<ChatMessage>(chat_History);
                            System.out.println("First: " + chatHistory);
                        } catch (JSONException jex) {
                            jex.printStackTrace();
                        }
                    } else {
                        //nothing in the database to retrieve
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("CANCELLED");
                }
            });
            return "Done";
        }
    }
}