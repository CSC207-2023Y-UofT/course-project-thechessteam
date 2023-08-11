package presenter;


import presenterinterface.PresenterInterface;
import viewinterface.ViewInterface;

public class Presenter implements PresenterInterface {
    private ViewInterface view;
    private final long[][] pieceLocations = new long[2][6];
    public Presenter() {}

    public void setView(ViewInterface view) {
        this.view = view;
    }

    @Override
    public void updateLocations(int j, long bitboard, boolean color) {
        int i;
        if (color) {
            i = 0;
        } else {
            i = 1;
        }
        pieceLocations[i][j] = bitboard;
    }

    @Override
    public void setLocation() {
        view.setBoard(pieceLocations);
    }

    @Override
    public void setHighlight(long highlight) {
        view.setHighlights(highlight);
    }

    @Override
    public void setTurn(boolean currentTurn) {
        view.setTurn(currentTurn);
    }
}

