package shell.commands;

import game.Game;
import game.GameManager;
import game.Player;

/**
 * CreateGame class: responsible for create_game command
 */
public class CreateGame extends Command {
    private final GameManager gameManager = GameManager.getInstance();

    public CreateGame(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        Player player = (Player) arguments[0];
        player.setToken(1);
        Game game = new Game();
        game.addPlayer(player);
        player.setGame(game);
        gameManager.addGame(game);
        return "The game was created. Your number is 1. Waiting for an opponent...";
    }
}