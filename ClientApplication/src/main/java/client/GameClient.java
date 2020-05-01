package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * GameClient class:
 * - an instance of this class will read commands
 * from the keyboard and it will send them to the server.
 * -the client stops when it reads from the keyboard the string "exit".
 */
public class GameClient {
    private String serverAddress = "127.0.0.1";
    private int PORT = 8100;

    public GameClient() {
        try {
            Socket socket = new Socket(serverAddress, PORT);
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            showCommands();
            while (true) {
                String request = scanner.nextLine();
                out.println(request);
                String response = in.readLine();
                System.out.println(response);
                if (response.equals("Server stopped") || response.equals("You left the game.")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCommands(){
        System.out.println("Enter command: \n-create game\n-join game\n-submit move\n" +
                "-exit (current client)\n-stop (server)");
    }

    public static void main(String[] args) {
        new GameClient();
    }
}
