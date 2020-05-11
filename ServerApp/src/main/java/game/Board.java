package game;

/**
 * Board class
 */
public class Board {
    private final int size = 5;
    private int[][] board;

    public Board() {
        board = new int[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                board[i][j] = 0;
            }
        }
    }

    public int getPiece(int xIndex, int yIndex) {
        return this.board[xIndex][yIndex];
    }

    public boolean setPiece(int xIndex, int yIndex, int piece) {
        if (xIndex < 0 || yIndex < 0 || xIndex >= size || yIndex >= size) {
            return false;
        }
        if (this.board[xIndex][yIndex] == 0) {
            this.board[xIndex][yIndex] = piece;
            return true;
        } else {
            return false;
        }
    }
}