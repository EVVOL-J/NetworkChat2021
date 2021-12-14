package chat.controllers;


import chat.personData.CollectionUsers;
import chat.network.Network;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class MainController {
    private CollectionUsers collectionUsers;
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
        collectionUsers =new CollectionUsers();
        textField.setOnAction(actionEvent -> sendMessage());
        sendButton.setOnAction(actionEvent -> sendMessage());
        userList.setItems(collectionUsers.getPersonList());
    }

    public void sendMessage() {
        String message=textField.getText()+"\n";
        appendMessage(message);
        network.write(message);
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
}

