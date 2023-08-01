import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;

public class LeaderBoard{
    public LeaderBoard(){}
    // has 4 methods - readData, writeData, addPlayer and printData
    // readData and writeData are private methods for use within class only
    // addPlayer is public, adds a player given their name
    // printData is public, used to print the leaderboard

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

    //write file v2
    public static void addPlayer(String player_name) {
        ArrayList<String[]> leaderboard = readData();
        String input = player_name.trim().toLowerCase();
        boolean existing_player = false;
        for (String[] p : leaderboard) {
            if (Objects.equals(p[0], input)) {
                existing_player = true;
                // Turn p[1] to int, add 1, turn it back to string, reassign p[1]
                p[1] = String.valueOf(Integer.parseInt(p[1]) + 1);
            }
        }
        if (!(existing_player)) {
            String[] new_player = {input, "1"};
            leaderboard.add(new_player);
        }

        // Save leaderboard with new player
        writeData(leaderboard); // writeData method defined above
    }

    // print leaderboard
    public static void printData() {
        ArrayList<String[]> leaderboard = readData();
        if (leaderboard.size() > 0) {
            System.out.println("Leaderboard");
            System.out.println("Player Name -> Wins");
            for (String[] player: leaderboard) {
                System.out.println(player[0] + " -> " + player[1]);
            }
        }
    }
}