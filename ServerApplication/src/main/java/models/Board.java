package models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Board class:
 * -displays the checkerboard using a 15×15 board
 * -provides users play Gomoku
 */
public class Board extends JPanel implements ActionListener, MouseListener {
    public JButton newGameBtn;
    public JButton surrenderBtn;
    public JLabel message;
    private int[][] board;

    boolean gameInProgress;
    Player currentPlayer;

    int winnerRowStart;
    int winnerRowFinish;
    int winnerColumnStart;
    int winnerColumnFinish;


    /**
     * - creating the buttons and label and adding listeners for mouse
     * clicks
     * - creating the board and starting the first game.
     */
    public Board() {
        setBackground(Color.LIGHT_GRAY);
        addMouseListener(this);
        surrenderBtn = new JButton("Surrender");
        surrenderBtn.addActionListener(this);
        newGameBtn = new JButton("New Game");
        newGameBtn.addActionListener(this);
        message = new JLabel("", JLabel.CENTER);
        message.setForeground(Color.BLACK);
        board = new int[15][15];
        setNewGame();
    }


    /**
     * responding to user's click on one of the two buttons (new game or surrender).
     */
    public void actionPerformed(ActionEvent actionEvent) {
        Object actionEventSource = actionEvent.getSource();
        if (actionEventSource == newGameBtn) {
            setNewGame();
        } else if (actionEventSource == surrenderBtn) {
            resetGame();
        }
    }


    public void setNewGame() {
        if (gameInProgress) {
            message.setText("Finish the current game first!");
            return;
        }
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                board[row][col] = 0;
            }
        }
        currentPlayer = Player.BLACK;   // BLACK moves first
        message.setText("BLACK - It's your turn!");
        gameInProgress = true;
        newGameBtn.setEnabled(false);
        surrenderBtn.setEnabled(true);
        winnerRowStart = -1;
        repaint();
    }


    /**
     * if a player clicks on surrender button, the opponent wins
     */
    public void resetGame() {
        if (!gameInProgress) {
            message.setText("There is no game in progress!");
            return;
        }
        if (currentPlayer == Player.WHITE) {
            message.setText("WHITE surrenders. BLACK wins.");
        } else {
            message.setText("BLACK surrenders. WHITE wins.");
        }
        newGameBtn.setEnabled(true);
        surrenderBtn.setEnabled(false);
        gameInProgress = false;
    }


    public void gameOver(String str) {
        message.setText(str);
        newGameBtn.setEnabled(true);
        surrenderBtn.setEnabled(false);
        gameInProgress = false;
    }


    public void doClickSquare(int row, int col) {
        if (board[row][col] != 0) {
            verifyEmptySquare(currentPlayer);
            return;
        }

        if (winner(row, col)) {
            displayWinner(currentPlayer);
            return;
        }

        if (currentPlayer == Player.BLACK) {
            board[row][col] = 2; //2 - BLACK
        } else {
            board[row][col] = 1; //1 - WHITE
        }
        repaint();

        boolean emptySpace = checkBoardFull();
        if (!emptySpace) {
            gameOver("The game ends in a draw.");
            return;
        }

        //next player
        if (currentPlayer == Player.BLACK) {
            currentPlayer = Player.WHITE;
            message.setText("WHITE - It's your turn!");
        } else {
            currentPlayer = Player.BLACK;
            message.setText("BLACK - It's your turn!");
        }

    }


    public void verifyEmptySquare(Player currentPlayer) {
        if (currentPlayer == Player.BLACK) {
            message.setText("BLACK - Please click an empty square.");
        } else {
            message.setText("WHITE - Please click an empty square.");
        }
    }

    public void displayWinner(Player currentPlayer) {
        if (currentPlayer == Player.WHITE) {
            gameOver("WHITE wins the game!");
        } else {
            gameOver("BLACK wins the game!");
        }
    }

    public boolean checkBoardFull() {
        boolean emptySpace = false;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (board[i][j] == 0) {
                    emptySpace = true;
                    break;
                }
            }
        }
        return emptySpace;
    }

    /**
     * if there are 5 pieces in a row in any direction, that player wins
     */
    private boolean winner(int row, int col) {

        if (count(board[row][col], row, col, 1, 0) >= 5) {
            return true;
        }
        if (count(board[row][col], row, col, 0, 1) >= 5) {
            return true;
        }
        if (count(board[row][col], row, col, 1, -1) >= 5) {
            return true;
        }
        if (count(board[row][col], row, col, 1, 1) >= 5) {
            return true;
        }
        winnerRowStart = -1; // no player won
        return false;
    }

    /**
     * count the pieces horizontally, vertically, or diagonally
     */
    private int count(int player, int row, int col, int dirX, int dirY) {
        int numberOfPiecesInRow = 1;
        int currentRow;
        int currentColumn;

        currentRow = row + dirX;
        currentColumn = col + dirY;
        while (currentRow >= 0 && currentRow < 15 && currentColumn >= 0 && currentColumn < 15 && board[currentRow][currentColumn] == player) {
            numberOfPiecesInRow++;
            currentRow = currentRow + dirX;
            currentColumn = currentColumn + dirY;
        }
        winnerRowStart = currentRow - dirX;
        winnerColumnStart = currentColumn - dirY;

        currentRow = row - dirX;
        currentColumn = col - dirY;
        while (currentRow >= 0 && currentRow < 15 && currentColumn >= 0 && currentColumn < 15 && board[currentRow][currentColumn] == player) {
            numberOfPiecesInRow++;
            currentRow = currentRow - dirX;
            currentColumn = currentColumn - dirY;
        }
        winnerRowFinish = currentRow + dirX;
        winnerColumnFinish = currentColumn + dirY;

        return numberOfPiecesInRow;
    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        for (int i = 1; i < 15; i++) {
            g.drawLine(1 + 15 * i, 0, 1 + 15 * i, getSize().height);
            g.drawLine(0, 1 + 15 * i, getSize().width, 1 + 15 * i);
        }
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
        g.drawRect(1, 1, getSize().width - 3, getSize().height - 3);


        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                if (board[row][col] != 0) {
                    drawPiece(g, board[row][col], row, col);
                }
            }
        }

         /* draw a line to mark
          the five winning pieces. */
        if (winnerRowStart >= 0) {
            drawWinningLine(g);
        }
    }

    private void drawPiece(Graphics g, int piece, int row, int col) {
        if (piece == 1) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillOval(4 + 15 * col, 4 + 15 * row, 10, 10);
    }


    private void drawWinningLine(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine(10 + 15 * winnerColumnStart, 10 + 15 * winnerRowStart, 10 + 15 * winnerColumnFinish, 10 + 15 * winnerRowFinish);
        g.drawLine(10 + 15 * winnerColumnStart, 10 + 15 * winnerRowStart, 10 + 15 * winnerColumnFinish, 10 + 15 * winnerRowFinish);
    }

    public void mousePressed(MouseEvent evt) {
        if (!gameInProgress)
            message.setText("Click \"New Game\" to start a new game.");
        else {
            int col = (evt.getX() - 2) / 15;
            int row = (evt.getY() - 2) / 15;
            if (col >= 0 && col < 15 && row >= 0 && row < 15)
                doClickSquare(row, col);
        }
    }

    public void mouseReleased(MouseEvent evt) {
    }

    public void mouseClicked(MouseEvent evt) {
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }
}