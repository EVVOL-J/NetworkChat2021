package chat.network;

import chat.NetworkChat;
import chat.controllers.MainController;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {
    private static final int PORT = 8189;
    private static final String ADDRESS = "localhost";
    private DataInputStream in;
    private DataOutputStream out;
    private final int port;
    private final String address;
    private static final String AUTH_COMMAND = "/auth";
    private static final String AUTH_OK_COMMAND = "/authOK";
    private static final String MESSAGE_COMMAND = "/mess";
    private NetworkChat networkChat;


    public Network(NetworkChat networkChat) {
        this(PORT, ADDRESS);
        this.networkChat = networkChat;
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
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            NetworkChat.showNetworkError("Ошибка подключения к серверу", "Сервер не запущен");
            return false;
        }
        return true;
    }

    public boolean write(String message) {
        try {
            out.writeUTF(message);
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
                    String message = in.readUTF();
                    Platform.runLater(() -> controller.appendMessage(message));
                }
            } catch (IOException e) {
                NetworkChat.showNetworkError("Потеря соединения", "Сервер упал");
            }

        });
        thread.setDaemon(true);
        thread.start();
    }

    public String sendAuthCommand(String login, String password){
        write(AUTH_COMMAND+" "+login+" "+password);
        try {
            String message=in.readUTF();
            System.out.println(message);
            if(message.startsWith(AUTH_OK_COMMAND)){
                return message.split(" ")[1];
            }
            else return null;
        } catch (IOException e) {
            NetworkChat.showNetworkError("Ошибка отравки сообщения", "Сервер упал");
            return null;
        }

    }

}
