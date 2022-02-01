package server;

import authData.DataBaseDataService;
import authData.DataService;
import client.ClientHandler;
import command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Network {
    private static final Logger logger= LogManager.getLogger(Network.class);
    private final Vector<ClientHandler> clientHandlerList;
    private final DataService dataService;
    private ServerSocket serverSocket;
    private ExecutorService executorService;


    public Network() {
        this.clientHandlerList = new Vector<>();
        this.dataService = new DataBaseDataService();
        this.executorService= Executors.newSingleThreadExecutor();
    }


    public void start(int port) throws IOException {
        dataService.start();
        serverSocket = new ServerSocket(port);
        executorService.submit(()->{
            try {
                logger.info("Сервер успешно запущен");
                connections(serverSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                close();
                executorService.shutdown();
            }
        });


    }

    private void connections(ServerSocket serverSocket) throws IOException {
        while (true) {
            logger.info("Wait new connections...");
            Socket socket = serverSocket.accept();
            logger.info("New client was been connected");
            ClientHandler clientHandler = new ClientHandler(this, socket);
            clientHandler.start();
        }
    }

    public synchronized DataService getDataService() {
        return dataService;
    }

    public synchronized void addClient(ClientHandler clientHandler) {
        clientHandlerList.add(clientHandler);
    }

    public synchronized void removeClient(ClientHandler clientHandler) {
        clientHandlerList.remove(clientHandler);

    }

    public synchronized void broadcastMessage(Integer chatId, String message, ClientHandler clientHandler) throws IOException {
        if (chatId != null) {
            Set<Integer> usersId = dataService.getUsersIDinChatByChatId(chatId);
            for (ClientHandler client : clientHandlerList) {
                for (Integer id : usersId) {
                    if (id.equals(client.getId())) {
                        client.writeCommand(Command.massageCommand(chatId, stringConstructor(clientHandler.getUserName(),message)));
                    }
                }
            }
        }

    }

    public String stringConstructor(String username, String message){
        StringBuilder sb=new StringBuilder();
        String date=String.format("Date:\n %tF %<tT \n", new Date());
        sb.append(date);
        sb.append(username+": "+message);
        sb.append("\n\n");
        return sb.toString();
    }


    public void sendInfoMessage(String infoMessage, ClientHandler clientHandler) throws IOException {
        Command infoCommand = Command.infoMessageCommand(infoMessage);
        clientHandler.writeCommand(infoCommand);
    }


    public synchronized void sendNamesMap(ClientHandler clientHandler) throws IOException {
        Command command = Command.userNamesCommand(dataService.getUserIDAndNames());
        clientHandler.writeCommand(command);
    }

    public synchronized void sendChatMap(ClientHandler clientHandler) throws IOException {
        Command command = Command.chatsOfUserCommand(dataService.getUserChatIdAndNameByUserID(clientHandler.getId()));
        clientHandler.writeCommand(command);
    }

    public synchronized void createNewChat(String chatTitle, Set<Integer> users, ClientHandler clientHandler) throws IOException {
        users.add(clientHandler.getId());
        if (!dataService.createNewChat(chatTitle, users)) {
            clientHandler.writeError("Create chat", "Error create chat");
            logger.error("Ошибка создания чата "+chatTitle+" у пользователя "+clientHandler.getUserName());
            return;
        }
        for (Integer i : users) {
            for (ClientHandler cl : clientHandlerList) {
                if (i.equals(cl.getId())) sendChatMap(cl);
            }
        }
    }
    public boolean checkName(String name) {
        return dataService.checkUserName(name);
    }

    public boolean checkLogin(String login) {
        return dataService.checkUserLogin(login);
    }

    public boolean createNewAccount(String name, String login, String password) throws IOException {
        if (dataService.createNewUser(name, login, password)) {
            for (ClientHandler clientHandler : clientHandlerList) {
                sendNamesMap(clientHandler);
            }
            return true;
        } else return false;
    }

    public void close() {
        dataService.stop();
        try {
            serverSocket.close();
            logger.info("Сервер успешно остановлен");
        } catch (IOException e) {
            logger.info("Ошибка закрытия серверСокет");
        }

    }



}
