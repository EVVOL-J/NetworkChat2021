package chat.controllers;

import chat.NetworkChat;
import chat.network.Network;
import command.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AuthController {
    private Network network;

    @FXML
    private PasswordField password;
    @FXML
    private TextField login;

    @FXML
    public void sendAuthCommand(ActionEvent actionEvent) {
        if(password.getText()==null||login.getText()==null||password.getText().equals("")||login.getText().equals("")){
            NetworkChat.showNetworkError("Login and password should be not empty!", "Auth error");
            return;
        }
        String username=network.sendCommand(Command.authCommand(login.getText().strip(),password.getText()));
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


    public void createAccount(MouseEvent mouseEvent) {
        try {
            network.getNetworkChat().initAndShowNewUserWindow();
        } catch (IOException e) {
            System.out.println("Ошибка создания окна нового пользователя");
        }
    }
}
