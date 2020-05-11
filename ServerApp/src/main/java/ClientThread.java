import game.Player;
import shell.*;
import shell.commands.Command;
import shell.commands.SubmitMove;

import java.io.*;
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
    private final Socket socket;
    private final Shell shell;
    private final Player player;

    public ClientThread(Socket socket, Shell shell) {
        this.socket = socket;
        this.shell = shell;
        this.player = new Player();
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            int stopFlag = 0;
            while (stopFlag == 0) {
                String request = in.readLine();

                String[] commandArguments = request.split(" ", 2);
                Command command = shell.getCommand(commandArguments[0]);

                String response = executeCommand(shell, command, commandArguments);
                out.println(response);
                out.flush();

                if (response.equals("You've left the game")) {
                    stopFlag = 1;
                } else if (response.contains("stopped")) {
                    System.exit(0);
                } else if (response.contains("created") || response.contains("joined")) {
                    startGame(in, out);
                }
            }
        } catch (IOException exception) {
            System.err.println("Communication error: " + exception);
        } finally {
            try {
                socket.close();
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
            }
        }
    }

    private String executeCommand(Shell shell, Command command, String[] commandArguments) {
        if (command == null) {
            return "Invalid command";
        } else if (command.getCommand().equals("show_commands")) {
            return command.execute(shell);
        } else if (command.getCommand().equals("set_name")) {
            return executeSetName(command, commandArguments);
        } else if (command.getCommand().equals("create_game")) {
            return command.execute(player);
        } else if (command.getCommand().equals("join_game")) {
            return executeJoinGame(command, commandArguments);
        }
        return command.execute();
    }

    private String executeSetName(Command command, String[] commandArguments) {
        if (commandArguments.length > 1) {
            return command.execute(player, commandArguments[1]);
        }
        return command.execute();
    }

    private String executeJoinGame(Command command, String[] commandArguments) {
        if (commandArguments.length > 1) {
            return command.execute(player, commandArguments[1]);
        }
        return command.execute();
    }

    private void startGame(BufferedReader in, PrintWriter out) throws IOException {
        waitingForOpponent();
        String response;
        if (player.getToken() == 1) {
            response = "Your opponent is " + player.getGame().getPlayers().get(1).getName() + ". It's your turn to submit a move";
            out.println(response);
            out.flush();
        }

        String request;
        boolean playing = true;
        while (playing) {
            if (player.getGame().getCurrentTurn() == player.getToken()) {
                boolean validMove = false;
                while (!validMove) {
                    request = in.readLine();

                    response = submitMove(request);
                    out.println(response);
                    out.flush();
                    if (!response.contains("Invalid")) {
                        if (response.contains("won")) {
                            playing = false;
                        }
                        validMove = true;
                    }
                }
                player.getGame().updateTurn();
            }

            if (player.getGame().getCurrentTurn() != player.getToken()) {
                response = getMoveFromOpponent(playing);
                if (playing) {
                    out.println(response);
                    out.flush();
                    if (response.contains("lost")) {
                        playing = false;
                    }
                }
            }
        }
    }

    private void waitingForOpponent() {
        synchronized (player.getGame()) {
            player.getGame().notifyAll();
            while (player.getGame().getPlayers().size() < 2) {
                try {
                    player.getGame().wait();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private String getMoveFromOpponent(boolean playing) {
        String move = "";
        synchronized (player.getGame()) {
            player.getGame().notifyAll();
            if (!playing) {
                return "";
            }
            while (player.getGame().getMoves().size() == 0 ||
                    player.getGame().getLastMove().get("token").equals(String.valueOf(player.getToken()))) {
                try {
                    player.getGame().wait();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }

            if (player.getGame().getLastMove().get("move").equals("won the game")) {
                move = move.concat("You lost.");
                move = move.concat("Your opponent move: ");
                move = move.concat(player.getGame().getMoves().get(player.getGame().getMoves().size() - 2).get("move"));
            } else {
                move = move.concat("Your opponent move: ");
                move = move.concat(player.getGame().getLastMove().get("move"));
            }
        }
        return move;
    }

    private String submitMove(String move) {
        SubmitMove submitMoveCommand = new SubmitMove("submit_move", "x, y");
        String[] moveArguments = move.split(" ");
        if (moveArguments.length == 2) {
            return submitMoveCommand.execute(player, moveArguments[0], moveArguments[1]);
        }
        return submitMoveCommand.execute();
    }


}
