package View;

import Controller.Controller;
import Presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * MainMenu class provides the graphical user interface for the main menu screen of a chess game.
 * It includes buttons for starting a new game, viewing preferences, displaying the leaderboard, and accessing tutorials.
 * The class implements ActionListener to handle user interactions with these buttons.
 *
 */
public class MainMenu implements ActionListener {
    private Controller clickController;
    private Presenter presenter;
    JFrame menu_frame = new JFrame("Main Menu");
    JLabel menuBackground = new JLabel("Background");
    JLabel logoBackground = new JLabel("logoBackground");
    JLabel buttonBackground = new JLabel("buttonBackground");
    JLabel chessTeam_logo = new JLabel("theChessTeam");
    JButton playButton = new JButton("playButton");
    JButton preferencesButton = new JButton("preferencesButton");
    JButton leaderBoardButton = new JButton("leaderBoardButton");
    JButton tutorialButton = new JButton("tutorialButton");

    /**
     * Constructs a new MainMenu object.
     *
     * @param clickController The Controller object responsible for handling user clicks.
     * @param presenter The Presenter object responsible for handling the display logic.
     */
    public MainMenu(Controller clickController, Presenter presenter) {
        this.clickController = clickController;
        this.presenter = presenter;
        // Configuring menu background
        Image unscaledBackgroundIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/projectimages/MenuBackground.jpg"))).getImage();
        Image backgroundImage = unscaledBackgroundIcon.getScaledInstance(500 ,350,
                java.awt.Image.SCALE_SMOOTH);

        menuBackground.setText("");
        menuBackground.setBounds(0, 0, 500, 350);
        menuBackground.setIcon(new ImageIcon(backgroundImage));

        // Configuring the game logo
        Image unscaledLogoBIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/LogoBackground.png"))).getImage();
        Image logoBackgroundIcon = unscaledLogoBIcon.getScaledInstance(350 ,60, java.awt.Image.SCALE_SMOOTH);

        logoBackground.setText("");
        logoBackground.setBounds(70, 30, 350, 60);
        logoBackground.setIcon(new ImageIcon(logoBackgroundIcon));

        Image unscaledIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/Logo.png"))).getImage();
        Image gameOverIcon = unscaledIcon.getScaledInstance(350 ,60, java.awt.Image.SCALE_SMOOTH);

        chessTeam_logo.setText("");
        chessTeam_logo.setBounds(0, 0, 350, 60);
        chessTeam_logo.setIcon(new ImageIcon(gameOverIcon));

        // Configuring the background for buttons
        Image unscaledBBackground = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/projectimages/BBackground.png"))).getImage();
        Image buttonBackgroundIcon = unscaledBBackground.getScaledInstance(250 ,200,
                java.awt.Image.SCALE_SMOOTH);

        buttonBackground.setText("");
        buttonBackground.setBounds(120, 100, 250, 200);
        buttonBackground.setIcon(new ImageIcon(buttonBackgroundIcon));

        // Configuring the play button
        playButton.setText("PLAY");
        playButton.setFocusPainted(false);
        playButton.setBounds(25, 30, 200, 25);
        playButton.setBackground(new Color(48, 183, 62));
        playButton.addActionListener(this);

        // Configuring the preferences button
        preferencesButton.setText("Preferences");
        preferencesButton.setFocusPainted(false);
        preferencesButton.setBounds(25, 70, 200, 25);
        preferencesButton.setBackground(new Color(210, 242, 245));
        preferencesButton.addActionListener(this);

        // Configuring the leaderboard button
        leaderBoardButton.setText("Leaderboard");
        leaderBoardButton.setFocusPainted(false);
        leaderBoardButton.setBounds(25, 110, 200, 25);
        leaderBoardButton.setBackground(new Color(184, 190, 210));
        leaderBoardButton.addActionListener(this);

        // Configuring the tutorial button
        tutorialButton.setText("Tutorial");
        tutorialButton.setFocusPainted(false);
        tutorialButton.setBounds(25, 150, 200, 25);
        tutorialButton.setBackground(new Color(243, 192, 53));
        tutorialButton.addActionListener(this);

        // All added components
        menuBackground.add(logoBackground);
        menuBackground.add(buttonBackground);
        logoBackground.add(chessTeam_logo);
        buttonBackground.add(playButton);
        buttonBackground.add(preferencesButton);
        buttonBackground.add(leaderBoardButton);
        buttonBackground.add(tutorialButton);
        menu_frame.add(menuBackground);

        // configuring the window
        menu_frame.setLayout(null);
        menu_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu_frame.setSize(500, 350);
        menu_frame.setResizable(false);

        menu_frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-menu_frame.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-menu_frame.getHeight())/2);

        menu_frame.setVisible(true);
    }

    /**
     * Handles button clicks from the user, providing functionality to start a new game,
     * view preferences, display the leaderboard, and access tutorials.
     *
     * @param e The ActionEvent object containing information about the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) { // Starts a new game instance
            menu_frame.dispose();
            ChessBoardUI board = new ChessBoardUI(clickController, presenter);
            presenter.set_view(board);
            board.newGame();
        } else if (e.getSource() == leaderBoardButton) {
            menu_frame.dispose();
            new LeaderBoardUI(clickController, presenter);
        }
    }
}