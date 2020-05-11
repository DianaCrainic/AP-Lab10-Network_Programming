/**
 * Board class: initialize the matrix, set a piece and print the board
 */
public class Board {
    private final int size = 5;
    private int[][] board;

    public Board() {
        board = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = 0;
            }
        }
    }

    public boolean setPiece(int x, int y, int piece) {
        if (x < 0 || y < 0 || x >= size || y >= size) {
            return false;
        } else if (this.board[x][y] == 0) {
            this.board[x][y] = piece;
            return true;
        } else {
            return false;
        }

    }

    public void print() {
        System.out.println("\nBoard:\n_________");
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print(i);
            System.out.print(' ');
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.print(i);
            System.out.print(' ');
            for (int j = 0; j < size; j++) {
                System.out.print(this.board[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println("__________");
    }
}