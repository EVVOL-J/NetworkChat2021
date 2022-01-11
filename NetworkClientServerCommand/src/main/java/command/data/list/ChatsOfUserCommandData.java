package command.data.list;

import command.data.Chat;

import java.io.Serializable;
import java.util.List;

public class ChatsOfUserCommandData implements Serializable {
    private final List<Chat> chatsOfUser;

    public ChatsOfUserCommandData(List<Chat> chatsOfUser) {
        this.chatsOfUser=chatsOfUser;
    }

    public List<Chat> getOnlineUserName() {
        return chatsOfUser;
    }
}
