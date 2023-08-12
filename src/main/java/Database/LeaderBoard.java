package Database;

import java.io.File;
import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * LeaderBoard class manages the leaderboard functionality for a game, including reading and writing
 * player data to a file, adding new players, and printing the leaderboard. It stores players as a
 * custom class with attributes for name and wins.
 *
 */
public class LeaderBoard{
    public LeaderBoard(){}
    // has 5 methods - readData, writeData, addPlayer, sendData and printData
    // readData and writeData are private methods for use within class only
    // addPlayer is public, adds a player given their name
    // printData is public, used to print the leaderboard


    /**
     * Represents a Player with a name and a number of wins.
     */
    protected static class Player {
        private final String name;
        private int wins;

        /**
         * Constructs a new Player object with the given name and wins.
         *
         * @param name The name of the player.
         * @param wins The number of wins for the player.
         */
        protected Player(String name, int wins){
            this.name = name;
            this.wins = wins;
        }

        /**
         * Retrieves the name of the player.
         *
         * @return The player's name.
         */
        protected String getName() {
            return this.name;
        }

        /**
         * Retrieves the number of wins for the player.
         *
         * @return The player's number of wins.
         */
        protected int getWins() {
            return this.wins;
        }

        /**
         * Increments the player's win count by 1.
         */
        protected void addWins() {
            this.wins += 1;
        }
    }

    /**
     * Reads the player data from the file "players.txt" and returns an ArrayList of Player objects.
     *
     * @return An ArrayList containing Player objects representing the current leaderboard.
     */
    private static ArrayList<Player> readData() {
        ArrayList<Player> leaderboard = new ArrayList<>(); // start with an empty array to populate
        try {
            // read file
            Scanner player_read = new Scanner(new File("players.txt"));
            while (player_read.hasNext()) {
                // split line by comma into name and wins
                String[] current_player = player_read.nextLine().split(",");
                // create new player with name and wins and add it to leaderboard
                leaderboard.add(new Player(current_player[0], Integer.parseInt(current_player[1])));
            }
            // close file
            player_read.close();
        } catch (IOException e) {
            System.out.println("No Players in leaderboard yet");
        }
        return leaderboard;
    }

    /**
     * Writes the given ArrayList of Player objects to the file "players.txt."
     *
     * @param leaderboard The ArrayList of Player objects to write to the file.
     */
    private static void writeData(ArrayList<Player> leaderboard) {
        try {
            // open players.txt to write in it
            PrintWriter player_output = new PrintWriter("players.txt");
            for (Player p: leaderboard) {
                // write each player in as you loop through it
                player_output.println(p.getName() + "," + p.getWins());
            }
            player_output.close(); //close file
        } catch (IOException e) {
            System.out.println("Could not write to file 'players'");
        }
    }

    /**
     * Adds a new player to the leaderboard or increments the win count of an existing player.
     * The leaderboard is sorted by wins in descending order.
     *
     * @param player_name The name of the player to add or update.
     */
    public static void addPlayer(String player_name) {
        // read file into leaderboard var
        ArrayList<Player> leaderboard = readData();
        // process name
        // check whether the player is already in leaderboard, add a win if it is
        boolean existing_player = false;
        for (Player p : leaderboard) {
            if (Objects.equals(p.getName(), player_name)) {
                existing_player = true;
                p.addWins();
            }
        }
        // if new player, create new player and add to the leaderboard
        if (!(existing_player)) {
            Player new_player = new Player(player_name, 1);
            leaderboard.add(new_player);
        }
        //sort the leaderboard based on wins
        leaderboard.sort(Comparator.comparing(Player::getWins));
        //descending order
        Collections.reverse(leaderboard);
        // Save leaderboard with new player
        writeData(leaderboard); // writeData method defined above
    }

    /**
     * Sends a List<String> representation of the leaderboard
     */
    public static List<String> sendData() {
        // sends a copy of the leaderboard
        ArrayList<Player> leaderboard = readData();

        List<String> cloned_list = new ArrayList<>();

        if (!leaderboard.isEmpty()) {
            // Adds the player and wins to the cloned list
            for (Player p: leaderboard) {
                cloned_list.add(p.getName() + ": " + p.getWins());
            }
        }

        return cloned_list;
    }

    /*
    // print leaderboard (was used for testing)
    public static void printData() {
        // read file
        ArrayList<Player> leaderboard = readData();
        if (!leaderboard.isEmpty()) {
            // print each player and wins
            for (Player p: leaderboard) {
                System.out.println(p.getName() + " -> " + p.getWins());
            }
        }
    } */
}