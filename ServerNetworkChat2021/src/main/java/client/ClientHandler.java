package client;

import command.Command;
import command.data.*;
import command.data.list.*;
import server.Network;

import java.io.*;
import java.net.Socket;


public class ClientHandler {


    private final Network network;
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String userName;
    private Integer id;

    public ClientHandler(Network network, Socket socket) {
        this.network = network;
        this.socket = socket;
        this.userName = null;
    }

    public void start() {
        new Thread(() -> {
            try {
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                workWithClient();
            } catch (IOException e) {
                System.out.println("Error create input/output stream socket");
            } finally {
                try {
                    network.sendInfoMessage("Connection close", this);
                    System.out.println("Connection with user: " + userName + " was been close");
                    socket.close();
                } catch (IOException e) {
                    System.out.println("error socket close");
                }
            }
        }).start();
    }

    private void workWithClient() {
        try {
            auth();
            waitCommand();
        } catch (IOException e) {
            System.out.println("Connection break" + userName);

        } catch (ClassNotFoundException e) {
            System.out.println("Read object failed");
        } finally {
            System.out.println("Connection close: " + userName);
            network.removeClient(this);
        }


    }

    private void waitCommand() throws IOException, ClassNotFoundException {

        while (true) {
            Command command = (Command) in.readObject();
            TypeOfCommand type = command.getType();

            switch (type) {
                case CREATE_NEW_CHAT: {
                    CreateNewChatCommandData data = (CreateNewChatCommandData) command.getData();
                    network.createNewChat(data.getChatTitle(), data.getUsers(), this);
                    break;

                }
                case CHAT_MESSAGE: {
                    MassageCommandData data=(MassageCommandData) command.getData();
                    System.out.println(data.getChatID()+": "+data.getMessage());
                    network.broadcastMessage(data.getChatID(),data.getMessage(), this);
                    break;
                }
                default: {
                    writeError("waiteCommand error", "Invalid command");
                    System.out.println(command.getType());
                }

            }


        }
    }

    private void auth() throws IOException, ClassNotFoundException {
        while (true) {
            Command command = (Command) in.readObject();
            TypeOfCommand type = command.getType();
            switch (type) {
                case CREATE_NEW_USER: {
                    CreateNewUserCommandData data = (CreateNewUserCommandData) command.getData();
                    String name = data.getName();
                    String login = data.getLogin();
                    String password = data.getPassword();
                    if (network.checkName(name)) {
                        writeError("Создание пользователя", "Пользователь с таким именем уже существует");
                        break;
                    }
                    if (network.checkLogin(login)) {
                        writeError("Создание пользователя", "Пользователь с таким логином уже существует");
                        break;
                    }
                    if(network.createNewAccount(name, login,password)){
                        writeCommand(Command.infoMessageCommand("Пользователь успешно создан"));
                        break;
                    }
                    else writeError("Create account","Ошибка создания пользователя");
                    break;
                }
                case AUTH: {
                    AuthCommandData authCommandData = (AuthCommandData) command.getData();
                    String login = authCommandData.getLogin();
                    String password = authCommandData.getPassword();
                    this.id = network.getDataService().getUserIDByLoginAndPassword(login, password);
                    if (id != null) {
                        this.userName = network.getDataService().getUserNameById(id);
                        network.addClient(this);
                        writeCommand(Command.authOKCommand(id, userName));
                        System.out.println("Auth client " + userName + " was success");
                        network.sendNamesMap(this);
                        network.sendChatMap(this);
                        return;
                    } else {
                        writeError("Auth error", "Invalid login or password");
                        System.out.println("Auth error: invalid login or password");
                    }
                    break;
                }
                default: {
                    writeError("Auth error", "Invalid command");
                }
            }
        }
    }

    public void writeError(String type, String message) throws IOException {
        System.out.println("Error type: " + type);
        System.out.println("Error message: " + message);
        writeCommand(Command.errorMassageCommand(type, message));
    }


    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void writeCommand(Command command) throws IOException {
        out.writeObject(command);
    }
}

