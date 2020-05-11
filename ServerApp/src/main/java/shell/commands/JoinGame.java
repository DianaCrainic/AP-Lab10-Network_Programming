package shell.commands;

import game.Game;
import game.GameManager;
import game.Player;

/**
 * JoinGame class: responsible for join_game command
 */
public class JoinGame extends Command {
    private final GameManager gameManager = GameManager.getInstance();

    public JoinGame(String command, String arguments) {
        super(command, arguments);
    }

    @Override
    public String execute(Object... arguments) {
        if (arguments.length != 2) {
            return "Please specify the id of the game.";
        }
        Player player = (Player) arguments[0];
        int id = Integer.parseInt((String) arguments[1]);

        if (gameManager.getGames().size() - 1 >= id && gameManager.getGames().get(id).isAvailable()) {
            Game game = gameManager.getGames().get(id);
            player.setGame(game);
            player.setToken(2);
            game.addPlayer(player);
            return "You joined the game. Your number is 2. Your opponent is " + game.getPlayers().get(0).getName();
        }
        return "Game unavailable";
    }
}