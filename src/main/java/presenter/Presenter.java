package presenter;


import presenterinterface.PresenterInterface;
import viewinterface.ViewInterface;

public class Presenter implements PresenterInterface {
    private ViewInterface view;
    private final long[][] pieceLocations = new long[2][6];
    public Presenter() {}

    public void set_view(ViewInterface view) {
        this.view = view;
    }

    @Override
    public void update_locations(int j, long bitboard, boolean color) {
        int i;
        if (color) {
            i = 0;
        } else {
            i = 1;
        }
        pieceLocations[i][j] = bitboard;
    }

    @Override
    public void set_location() {
        view.setBoard(pieceLocations);
    }

    @Override
    public void set_highlight(long highlight) {
        view.setHighlights(highlight);
    }

    @Override
    public void set_turn(boolean currentTurn) {
        view.setTurn(currentTurn);
    }
}

