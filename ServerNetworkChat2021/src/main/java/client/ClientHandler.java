package client;

import server.Network;

import java.io.*;
import java.net.Socket;

public class ClientHandler {

    private static final String END_COMMAND ="/end" ;
    private final Network network;
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private static final String AUTH_COMMAND = "/auth";
    private static final String AUTH_OK_COMMAND = "/authOK";
    private static final String MESSAGE_COMMAND = "/mess";
    private String userName;

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
                    socket.close();
                    network.sendInfoMessage("Connection close");
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
            System.out.println("Connection break");

        }finally {
            System.out.println("Connection close");
            network.remove(this);
            network.printListClientHandler();
        }


    }

    private void waitCommand() throws IOException{
        while (true){
            String command=in.readUTF();
            if(command.startsWith(MESSAGE_COMMAND)){
                String message=command.split(" ",2)[1];
                network.broadcastMessage(message, this);

            }

            if(command.startsWith(END_COMMAND)){
                break;
            }
        }
    }

    private void auth() throws IOException {
        while (true) {

            //out.writeUTF("Enter login and password");
            String message = in.readUTF();
            if (message.startsWith(AUTH_COMMAND)) {
                String[] messageArr = message.split(" ");
                if (messageArr.length > 2) {
                    String login = messageArr[1];
                    String pass = messageArr[2];
                    this.userName = network.getAuthService().getUserNameByLoginAndPassword(login, pass);
                    if (userName != null) {
                        network.addClient(this);
                        out.writeUTF(AUTH_OK_COMMAND +" "+ userName);
                        System.out.println("Auth client is success");
                        break;
                    }
                }
            }


        }

    }

    public String getUserName() {
        return userName;
    }

    public void writeMessage(String message) throws IOException {
        out.writeUTF(MESSAGE_COMMAND+message);
    }
}
