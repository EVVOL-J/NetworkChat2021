package chat.controllers;

import chat.NetworkChat;
import chat.network.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthController {
    private Network network;

    @FXML
    private PasswordField password;
    @FXML
    private TextField login;

    @FXML
    public void sendAuthCommand(ActionEvent actionEvent) {
        if(password==null||login==null){
            NetworkChat.showNetworkError("Login and password should be not empty!", "Auth error");
            return;
        }
        String username=network.sendAuthCommand(login.getText().strip(),password.getText());
        if(username!=null){
            try {
                network.getNetworkChat().initAndShowMainWindow(username);

            } catch (IOException e) {
                System.out.println("Ошибка создания main окна");
            }
        } //else NetworkChat.showNetworkError("Incorrect login or password", "Auth error");

    }

    public void setNetwork(Network network) {
        this.network = network;
    }

}
