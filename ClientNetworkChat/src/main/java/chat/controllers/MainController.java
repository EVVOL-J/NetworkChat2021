package chat.controllers;


import chat.history.History;
import chat.network.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainController {

    private Network network;
    private History history;
    private String selectChat = "";

    @FXML
    private Label userInfo;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;

    @FXML
    private ListView chatList;

    @FXML
    private Button sendButton;


    public void initialize() {

        textField.setOnAction(actionEvent -> sendMessage());
        sendButton.setOnAction(actionEvent -> sendMessage());
    }

    public void sendMessage() {
        String message = textField.getText();
        Integer id = null;
        message.strip();
        List<String> chat = chatList.getSelectionModel().getSelectedItems();
        if (!chat.isEmpty()) {
            if (message != null || !message.isEmpty())
                id = network.getCollectionOfChats().getChatIDByTitle(chat.get(0));
            if (id != null)
                network.write(id, message + '\n');
        }
        textField.clear();
    }

    public void appendMessage(Integer chatID, String message) {
        if (network.getCollectionOfChats().getChatNameById(chatID).equals(selectChat))
            textArea.appendText(message);
        history.writeMessageToChat(network.getCollectionOfChats().getChatNameById(chatID), message);
    }

    @FXML
    public void newInfo() {
        String selectedItem = chatList.getSelectionModel().getSelectedItems().get(0).toString();
        if (!selectedItem.equals(selectChat)) {
            selectChat = selectedItem;
            ArrayList<String> chatHistory = history.readLastOneHundredMessages(selectedItem);
            textArea.clear();
            for (String message : chatHistory) {
                textArea.appendText(message);
            }
            userInfo.setText(selectedItem);
        }
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setHistory(String username) {
        this.history = new History(username);
    }

    public void setUserInfo(String userMessage) {
        userInfo.setText(userMessage);
    }

    public void reloadChatList(Map<Integer, String> chats) {
        network.getCollectionOfChats().insertChats(chats);
        chatList.setItems(network.getCollectionOfChats().getChatList());
        chatList.getSelectionModel().select(0);
        newInfo();
    }

    public void reloadUserList(Map<Integer, String> userNames) {
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

