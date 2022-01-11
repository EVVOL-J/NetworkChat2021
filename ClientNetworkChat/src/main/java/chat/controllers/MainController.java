package chat.controllers;


import chat.network.Network;
import command.data.Chat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;


public class MainController {

    private Network network;

    @FXML
    private Label userInfo;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;

    @FXML
    private ListView userList;

    @FXML
    private Button sendButton;





    public void initialize() {
        textField.setOnAction(actionEvent -> sendMessage());
        sendButton.setOnAction(actionEvent -> sendMessage());

    }

    public void sendMessage() {
        String message=textField.getText()+"\n";
       // userList.getSelectionModel().getSelectedItem();
        //appendMessage(message);
        network.write(null, message);
    }

    public void appendMessage(String message){
        textArea.appendText(message);
        textField.clear();
    }

    @FXML
    public void newInfo() {
        userInfo.setText(String.valueOf(userList.getSelectionModel().getSelectedItems()));
    }

    public void setNetwork(Network network) {
        this.network = network;
    }


    public void setUserInfo(String userMessage) {
        userInfo.setText(userMessage);
    }

    public void reloadChatList(List<Chat> chats){
        network.getCollectionOfChats().insertChats(chats);
        userList.setItems(network.getCollectionOfChats().getPersonList());}

    public void reloadUserList(Set<String> userNames){
        network.getCollectionOfChats().setUserNames(userNames);
    }

    public void createNewChat(ActionEvent actionEvent) {
        try {
            network.getNetworkChat().initAndShowNewChatWindow();
        } catch (IOException e) {
            System.out.println("Ошибка создания chat окна");
        }
    }


}

