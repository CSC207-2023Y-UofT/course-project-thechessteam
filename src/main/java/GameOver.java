import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GameOver implements ActionListener {
    JFrame window_frame = new JFrame();
    JLabel game_overLabel = new JLabel("Game Over!");
    JButton playAgain = new JButton("Play Again");
    JButton menuButton = new JButton("Main Menu");

    GameOver() {
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

        // All added components
        window_frame.add(game_overLabel);
        window_frame.add(playAgain);
        window_frame.add(menuButton);

        // configuring the window
        window_frame.setLayout(null);
        window_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window_frame.setSize(200, 250);
        window_frame.setResizable(false);

        window_frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-window_frame.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-window_frame.getHeight())/2);

        window_frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            window_frame.dispose();
            UI.javaF.dispose();
            MainMenu new_menu = new MainMenu();
        } else if (e.getSource() == playAgain) {
            System.out.println("Running the game again");
        }
    }
}
