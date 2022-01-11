package server;

import authData.AuthService;
import authData.BaseAuthService;
import client.ClientHandler;
import command.Command;
import command.data.Chat;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Network {
    private final List<ClientHandler> clientHandlerList;
    private final AuthService authService;

    public Network() {
        this.clientHandlerList = new ArrayList<>();
        this.authService = new BaseAuthService();
    }

    public void start(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        connections(serverSocket);

    }

    private void connections(ServerSocket serverSocket) throws IOException {
        while (true) {
            System.out.println("Wait new connections...");
            Socket socket = serverSocket.accept();
            System.out.println("New client was been connected");
            ClientHandler clientHandler = new ClientHandler(this, socket);
            clientHandler.start();
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized void addClient(ClientHandler clientHandler) {
        clientHandlerList.add(clientHandler);
    }

    public synchronized void remove(ClientHandler clientHandler) {
        clientHandlerList.remove(clientHandler);

    }

    public synchronized void broadcastMessage(String username, String message, ClientHandler clientHandler) throws IOException {
        for (ClientHandler client : clientHandlerList) {
            if (username == null)
                client.writeCommand(Command.privateMassageCommand(clientHandler.getUserName(), message));
            else if (client.getUserName().equals(username))
                client.writeCommand(Command.privateMassageCommand(clientHandler.getUserName(), message));
        }

    }

    public synchronized void printListClientHandler() {
        for (ClientHandler clientHandler : clientHandlerList)
            System.out.println(clientHandler.getUserName());
    }

    public void sendInfoMessage(String connection_close, ClientHandler clientHandler) throws IOException {
        Command infoCommand = Command.infoMessageCommand(connection_close);
        clientHandler.writeCommand(infoCommand);

    }

    public synchronized void broadcastChats() throws IOException {
        List<Chat> chats = new ArrayList<>();
        for (ClientHandler clientHandler : clientHandlerList) {
            chats.add(new Chat(clientHandler.getUserName(), true));
        }
        Command chatsInfo = Command.chatsOfUserCommand(chats);
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.writeCommand(chatsInfo);
        }


    }

    public synchronized void sendNames(ClientHandler clientHandler) throws IOException {
        Command command=Command.userNamesCommand(authService.getUserNames());
        clientHandler.writeCommand(command);
    }
}
