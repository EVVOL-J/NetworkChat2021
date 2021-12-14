package chat.personData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CollectionUsers {
    private ObservableList<User> userList;

    public CollectionUsers() {
        this.userList = FXCollections.observableArrayList();
        userList.add(new User("Name1","pass1","login1"));
        userList.add(new User("Name2","pass2","login2"));
        userList.add(new User("Name3","pass3","login3"));

    }

    public ObservableList getPersonList() {
        return userList;
    }
}

