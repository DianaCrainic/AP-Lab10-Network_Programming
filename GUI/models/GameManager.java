package models;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static GameManager gameManager;
    private List<Game> games;

    private GameManager() {
        this.games = new ArrayList<>();
    }

    public static GameManager getGameManager() {
        return gameManager;
    }

    public static void setGameManager(GameManager gameManager) {
        GameManager.gameManager = gameManager;
    }

    public void setGames(List<Game> games) {
        this.games = games;
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
