package View;

import Controller.Controller;
import Presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LeaderBoardUI implements ActionListener {
    private Controller clickController;
    private Presenter presenter;
    JFrame leaderboard_frame = new JFrame("Database.LeaderBoard");
    JLabel frameBackground = new JLabel("Background");
    JLabel leaderBackground = new JLabel("buttonBackground");
    JLabel nameSort = new JLabel("Name");
    JLabel winSort = new JLabel("Wins");
    JPanel scoresPanel = new JPanel();
    JScrollPane scrollingScore = new JScrollPane(scoresPanel);
    JButton backButton = new JButton("Back");

    public LeaderBoardUI(Controller clickController, Presenter presenter) {
        this.clickController = clickController;
        this.presenter = presenter;
        // Configuring menu background
        Image unscaledBackgroundIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/projectimages/MenuBackground.jpg"))).getImage();
        Image backgroundImage = unscaledBackgroundIcon.getScaledInstance(400 ,500,
                java.awt.Image.SCALE_SMOOTH);

        frameBackground.setText("");
        frameBackground.setBounds(0, 0, 400, 500);
        frameBackground.setIcon(new ImageIcon(backgroundImage));

        // Configuring the background for buttons
        Image unscaledBBackground = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/projectimages/BBackground.png"))).getImage();
        Image buttonBackgroundIcon = unscaledBBackground.getScaledInstance(385 ,475,
                java.awt.Image.SCALE_SMOOTH);

        leaderBackground.setText("");
        leaderBackground.setBounds(0, -10, 385, 475);
        leaderBackground.setIcon(new ImageIcon(buttonBackgroundIcon));

        // creating the score panel
        scoresPanel.setPreferredSize(new Dimension(300,10000));
        scoresPanel.setBounds(20,55,345,100);
        scoresPanel.setLayout(null);
        scoresPanel.setBackground(new Color(247, 250, 220));

        // Scrolling bar
        scrollingScore.setBounds(20,55,345,390);
        scrollingScore.setPreferredSize(new Dimension(345,390));
        scrollingScore.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // creating the score panel
        nameSort.setOpaque(true);
        nameSort.setBounds(0,0,171,25);
        nameSort.setBackground(new Color(97, 54, 169));

        // creating the score panel
        winSort.setOpaque(true);
        winSort.setBounds(171,0,171,25);
        winSort.setBackground(new Color(150, 32, 210));

        // Configuring the back button
        backButton.setText("Back");
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 25, 100, 20);
        backButton.setBackground(new Color(184, 190, 210));
        backButton.addActionListener(this);

        // adds each saved score from the leaderboardTable
        // TODO replace for loop with code to create new labels into table
        int iteration = 0;
        for (int i = 5; i >= 0; i--) {
            iteration += 1;
            JLabel tempName = new JLabel("Temp Name");
            tempName.setOpaque(true);
            tempName.setBackground(new Color(176, 174, 232));
            tempName.setBounds(0,iteration*25,171,25);


            JLabel tempWins = new JLabel(Integer.toString(i));
            tempWins.setOpaque(true);
            tempWins.setBackground(new Color(191, 133, 253));
            tempWins.setBounds(171,iteration*25,171,25);

            scoresPanel.add(tempName);
            scoresPanel.add(tempWins);
        }

        // Adding components to frame
        leaderboard_frame.add(frameBackground);
        frameBackground.add(leaderBackground);
        leaderBackground.add(backButton);
        leaderBackground.add(scrollingScore);
        scoresPanel.add(nameSort);
        scoresPanel.add(winSort);


        // Creates the leaderboard Frame
        leaderboard_frame.setLayout(null);
        leaderboard_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        leaderboard_frame.setSize(400, 500);
        leaderboard_frame.setResizable(false);

        leaderboard_frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-leaderboard_frame.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-leaderboard_frame.getHeight())/2);

        leaderboard_frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            leaderboard_frame.dispose();
            new MainMenu(clickController, presenter);
        }
    }
}
