package Use_Cases;

import Entities.ChessGame;

public class NewGame {
    private ChessGame currentGame;
    public NewGame(ChessGame currentGame) {
        this.currentGame = currentGame;
    }
    public void start_new_game() {
        currentGame.new_game();
    }
}
