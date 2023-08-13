package view;

import controller.Controller;
import database.LeaderBoard;

import presenter.Presenter; // Used for reestablishing framework after we create a new view class.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LeaderBoardUI implements ActionListener {
    private final Controller clickController;
    private final Presenter presenter;
    JFrame leaderboardFrame = new JFrame("Database.LeaderBoard");
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
        int iteration = 0;

        for (String i: LeaderBoard.sendData()) {
            iteration += 1;

            String[] string_split = i.split(":");
            String name = string_split[0];
            String wins = string_split[1];

            JLabel tempName = new JLabel(name);
            tempName.setOpaque(true);
            tempName.setBackground(new Color(176, 174, 232));
            tempName.setBounds(0,iteration*25,171,25);


            JLabel tempWins = new JLabel(wins);
            tempWins.setOpaque(true);
            tempWins.setBackground(new Color(191, 133, 253));
            tempWins.setBounds(171,iteration*25,171,25);

            scoresPanel.add(tempName);
            scoresPanel.add(tempWins);
        }

        // Adding components to frame
        leaderboardFrame.add(frameBackground);
        frameBackground.add(leaderBackground);
        leaderBackground.add(backButton);
        leaderBackground.add(scrollingScore);
        scoresPanel.add(nameSort);
        scoresPanel.add(winSort);


        // Creates the leaderboard Frame
        leaderboardFrame.setLayout(null);
        leaderboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        leaderboardFrame.setSize(400, 500);
        leaderboardFrame.setResizable(false);

        leaderboardFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width- leaderboardFrame.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height- leaderboardFrame.getHeight())/2);

        leaderboardFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            leaderboardFrame.dispose();
            new MenuUI(clickController, presenter);
        }
    }
}
