package ser402team.weallcode;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ChatAvailability extends AppCompatActivity {

    public static final String FIREBASE_URL = "https://weallcode-chat.firebaseio.com/";
    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    public static final String FRIEND_USERNAME = "ser402team.weallcode.FRIEND_USERNAME";
    public static String myUsername = "";
    private static String myFriendsUsername = "";
    private Firebase REF;
    private ListView friendsList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;
    private ValueEventListener mConnectedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_availability);

        Bundle bund = getIntent().getExtras();
        myUsername = bund.getString(MY_USERNAME);

        //connect to firebase
        Firebase.setAndroidContext(this);
        REF = new Firebase(FIREBASE_URL);

        //put conversations with friends on the listview
        populateFriendsList();

        //add friend
        ImageView addButton = (ImageView) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchNewFriend();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(ChatAvailability.this, android.R.layout.simple_list_item_1, list);

        //get list container to populate friends to, and make them clickable
        friendsList = (ListView) findViewById(R.id.chatListContainer);
        friendsList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                myFriendsUsername = name;
                goToConversation();
            }
        });

        //set adapter
        friendsList.setAdapter(adapter);

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                friendsList.setSelection(adapter.getCount() - 1);
            }
        });

        //indication of connection status ***************** (can be deleted later) ****************
        mConnectedListener = REF.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(ChatAvailability.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatAvailability.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
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

    protected void goToConversation() {
        Intent intent = new Intent(ChatAvailability.this, ChatActivity.class);
        intent.putExtra(MY_USERNAME, myUsername);
        intent.putExtra(FRIEND_USERNAME, myFriendsUsername);
        startActivity(intent);
    }

    protected void goToSearchNewFriend() {
        Intent intent = new Intent(ChatAvailability.this, SearchFriendActivity.class);
        intent.putExtra(MY_USERNAME, myUsername);
        startActivity(intent);
    }

    protected void populateFriendsList() {

        REF.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userIdentity = "-" + myUsername.toLowerCase() + "-";

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey().toString();

                    if (key.contains(userIdentity)) {
                        String friend = getFriendsName(key, myUsername);
                        list.add(friend);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //error happened
            }
        });
    }

    protected String getFriendsName(String chatroomName, String me) {
        //endIndex is the index of the tack symbol of the chatroom
        //   remove that and parse out the usernames
        int endIndex = chatroomName.indexOf("-", 1);
        String firstUser = chatroomName.substring(1, endIndex);
        String secondUser = chatroomName.substring(endIndex + 1, chatroomName.length() - 1);

        if(secondUser.equals(me.toLowerCase())) {
            return firstUser;
        }

        return secondUser;
    }
}
