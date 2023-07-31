package UseCases;

import Entities.ChessGame;
import Entities.LocationBitboard;

public class HighlightValid {
    public void create_highlight(long from) {
        LocationBitboard currentBoard = ChessGame.getCurrentBoard();
        boolean turn = ChessGame.getTurn();

        long highlight = get_highlight(from, turn, currentBoard);
        if (highlight != 0L) {
            // TODO Presenter.highlight_UI(highlight)
        }
    }

    public long get_highlight(long from, boolean turn, LocationBitboard board) {
        boolean canHighlight = false;
        if (turn) { // White's turn
            if ((from & board.getWhiteLocations()) != 0L) {
                canHighlight = true;
            }
        } else {
            if ((from & board.getBlackLocations()) != 0L) {
                canHighlight = true;
            }
        }

        if (canHighlight) {
            return ActualValidCalculator.actual_valid_moves(from, turn, board);
        } else {
            return 0L;
        }
    }
}