package command.data.list;

import java.io.Serializable;

public class MassageCommandData implements Serializable {
    private final Integer chatID;
    private final String message;

    public MassageCommandData(Integer username, String message) {
        this.chatID = username;
        this.message = message;
    }

    public Integer getChatID() {
        return chatID;
    }

    public String getMessage() {
        return message;
    }
}
