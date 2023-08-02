import java.io.File;
import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;

public class LeaderBoard{
    public LeaderBoard(){}
    // has 4 methods - readData, writeData, addPlayer and printData
    // readData and writeData are private methods for use within class only
    // addPlayer is public, adds a player given their name
    // printData is public, used to print the leaderboard


    //leaderboard will hold custom class player with attributes for name and wins
    protected static class Player {
        private String name;
        private int wins;
        protected Player(String name, int wins){
            this.name = name;
            this.wins = wins;
        }
        // Good coding practice bla bla getter functions
        protected String getName() {
            return this.name;
        }
        protected int getWins() {
            return this.wins;
        }
        protected void addWins() {
            this.wins += 1;
        }
    }

    // read file
    private static ArrayList<Player> readData() {
        ArrayList<Player> leaderboard = new ArrayList<>(); // start with an empty array to populate
        try {
            Scanner player_read = new Scanner(new File("players.txt"));
            while (player_read.hasNext()) {
                String[] current_player = player_read.nextLine().split(",");
                leaderboard.add(new Player(current_player[0], Integer.parseInt(current_player[1])));
            }
            player_read.close();
        } catch (IOException e) {
            System.out.println("No Players in leaderboard yet");
        }
        return leaderboard;
    }

    // write file
    private static void writeData(ArrayList<Player> leaderboard) {
        try {
            PrintWriter player_output = new PrintWriter("players.txt");
            for (Player p: leaderboard) {
                player_output.println(p.getName() + "," + p.getWins());
            }
            player_output.close(); //close file
        } catch (IOException e) {
            System.out.println("Could not write to file 'players'");
        }
    }

    //write file v2
    public static void addPlayer(String player_name) {
        ArrayList<Player> leaderboard = readData();
        String input = player_name.trim().toLowerCase();
        boolean existing_player = false;
        for (Player p : leaderboard) {
            if (Objects.equals(p.getName(), input)) {
                existing_player = true;
                p.addWins();
            }
        }
        if (!(existing_player)) {
            Player new_player = new Player(input, 1);
            leaderboard.add(new_player);
        }
        //sort the leaderboard based on wins
        Collections.sort(leaderboard, Comparator.comparing(Player::getWins));
        //descending order
        Collections.reverse(leaderboard);
        // Save leaderboard with new player
        writeData(leaderboard); // writeData method defined above
    }

    // print leaderboard
    public static void printData() {
        ArrayList<Player> leaderboard = readData();
        if (leaderboard.size() > 0) {
            System.out.println("Leaderboard");
            System.out.println("Player Name -> Wins");
            for (Player p: leaderboard) {
                System.out.println(p.getName() + " -> " + p.getWins());
            }
        }
    }
}