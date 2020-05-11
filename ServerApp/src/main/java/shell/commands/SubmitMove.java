package shell.commands;

import game.Player;

/**
 * SubmitMove class: responsible for submit_move command
 */
public class SubmitMove extends Command {

    public SubmitMove(String command, String arguments) {
        super(command, arguments);
    }

    @Override
    public String execute(Object... arguments) {
        if (arguments.length != 3) {
            return "Please specify the coordinates: submit_move x y";
        }

        Player player = (Player) arguments[0];
        int x = Integer.parseInt((String) arguments[1]);
        int y = Integer.parseInt((String) arguments[2]);

        if (player.getGame().getBoard().setPiece(x, y, player.getToken())) {
            String result = "Player " + player.getName() + " placed a number on (" + x + "," + y + ")";
            String move = arguments[1] + " " + arguments[2];
            player.getGame().addMove(player, move);
            if (player.wonTheGame(x, y)) {
                result = result.concat(" and WON the game!");
                player.getGame().addMove(player, "won the game");
            }
            return result;
        }

        return "Invalid move";
    }
}