package command.data.list;

import java.io.Serializable;
import java.util.Set;

public class CreateNewChatCommandData implements Serializable {

    private final String chatTitle;
    private final Set<Integer> users;

    public CreateNewChatCommandData(String chatTitle, Set<Integer> users) {
        this.chatTitle = chatTitle;
        this.users = users;
    }

    public String getChatTitle() {
        return chatTitle;
    }

    public Set<Integer> getUsers() {
        return users;
    }
}
