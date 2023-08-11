package database;

import java.io.File;
import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;

public class LeaderBoard{
    public LeaderBoard(){}
    // has 5 methods - readData, writeData, addPlayer, sendData and printData
    // readData and writeData are private methods for use within class only
    // addPlayer is public, adds a player given their name
    // printData is public, used to print the leaderboard


    //leaderboard will hold custom class player with attributes for name and wins
    protected static class Player {
        private final String name;
        private int wins;
        protected Player(String name, int wins){
            this.name = name;
            this.wins = wins;
        }
        // Good coding practice blah blah getter functions
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
            // read file
            Scanner playerRead = new Scanner(new File("players.txt"));
            while (playerRead.hasNext()) {
                // split line by comma into name and wins
                String[] currentPlayer = playerRead.nextLine().split(",");
                // create new player with name and wins and add it to leaderboard
                leaderboard.add(new Player(currentPlayer[0], Integer.parseInt(currentPlayer[1])));
            }
            // close file
            playerRead.close();
        } catch (IOException e) {
            System.out.println("No Players in leaderboard yet");
        }
        return leaderboard;
    }

    // write file
    private static void writeData(ArrayList<Player> leaderboard) {
        try {
            // open players.txt to write in it
            PrintWriter playerOutput = new PrintWriter("players.txt");
            for (Player p: leaderboard) {
                // write each player in as you loop through it
                playerOutput.println(p.getName() + "," + p.getWins());
            }
            playerOutput.close(); //close file
        } catch (IOException e) {
            System.out.println("Could not write to file 'players'");
        }
    }

    // add player
    public static void addPlayer(String playerName) {
        // read file into leaderboard var
        ArrayList<Player> leaderboard = readData();
        // process name
        // check whether the player is already in leaderboard, add a win if it is
        boolean existingPlayer = false;
        for (Player p : leaderboard) {
            if (Objects.equals(p.getName(), playerName)) {
                existingPlayer = true;
                p.addWins();
            }
        }
        // if new player, create new player and add to the leaderboard
        if (!(existingPlayer)) {
            Player newPlayer = new Player(playerName, 1);
            leaderboard.add(newPlayer);
        }
        //sort the leaderboard based on wins
        leaderboard.sort(Comparator.comparing(Player::getWins));
        //descending order
        Collections.reverse(leaderboard);
        // Save leaderboard with new player
        writeData(leaderboard); // writeData method defined above
    }

    // Sends a List<String> representation of the leaderboard
    public static List<String> sendData() {
        // sends a copy of the leaderboard
        ArrayList<Player> leaderboard = readData();

        List<String> clonedList = new ArrayList<>();

        if (!leaderboard.isEmpty()) {
            // Adds the player and wins to the cloned list
            for (Player p: leaderboard) {
                clonedList.add(p.getName() + ": " + p.getWins());
            }
        }

        return clonedList;
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