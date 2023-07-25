import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;

public class LeaderBoard{
    // has 3 mthods excluding main - readData, writedata and printData
    public static void main(String[] args){
        // Read existing leaderboard
        ArrayList<String> leaderboard = readData(); // readData method defined below
        printData(leaderboard);   // printList method defined below

        // Add new player
        Scanner input = new Scanner(System.in); //add small intro about scanner class
        System.out.print("Please enter Player name: "); // adjust this with gui
        leaderboard.add(input.nextLine()); // more work happens here, which I will add later

        // Save leaderboard with new player
        writeData(leaderboard); // writeData method defined below
    }


    // write file
    private static void writeData(ArrayList<String> players) {
        try {
            PrintWriter player_output = new PrintWriter("players.txt");
            for (String player: players) {
                player_output.println(player);
            }
            player_output.close(); //close file
        } catch (IOException e) {
            System.out.println("Could not write to file 'players'");
        }
    }

    // read file
    private static ArrayList<String> readData() {
        ArrayList<String> leaderboard = new ArrayList<String>(); // start with an empty array to populate
        try {
            Scanner player_read = new Scanner(new File("players.txt"));
            while (player_read.hasNext()) {
                leaderboard.add(player_read.nextLine());
            }
            players_read.close();
        } catch (IOException e) {
            System.out.println("No Players in leaderboard yet");
        }
        return leaderboard;
    }

    // print leaderboard
    private static void printData(ArrayList<String> leaderboard) {
        if (leaderboard.size > 0) {
            System.out.println("Leaderboard");
            for (String player: leaderboard) {
                System.out.println(player);
            }
        }
    }
}