import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JPanel MenuFrame;
    private JButton preferencesButton;
    private JButton playButton;

    public MainMenu() {
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        preferencesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    public static void main(String[] args) {
        MainMenu newMenu = new MainMenu();
        newMenu.setContentPane(newMenu.MenuFrame);
        newMenu.setTitle("The Chess Team");
        newMenu.setSize(300,200);
        newMenu.setVisible(true);
        newMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
