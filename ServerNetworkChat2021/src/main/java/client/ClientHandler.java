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
            System.out.println("Connection break");

        } catch (ClassNotFoundException e) {
            System.out.println("Read object failed");
        } finally {
            System.out.println("Connection close");
            network.remove(this);
            network.printListClientHandler();
        }


    }

    private void waitCommand() throws IOException, ClassNotFoundException {

        while (true) {
            Command command = (Command) in.readObject();
            TypeOfCommand type = command.getType();

            switch (type) {
                case PRIVATE_MESSAGE: {
                    PrivateMassageCommandData data = (PrivateMassageCommandData) command.getData();
                    network.broadcastMessage(data.getUsername(),data.getMessage(),this);

                    break;
                }
                default: {
                    writeError("Auth error", "Invalid command");
                }

            }


            }
        }

        private void auth () throws IOException, ClassNotFoundException {
            while (true) {


                Command command = (Command) in.readObject();
                TypeOfCommand type = command.getType();


                switch (type) {
                    case AUTH: {
                        AuthCommandData authCommandData = (AuthCommandData) command.getData();
                        String login = authCommandData.getLogin();
                        String password = authCommandData.getPassword();
                        this.userName = network.getAuthService().getUserNameByLoginAndPassword(login, password);
                        if (userName != null) {
                            network.addClient(this);
                            writeCommand(Command.authOKCommand(userName));
                            System.out.println("Auth client " + userName + " was success");
                            network.sendNames(this);
                            //network.broadcastChats();
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

        private void writeError (String type, String message) throws IOException {
            System.out.println("Error type: "+type);
            System.out.println("Error message: "+message);
            writeCommand(Command.errorMassageCommand(type, message));
        }


        public String getUserName () {
            return userName;
        }

        public void writeCommand (Command command) throws IOException {
            out.writeObject(command);
        }
    }

