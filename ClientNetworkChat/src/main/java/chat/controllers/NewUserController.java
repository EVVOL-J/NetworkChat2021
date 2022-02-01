package chat.controllers;

import chat.NetworkChat;
import chat.network.Network;
import command.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewUserController {
    private Network network;
    @FXML
    private TextField name;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private TextField rePassword;


    public void createNewUser(ActionEvent actionEvent) {
        String userName = name.getText().strip();
        String userLogin = login.getText().strip();
        String userPassword = password.getText();
        String userRePassword = rePassword.getText();
        if(check(userName, userLogin, userPassword, userRePassword))
        {
            network.sendCommand(Command.createNewUserCommand(userName,userLogin,userPassword));
            network.getNetworkChat().closeNewUserWindow();
        }
    }

    private boolean check(String userName, String userLogin, String userPassword, String userRePassword) {
        if (userName == null || userLogin == null || userPassword == null || userRePassword == null ||
                userName.isEmpty() || userLogin.isEmpty() || userPassword.isEmpty() || userRePassword.isEmpty()) {
            NetworkChat.showNetworkError("Поля не должны быть пустыми", "Ошибка создания пользователя");
            return false;
        }
        if (!userPassword.equals(userRePassword)) {
            NetworkChat.showNetworkError("Пароли не совпадают", "Ошибка создания пользователя");
            return false;
        }
        return true;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }
}
