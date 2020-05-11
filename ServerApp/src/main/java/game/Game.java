package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game class
 */
public class Game {
    private final int winLength = 3;
    private int currentTurn;
    private Board board;
    private List<Player> players;
    private List<Map<String, String>> moves;

    public Game() {
        this.currentTurn = 1;
        this.board = new Board();
        this.players = new ArrayList<>();
        this.moves = new ArrayList<>();
    }

    public int getCurrentTurn() {
        return currentTurn;
    }


    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }


    public List<Map<String, String>> getMoves() {
        return moves;
    }

    public synchronized boolean isAvailable() {
        return players.size() < 2;
    }

    public boolean addPlayer(Player player) {
        if (players.size() > 2) {
            return false;
        }
        players.add(player);
        return true;
    }

    public void updateTurn() {
        if (this.currentTurn == 1) {
            this.currentTurn = 2;
        } else {
            this.currentTurn = 1;
        }
    }

    public synchronized void addMove(Player player, String move) {
        Map<String, String> currentMove = new HashMap<>();
        String token = String.valueOf(player.getToken());
        currentMove.put("token", token);
        currentMove.put("move", move);
        this.moves.add(currentMove);
    }

    public synchronized Map<String, String> getLastMove() {
        return this.moves.get(this.moves.size() - 1);
    }

    private final int[] xDirections = {1, 0, 1, 1};
    private final int[] yDirections = {0, 1, 1, -1};

    public boolean checkWon(int token, int x, int y) {
        for (int direction = 0; direction < 4; ++direction) {
            int currentChainSize = 1;

            for (int i = 1; i < winLength; ++i) {
                int currentX = x + xDirections[direction] * i;
                int currentY = y + yDirections[direction] * i;

                if (this.board.getPiece(currentX, currentY) == token) {
                    ++currentChainSize;
                } else {
                    break;
                }
            }

            for (int i = 1; i < winLength; ++i) {
                int currentX = x - xDirections[direction] * i;
                int currentY = y - yDirections[direction] * i;

                if (this.board.getPiece(currentX, currentY) == token) {
                    ++currentChainSize;
                } else {
                    break;
                }
            }

            if (currentChainSize >= winLength) {
                return true;
            }
        }
        return false;
    }




}