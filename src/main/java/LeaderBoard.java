import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;

public class LeaderBoard{
    // TODO: Add initializer
    // has 3 methods excluding main - readData, writeData and printData
    public static void main(String[] args){
        // Read existing leaderboard
        ArrayList<String[]> leaderboard = readData(); // readData method defined below
        printData(leaderboard);   // printData method defined below

        // Add new player
        Scanner input_scanner = new Scanner(System.in); // Scanner class is used to get user input, we just created a scanner object
        System.out.print("Please enter Player name: "); // adjust this with gui
        String input = input_scanner.nextLine();
        // player name must not contain coma
        while (input.contains(",")) {
            System.out.print("Player name must not contain comma.");
            System.out.print("Please enter Player name: ");
            input = input_scanner.nextLine();
        }

        input = input.trim().toLowerCase();
        boolean existing_player = false;
        for (String[] p : leaderboard) {
            if (Objects.equals(p[0], input)) {
                existing_player = true;
                // Turn p[1] to int, add 1, turn it back to string, reassign p[1]
                p[1] = String.valueOf(Integer.parseInt(p[1]) + 1);
            }
        }
        if (!(existing_player)) {
            String[] new_player = {input, "0"};
            leaderboard.add(new_player);
        }

        // Save leaderboard with new player
        writeData(leaderboard); // writeData method defined below
    }

    // read file
    private static ArrayList<String[]> readData() {
        ArrayList<String[]> leaderboard = new ArrayList<>(); // start with an empty array to populate
        try {
            Scanner player_read = new Scanner(new File("players.txt"));
            while (player_read.hasNext()) {
                String[] current_player = player_read.nextLine().split(",");
                leaderboard.add(new String[] {current_player[0], current_player[1]});
            }
            player_read.close();
        } catch (IOException e) {
            System.out.println("No Players in leaderboard yet");
        }
        return leaderboard;
    }

    // write file
    private static void writeData(ArrayList<String[]> leaderboard) {
        try {
            PrintWriter player_output = new PrintWriter("players.txt");
            for (String[] player: leaderboard) {
                player_output.println(player[0] + "," + player[1]);
            }
            player_output.close(); //close file
        } catch (IOException e) {
            System.out.println("Could not write to file 'players'");
        }
    }

    // print leaderboard
    private static void printData(ArrayList<String[]> leaderboard) {
        if (leaderboard.size() > 0) {
            System.out.println("Leaderboard");
            System.out.println("Player Name -> Wins");
            for (String[] player: leaderboard) {
                System.out.println(player[0] + " -> " + player[1]);
            }
        }
    }
}