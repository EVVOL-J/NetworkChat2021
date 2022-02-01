package chat.network;

import chat.NetworkChat;
import chat.controllers.MainController;
import chat.personData.CollectionOfChatsAndUsers;
import command.Command;
import command.data.TypeOfCommand;
import command.data.list.*;
import javafx.application.Platform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Network {
    private static final int PORT = 8189;
    private static final String ADDRESS = "localhost";
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final int port;
    private final String address;
    private NetworkChat networkChat;
    private CollectionOfChatsAndUsers collectionOfChats;



    public Network(NetworkChat networkChat) {
        this(PORT, ADDRESS);
        this.networkChat = networkChat;
        collectionOfChats =new CollectionOfChatsAndUsers();
    }

    public NetworkChat getNetworkChat() {
        return networkChat;
    }

    Network(int port, String address) {
        this.port = port;
        this.address = address;
    }


    public boolean connect() {
        try {
            Socket socket = new Socket(address, port);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            NetworkChat.showNetworkError("Ошибка подключения к серверу", "Сервер не запущен");
            return false;
        }
        return true;
    }

    public CollectionOfChatsAndUsers getCollectionOfChats() {
        return collectionOfChats;
    }

    public boolean write(Integer chatID, String message) {
        try {
            out.writeObject(Command.massageCommand(chatID,message));
        } catch (IOException e) {
            NetworkChat.showNetworkError("Ошибка отравки сообщения", "Сервер упал");
            return false;
        }
        return true;
    }

    public void read(MainController controller) {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Command command=(Command)in.readObject();
                    TypeOfCommand type = command.getType();
                    switch (type) {
                        case USER_NAMES:{
                            UserSetCommandData data= (UserSetCommandData) command.getData();
                            Platform.runLater(()->controller.reloadUserList(data.getUserNames()));
                            break;
                        }
                        case USER_CHATS:{
                            ChatsOfUserCommandData data=(ChatsOfUserCommandData) command.getData();
                            Platform.runLater(()->controller.reloadChatList(data.getMapChats()));
                            break;
                        }
                        case CHAT_MESSAGE: {
                            MassageCommandData data = (MassageCommandData) command.getData();
                            Platform.runLater(() -> controller.appendMessage(data.getChatID(),data.getMessage()));

                            break;
                        }
                        case ERROR_MESSAGE: {
                            ErrorMassageCommandData data = (ErrorMassageCommandData) command.getData();
                            NetworkChat.showNetworkError(data.getTypeErr(), data.getMessage());
                            break;
                        }
                        case INFO_MESSAGE: {
                            InfoMessageCommandData data = (InfoMessageCommandData) command.getData();
                            networkChat.showNetworkInfoMessage(data.getMessage());
                            break;
                        }
                        default: {
                            NetworkChat.showNetworkError("Ошибка комманд", "Пришла неизвестная команда");
                            break;
                        }
                    }

                }
            } catch (IOException e) {
                NetworkChat.showNetworkError("Потеря соединения", "Сервер упал");
            } catch (ClassNotFoundException e) {
                NetworkChat.showNetworkError("Ошибка команды", "Пришел неизвестный объект");
            }

        });
        thread.setDaemon(true);
        Platform.runLater(() -> thread.start());
    }



    public String sendCommand(Command writeCommand) {
        try {
            out.writeObject(writeCommand);
            Command readCommand = (Command) in.readObject();
            TypeOfCommand type = readCommand.getType();
            switch (type) {
                case AUTH_OK: {
                    AuthOkCommandData data = (AuthOkCommandData) readCommand.getData();
                    return data.getUsername();
                }
                case ERROR_MESSAGE: {
                    ErrorMassageCommandData data = (ErrorMassageCommandData) readCommand.getData();
                    networkChat.showNetworkError(data.getTypeErr(), data.getMessage());
                    return null;
                }
                case INFO_MESSAGE:{
                    InfoMessageCommandData data=(InfoMessageCommandData) readCommand.getData();
                    networkChat.showNetworkInfoMessage(data.getMessage());
                    return null;
                }
                default: {
                    networkChat.showNetworkError("Ошибка комманд", "Пришла неизвестная команда");
                    return null;
                }
            }


        } catch (IOException e) {
            NetworkChat.showNetworkError("Ошибка отравки сообщения", "Сервер упал");
            return null;

        } catch (ClassNotFoundException e) {
            NetworkChat.showNetworkError("Пришел неизвестный объект", "Ошибка принятия сообщения");
            return null;
        }
    }



  }
