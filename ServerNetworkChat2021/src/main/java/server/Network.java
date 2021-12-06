package server;

import authData.AuthService;
import authData.BaseAuthService;
import client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Network {
    private final List<ClientHandler> clientHandlerList;
    private final AuthService authService;

    public Network(){
        this.clientHandlerList=new ArrayList<>();
        this.authService=new BaseAuthService();
    }

    public void start(int port) throws IOException {
        ServerSocket serverSocket=new ServerSocket(port);
        connections(serverSocket);

    }

    private void connections(ServerSocket serverSocket) throws IOException {
        while (true){
            System.out.println("Wait new connections...");
            Socket socket= serverSocket.accept();
            System.out.println("New client was been connected");
            ClientHandler clientHandler =new ClientHandler(this, socket);
            clientHandler.start();
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void addClient(ClientHandler clientHandler) {
        clientHandlerList.add(clientHandler);
    }

    public void remove(ClientHandler clientHandler) {
        clientHandlerList.remove(clientHandler);

    }

    public void broadcastMessage(String message, ClientHandler clientHandler) throws IOException {
        for (ClientHandler client:clientHandlerList){
                if(client.getUserName().equals(clientHandler.getUserName())){
                    client.writeMessage("Me: "+message);
                }
                else client.writeMessage(clientHandler.getUserName()+": "+message);
            }

    }
}
