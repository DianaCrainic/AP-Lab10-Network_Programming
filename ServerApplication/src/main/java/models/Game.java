package models;

import java.awt.*;
import javax.swing.*;

/**
 * game instructions: https://en.wikipedia.org/wiki/Gomoku
 * Game class:
 * -this panel lets two users play Gomoku against each other.
 * -black always starts the game.
 * -players alternate turns placing a piece of their color on an empty intersection.
 * -the winner is the first player to form an unbroken chain of five stones horizontally,
 * vertically, or diagonally.
 * -the game ends in a draw if the board is filled
 * before either player wins.
 */
public class Game extends JPanel {

    /**
     * create the window, set sizes, properties
     */
    public void run() {
        JFrame window = new JFrame("GOMOKU");
        Game game = new Game();
        window.setContentPane(game);
        window.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screenSize.width - window.getWidth()) / 2,
                (screenSize.height - window.getHeight()) / 2);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
    }


    public Game() {
        setLayout(null);
        setPreferredSize(new Dimension(500, 400));
        setBackground(new Color(64, 127, 252));
        Board board = new Board();
        addElementsToBoard(board);
        setBoundsToElements(board);
    }

    public void addElementsToBoard(Board board) {
        add(board);
        add(board.newGameBtn);
        add(board.surrenderBtn);
        add(board.message);
    }

    public void setBoundsToElements(Board board) {
        board.setBounds(50, 50, 228, 228);
        board.newGameBtn.setBounds(300, 60, 120, 30);
        board.surrenderBtn.setBounds(300, 120, 120, 30);
        board.message.setBounds(0, 300, 350, 30);
    }
}