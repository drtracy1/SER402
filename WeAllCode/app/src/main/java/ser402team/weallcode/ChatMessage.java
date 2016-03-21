package ser402team.weallcode;

/**
 * @author JoCodes
 * Being used under the Code Project Open Licsense
 *
 * @update Kristel Basra 3/2016
 */
public class ChatMessage {

    private String author;
    private String textMessage;
    private String dateTime;

    ChatMessage(String usr, String msg, String date) {
        this.author = usr;
        this.textMessage = msg;
        this.dateTime = date;
    }

    public String getAuthor() { return author; }
    public String getTextMessage() { return textMessage; }
    public String getDate() { return dateTime; }
}