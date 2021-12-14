package commandData.list;

import java.io.Serializable;
import java.util.List;

public class GropeMessageCommandData implements Serializable {
    private final String titleChat;
    private final List<String> username;

    public GropeMessageCommandData(String titleChat, List<String> username) {
        this.titleChat = titleChat;
        this.username = username;
    }

    public String getTitleChat() {
        return titleChat;
    }

    public List<String> getUsername() {
        return username;
    }
}
