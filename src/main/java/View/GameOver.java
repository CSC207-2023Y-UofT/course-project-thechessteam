package View;

import Controller.Controller;
import Presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * GameOver class provides the graphical user interface for displaying the game over screen.
 * It presents the user with options to play again, navigate to the main menu, or submit their name.
 * The class implements ActionListener to handle user interactions with the buttons on the screen.
 *
 */
public class GameOver implements ActionListener {
    private Controller clickController;
    private ChessBoardUI ui;
    private Presenter presenter;
    JFrame window_frame = new JFrame();
    JLabel game_overLabel = new JLabel("Game Over!");
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
    GameOver(Controller clickController, ChessBoardUI ui, Presenter presenter) {
        this.clickController = clickController;
        this.ui = ui;
        this.presenter = presenter;
        // Configuring the gameOver Icon
        Image unscaledIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/GameOver.png"))).getImage();
        Image gameOverIcon = unscaledIcon.getScaledInstance(100 ,100, java.awt.Image.SCALE_SMOOTH);

        game_overLabel.setText("");
        game_overLabel.setBounds(43, 0, 100, 100);
        game_overLabel.setIcon(new ImageIcon(gameOverIcon));


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
        window_frame.add(game_overLabel);
        window_frame.add(playAgain);
        window_frame.add(menuButton);
        window_frame.add(nameBox);
        window_frame.add(submitButton);

        // configuring the window
        window_frame.setLayout(null);
        window_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window_frame.setSize(200, 300);
        window_frame.setResizable(false);

        window_frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-window_frame.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-window_frame.getHeight())/2);

        window_frame.setVisible(true);
    }

    /**
     * Handles button clicks from the user, providing functionality to play again, go to the main menu, or submit their name.
     *
     * @param e The ActionEvent object containing information about the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) { // Opens the menu screen
            window_frame.dispose();
            ui.disposeFrame();
            new MainMenu(clickController, presenter);
        } else if (e.getSource() == playAgain) { // Starts a new game instance
            window_frame.dispose();
            ui.disposeFrame();
            ChessBoardUI board = new ChessBoardUI(clickController, presenter);
            presenter.set_view(board);
            board.newGame();
        } else if (e.getSource() == submitButton) {
            if (!Objects.equals(nameBox.getText(), "Record Name")) {
                // TODO Put leaderboard insertion code here, use "nameBox.getText()" to get the contents of the inputed text
                System.out.println(nameBox.getText());
            }
        }
    }
}
