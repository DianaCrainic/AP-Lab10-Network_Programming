package server;

import models.Game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientThread class:
 * -an instance of this class will be responsible with communicating
 * with a client Socket.
 * -if the server receives the command 'stop' it will stop and
 * will return to the client the response "Server stopped",
 * otherwise it returns: "Server received the request ... ".
 */

public class ClientThread extends Thread {
    private Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            boolean serverIsRunningCurrentClient = true;

            while (serverIsRunningCurrentClient) {
                String request = in.readLine();
                String[] command = request.split(" ");

                String response;
                if (command[0].equals("stop")) {
                    stopServer();
                } else if (command[0].equals("exit")) {
                    serverIsRunningCurrentClient = false;
                    exitClient();
                } else {
                    response = "Server received the request: " + request;
                    switch (command[0]) {
                        case "create":
                            createGame();
                            break;
                        case "join":
                            joinGame();
                            break;
                        case "submit":
                            submitMove();
                            break;
                    }
                    out.println(response);
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private void createGame() {
        System.out.println("The game was created!");
    }

    private void joinGame() {
        System.out.println("You joined the game!");
        Game game = new Game();
        game.run();
    }

    private void submitMove() {
        System.out.println("The move was submitted!");
    }

    public void stopServer() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String response = "Server stopped";
            out.println(response);
            out.flush();
            socket.close();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public void exitClient() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String response = "You left the game.";
            out.println(response);
            out.flush();
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
