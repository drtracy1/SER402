package ser402team.weallcode;

import java.util.HashMap;

/**
 * Created by KBasra on 2/12/16.
 */
public class usersInformation {

    String username = "";
    String password = "";
    String email = "";
    int points = 0;

    usersInformation(String un, String pw, String em) {
        this.username = un;
        this.password = pw;
        this.email = em;
    }


    public void setUsername(String un) {
        this.username = un;
    }
    public void setPassword(String pw) {
        this.password = pw;
    }
    public void setEmail(String em) {
        this.email = em;
    }
    public void addPoints(int num) {
        this.points += num;
    }

    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }
    public int getPoints() {
        return this.points;
    }

}
