package view;

import controller.Controller;
import database.LeaderBoard;

import presenter.Presenter; // Used for reestablishing framework after we create a new view class.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * GameOverUI class provides the graphical user interface for displaying the game over screen.
 * It presents the user with options to play again, navigate to the main menu, or submit their name.
 * The class implements ActionListener to handle user interactions with the buttons on the screen.
 *
 */
public class GameOverUI implements ActionListener {
    private final Controller clickController;
    private final BoardUI ui;
    private final Presenter presenter;
    JFrame windowFrame = new JFrame();
    JLabel gameOverLabel = new JLabel("Game Over!");
    JButton playAgain = new JButton("Play Again");
    JButton menuButton = new JButton("Main Menu");
    JButton submitButton = new JButton("Submit Name");
    JTextField nameBox = new JTextField("Winner's Name");

    /**
     * Constructs a new GameOver object.
     *
     * @param clickController The Controller object responsible for handling user clicks.
     * @param ui The ChessBoardUI object representing the current user interface.
     * @param presenter The Presenter object responsible for handling the display logic.
     */
    GameOverUI(Controller clickController, BoardUI ui, Presenter presenter) {
        this.clickController = clickController;
        this.ui = ui;
        this.presenter = presenter;
        // Configuring the gameOver Icon
        Image unscaledIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/GameOver.png"))).getImage();
        Image gameOverIcon = unscaledIcon.getScaledInstance(100 ,100, java.awt.Image.SCALE_SMOOTH);

        gameOverLabel.setText("");
        gameOverLabel.setBounds(43, 0, 100, 100);
        gameOverLabel.setIcon(new ImageIcon(gameOverIcon));


        // Configuring the playAgain button
        playAgain.setText("PLAY AGAIN");
        playAgain.setFocusPainted(false);
        playAgain.setBounds(17, 110, 150, 30);
        playAgain.setBackground(new Color(48, 183, 62));
        playAgain.addActionListener(this);

        // Configuring the MainMenu button
        menuButton.setText("MAIN MENU");
        menuButton.setFocusPainted(false);
        menuButton.setBounds(17, 160, 150, 30);
        menuButton.setBackground(new Color(48, 115, 183));
        menuButton.addActionListener(this);

        // Configuring the submit name box
        nameBox.setText("Record Name");
        nameBox.setBounds(17, 210, 100, 30);
        nameBox.setBackground(new Color(255, 255, 255));

        // Configuring the submit button
        submitButton.setText("Submit Name");
        submitButton.setFocusPainted(false);
        submitButton.setBounds(127, 210, 40, 29);
        submitButton.setBackground(new Color(103, 106, 110));
        submitButton.addActionListener(this);


        // All added components
        windowFrame.add(gameOverLabel);
        windowFrame.add(playAgain);
        windowFrame.add(menuButton);
        windowFrame.add(nameBox);
        windowFrame.add(submitButton);

        // configuring the window
        windowFrame.setLayout(null);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setSize(200, 300);
        windowFrame.setResizable(false);

        windowFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width- windowFrame.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height- windowFrame.getHeight())/2);

        windowFrame.setVisible(true);
    }

    /**
     * Handles button clicks from the user, providing functionality to play again, go to the main menu, or submit their name.
     *
     * @param e The ActionEvent object containing information about the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) { // Opens the menu screen
            windowFrame.dispose();
            ui.disposeFrame();
            new MenuUI(clickController, presenter);
        } else if (e.getSource() == playAgain) { // Starts a new game instance
            windowFrame.dispose();
            ui.disposeFrame();
            BoardUI board = new BoardUI(clickController, presenter);
            presenter.setView(board);
            board.newBoard();
        } else if (e.getSource() == submitButton) {
            if (!Objects.equals(nameBox.getText(), "Record Name") && !nameBox.getText().contains(",") && !nameBox.getText().contains(":")) {
                LeaderBoard.addPlayer(nameBox.getText());
                submitButton.setEnabled(false);
                nameBox.setEnabled(false);
            }
        }
    }
}
