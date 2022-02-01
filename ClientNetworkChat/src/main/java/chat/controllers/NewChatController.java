package chat.controllers;

import chat.network.Network;
import command.Command;
import javafx.collections.ObservableList;
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

    public void initialize() {
        listOfUsers.setOnMouseClicked(actionEvent -> deselectPerson(listOfUsers, usersInChat));
        usersInChat.setOnMouseClicked(actionEvent -> deselectPerson(usersInChat, listOfUsers));

    }

    private <T extends ListView, V extends ListView> void deselectPerson(T t, V v) {
        ObservableList<String> item = t.getSelectionModel().getSelectedItems();
        if (item.size() > 0) {
            String select = item.get(0);
            int index = t.getItems().indexOf(select);
            v.getItems().add(select);
            v.getItems().sorted();
            t.getItems().remove(index);
        }
        t.getSelectionModel().clearSelection();
        v.getSelectionModel().clearSelection();

    }


    @FXML
    public void createChat(ActionEvent actionEvent) {
        String titleChat = chatName.getText();
        titleChat.strip();
        if (!titleChat.equals("") && titleChat != null && usersInChat.getItems().size() > 0) {
            if (network.getCollectionOfChats().getChatList().contains(titleChat)) {
                network.getNetworkChat().showNetworkError("Ошибка создания чата", "Чат с таким названием уже существует");
            } else {
                network.sendCommand(Command.createNewChatCommand(titleChat,network.getCollectionOfChats().getUsersIdSet(usersInChat.getItems())));
                network.getNetworkChat().closeNewChatWindow();
            }

        } else network.getNetworkChat().showNetworkError("Нет названия или участников", "Ошибка создания переписки");

    }


    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setListUsers() {
        listOfUsers.setItems(network.getCollectionOfChats().getUserNamesList());
    }
}
