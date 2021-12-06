

import server.Network;

import java.io.IOException;

public class ServerApp {
    private static final int PORT = 8189;

    public static void main(String[] args) {
        Network network = new Network();
        try {
            int port=PORT;
            if (args.length != 0){
                port=Integer.parseInt(args[0]);
            }
            network.start(port);
        } catch (IOException e) {
            System.out.println("Ошибка создания сервера");
            e.printStackTrace();
        }
    }
}
