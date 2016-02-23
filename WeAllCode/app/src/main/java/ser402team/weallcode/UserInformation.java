package ser402team.weallcode;

/**
 * Created by KBasra on 2/23/16.
 *
 * Use this class to upload values to the firebase only
 */
public class UserInformation {

    private String username = "";
    private String password = "";
    private String email = "";
    private int points = 0;

    UserInformation() {}

    UserInformation(String un, String em, String pw) {
        this.username = un;
        this.password = pw;
        this.email = em;
    }

    public void setPassword(String pw) { this.password = pw; }

    public void setEmail(String em) { this.email = em; }

    public void addPoints(int num) { this.points += num; }

    public void setUsername(String un) { this.username = un; }

    public String getUsername() { return this.username; }

    public String getPassword() { return this.password; }

    public String getEmail() { return this.email; }

    public int getPoints() { return this.points; }
}
