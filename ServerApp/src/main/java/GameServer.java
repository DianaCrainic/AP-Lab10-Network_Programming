import shell.*;
import shell.commands.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * GameServer class:
 * -an instance of this class will create a ServerSocket running at a specified port.
 * -the server will receive requests (commands) from clients and it will execute them.
 */
public class GameServer {
    private final int PORT = 8100;

    public GameServer() {
        Shell shell = getShell();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                new ClientThread(socket, shell).start();
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    private static Shell getShell() {
        Shell shell = new Shell();
        shell.addCommand(new SetName("set_name", "name"));
        shell.addCommand(new CreateGame("create_game"));
        shell.addCommand(new JoinGame("join_game", "id"));
        shell.addCommand(new ShowCommands("show_commands"));
        shell.addCommand(new StopServer("stop_server"));
        shell.addCommand(new Exit("exit"));
        return shell;
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
    }
}