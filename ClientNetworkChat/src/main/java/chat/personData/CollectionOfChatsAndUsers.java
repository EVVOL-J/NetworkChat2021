package chat.personData;

import command.data.Chat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Set;


public class CollectionOfChatsAndUsers {
    private ObservableList<Chat> chatsList;
    private ObservableList<String> userNames;

    public CollectionOfChatsAndUsers() {
        this.chatsList = FXCollections.observableArrayList();
        this.userNames=FXCollections.observableArrayList();
    }

    public void insertChats(List<Chat> user){
        chatsList.clear();
        chatsList.addAll(user);
    }

    public void setUserNames(Set<String> userNames) {
        this.userNames.clear();
        this.userNames.addAll(userNames);
    }

    public ObservableList<String> getUserNames() {
        return userNames;
    }

    public ObservableList getPersonList() {
        return chatsList;
    }
}

