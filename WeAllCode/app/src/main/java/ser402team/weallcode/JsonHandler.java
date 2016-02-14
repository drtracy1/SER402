package ser402team.weallcode;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by KBasra on 2/12/16.
 */
public class JsonHandler {

    private static final String usernameKey = "Username";
    private static final String passwordKey = "Password";
    private static final String emailKey = "Email";
    private static final String pointsKey = "Points";
    private static int indexWhereUsernameIs = 0;

    public void createUsername(Context context, String filename, String username, String password, String email) {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        FileInputStream in;
        FileOutputStream out;

        try {
            File file = context.getFileStreamPath(filename);

            //read from file only if it exists
            if(file.exists()) {
                //open file and get contents before writing out, then close stream
                in = context.openFileInput(filename);
                int size = in.available();
                byte[] buffer = new byte[size];
                in.read(buffer);
                in.close();

                //put contents in string then into JSONArray format
                String strBuffer = new String(buffer, "UTF-8");
                array = new JSONArray(strBuffer);
            }

            //create object and add to array, then parse to string
            object.put(usernameKey, username);
            object.put(passwordKey, password);
            object.put(emailKey, email);
            object.put(pointsKey, new Integer(0));
            array.put(object);
            String stringify = array.toString();

            //writes over previous file
            out = context.openFileOutput(filename, context.MODE_PRIVATE);
            out.write(stringify.getBytes());
            out.close();

        } catch (JSONException jex) {
            jex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean authenticateLogin(Context context, String filename, String strUsername, String strPassword) {
        boolean allowLogin = false;
        String uppercaseUsername = strUsername.toUpperCase();
        FileInputStream in;

        try {
            File file = context.getFileStreamPath(filename);

            //read from file only if it exists
            if (file.exists()) {
                //open file and get contents before writing out, then close stream
                in = context.openFileInput(filename);
                int size = in.available();
                byte[] buffer = new byte[size];
                in.read(buffer);
                in.close();

                //turn buffer from file into string
                String strFromFile = new String(buffer, "UTF-8");

                //find out if username and password match or not
                indexWhereUsernameIs = findUsernameIndex(strFromFile, uppercaseUsername);

                //if username was found check password
                if(indexWhereUsernameIs != -1) {
                    if (passwordCorrect(strFromFile, strPassword)){
                        //allow user to log in
                        allowLogin = true;
                    }
                    else {
                        //ask to reenter username and password
                        allowLogin =  false;
                    }
                }
                //username not found
                else {
                    //ask to reenter username and password
                    allowLogin = false;
                }
            }
            //file does not exist, so do not allow user to log in
            else {
                allowLogin = false;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return allowLogin;
    }

    //find if the username exists, if so get the index number
    public int findUsernameIndex(String strFromFile, String strUsername) {
        try {
            JSONArray array = new JSONArray(strFromFile);

            //get each JSON object in JSON array
            for(int i = 0; i < array.length(); i++)
            {
                JSONObject obj = array.getJSONObject(i);
                String str = obj.getString(usernameKey);

                //looking for username entered by user with JSON records
                if(str.compareToIgnoreCase(strUsername) == 0) {
                    System.out.println("Found user in json file: "+strUsername);
                    //return the index where the username exists
                    return i;
                }
            }

        } catch (JSONException je) {
            je.printStackTrace();
        }
        System.out.println("Did not find user in json file");
        return -1;
    }

    //see if the username matches the password entered
    public boolean passwordCorrect(String strFromFile, String strPassword) {

        boolean correctPassword = false;

        try {
            //get object with username found
            JSONArray array = new JSONArray(strFromFile);
            JSONObject obj = array.getJSONObject(indexWhereUsernameIs);
            String str = obj.getString(passwordKey);

            //password entered must match password on record exactly
            if(str.equals(strPassword)) {
                System.out.println("Password matches Username in json");
                correctPassword =  true;
            }
            else {
                System.out.println("Password did not match username in json");
                correctPassword = false;
            }

        } catch (JSONException je) {
            je.printStackTrace();
        }
        return correctPassword;
    }

    public boolean usernameExists(Context context, String filename, String username) {
        boolean foundUsername = false;
        String uppercaseUsername = username.toUpperCase();
        FileInputStream in;

        try {
            File file = context.getFileStreamPath(filename);

            //read from file only if it exists
            if (file.exists()) {
                //open file and get contents before writing out, then close stream
                in = context.openFileInput(filename);
                int size = in.available();
                byte[] buffer = new byte[size];
                in.read(buffer);
                in.close();

                //turn buffer from file into string
                String strFromFile = new String(buffer, "UTF-8");

                //get index of the username if exists, if it does not exists method will return -1
                indexWhereUsernameIs = findUsernameIndex(strFromFile, uppercaseUsername);

                if(indexWhereUsernameIs != -1) {
                    //username exists, ask user to try another username
                    foundUsername = true;
                }
            }
            else {
                foundUsername = false;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return foundUsername;
    }
}
