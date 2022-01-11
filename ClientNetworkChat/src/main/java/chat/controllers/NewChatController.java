package chat.controllers;

import chat.network.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class NewChatController {

    private Network network;
    @FXML
    public TextField chatName;
    @FXML
    public ListView usersInChat;
    @FXML
    public ListView listOfUsers;


    @FXML
    public void createChat(ActionEvent actionEvent) {
    }


    public void setNetwork(Network network) {
        this.network=network;
    }
}
