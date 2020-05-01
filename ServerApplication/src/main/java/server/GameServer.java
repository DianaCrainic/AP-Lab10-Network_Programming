package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * GameServer class:
 * -an instance of this class will create a ServerSocket running at a specified port.
 * -the server will receive requests (commands) from clients and it will execute them.
 */
public class GameServer {
    private static final int PORT = 8100;

    public GameServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            boolean serverIsRunning = true;
            while (serverIsRunning) {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
            }
        } catch (IOException ex) {
            System.err.println("Ooops..." + ex);
        }
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
    }
}
