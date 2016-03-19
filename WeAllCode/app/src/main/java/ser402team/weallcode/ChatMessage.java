package ser402team.weallcode;

/**
 * @author JoCodes
 * Being used under the Code Project Open Licsense
 *
 * @update Kristel Basra
 */
public class ChatMessage {
    private long id;
    private boolean isMe;
    private String message;
    private String dateTime;

    ChatMessage() {}

    ChatMessage(String msg, Boolean author) {
        this.message = msg;
        this.isMe = author;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public boolean getIsMe() { return isMe; }

    public void setMe(boolean isMe) { this.isMe = isMe; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getDate() { return dateTime; }

    public void setDate(String dateTime) { this.dateTime = dateTime; }
}