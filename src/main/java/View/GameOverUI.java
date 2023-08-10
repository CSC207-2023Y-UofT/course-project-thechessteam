package View;

import Controller.Controller;
import Database.LeaderBoard;
import Presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GameOverUI implements ActionListener {
    private final Controller clickController;
    private final BoardUI ui;
    private final Presenter presenter;
    JFrame window_frame = new JFrame();
    JLabel game_overLabel = new JLabel("Game Over!");
    JButton playAgain = new JButton("Play Again");
    JButton menuButton = new JButton("Main Menu");
    JButton submitButton = new JButton("Submit Name");
    JTextField nameBox = new JTextField("Winner's Name");

    GameOverUI(Controller clickController, BoardUI ui, Presenter presenter) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) { // Opens the menu screen
            window_frame.dispose();
            ui.disposeFrame();
            new MenuUI(clickController, presenter);
        } else if (e.getSource() == playAgain) { // Starts a new game instance
            window_frame.dispose();
            ui.disposeFrame();
            BoardUI board = new BoardUI(clickController, presenter);
            presenter.set_view(board);
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
