import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * GameClient class:
 * - an instance of this class will read commands
 * from the keyboard and it will send them to the server.
 * -the client stops when it reads from the keyboard the string "exit".
 */
public class GameClient {
    private static int currentPlayer;
    private static int opponentPlayer;

    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port

        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {

            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Send command: ");
                String command = scanner.nextLine();
                out.println(command);

                String response = in.readLine();
                System.out.println(response);

                if (command.equals("exit") || command.equals("stop_server")) {
                    break;
                } else if (response.contains("created") || response.contains("joined")) {
                    setCurrentPlayer(response);
                    startGame(in, out);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }

    private static void setCurrentPlayer(String response) {
        if (response.contains("create")) {
            currentPlayer = 1;
            opponentPlayer = 2;
        } else {
            currentPlayer = 2;
            opponentPlayer = 1;
        }
    }

    private static void startGame(BufferedReader in, PrintWriter out) throws IOException {
        Board board = new Board();
        String response = in.readLine();
        System.out.println(response);

        if (currentPlayer == 2) {
            updateBoard(board, response, opponentPlayer);
            board.print();
        }
        boolean playing = true;
        while (playing) {
            if (response.contains("won") || response.contains("lost")) {
                playing = false;
            } else {
                String move = "";
                boolean validMove = false;
                while (!validMove) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Submit move: ");
                    move = scanner.nextLine();
                    out.println(move);

                    response = in.readLine();
                    System.out.println(response);
                    if (!response.contains("Invalid")) {
                        validMove = true;
                    }
                }

                updateBoard(board, move, currentPlayer);
                board.print();

                if (response.contains("won") || response.contains("lost")) {
                    playing = false;
                } else {
                    response = in.readLine();
                    System.out.println(response);
                    updateBoard(board, response, opponentPlayer);
                    board.print();
                }
            }
        }
    }


    private static void updateBoard(Board board, String move, int piece) {
        String[] arguments = move.split(" ");
        int x = Integer.parseInt(arguments[arguments.length - 2]);
        int y = Integer.parseInt(arguments[arguments.length - 1]);
        board.setPiece(x, y, piece);
    }
}