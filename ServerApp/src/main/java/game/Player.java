package game;

/**
 * Player class
 */
public class Player {
    private String name;
    private Game game;
    private int token;

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public boolean wonTheGame(int x, int y) {
        return this.game.checkWon(token, x, y);
    }
}