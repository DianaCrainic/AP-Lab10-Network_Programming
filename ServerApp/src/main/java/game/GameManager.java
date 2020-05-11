package game;

import java.util.ArrayList;
import java.util.List;

/**
 * GameManager class: singleton class
 */
public class GameManager {
    private static GameManager gameManager;
    private List<Game> games;

    private GameManager() {
        this.games = new ArrayList<>();
    }

    public List<Game> getGames() {
        return games;
    }

    public synchronized static GameManager getInstance() {
        if (gameManager == null) {
            gameManager = new GameManager();
        }

        return gameManager;
    }

    public synchronized void addGame(Game game) {
        games.add(game);
    }

}