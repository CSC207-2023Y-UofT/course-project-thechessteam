import org.junit.Assert.assertEquals;
import org.junit.Test;
public class PawnTests {
    // valid_move tests

    // initial spot two move ahead
    // not initial spot two move ahead(shouldnt work)
    // initial spot one move forward
    // initial spot sideways capture
    // corner row sideways capture(shouldnt work)
    // last row(no valid moves)

    // cant cross enemy champ
    // doesnt have sideways if theres nothing to capture
    // doesnt have forward if enemy there

    // normal, initial spot, no enemy
    // not initial spot no enemy
    // not initial spot double capture target
    // initial spot, enemy right in front
    // corner spot
    // last row

    long from = 0b0000000000000000000000000000000000000000000000001111111100000000L;
    @Test
    public long valid_moves(){ //this name can be anything
        Pawn test_pawn = new Pawn();
        long test_moves = valid_moves(from, true, board);
        long expected_moves = ;
        Assertions.assertEquals(expected_moves, test_moves);
    }
    @Test
    public long valid_moves(){ //this name can be anything
        Pawn test_pawn = new Pawn();
        long test_moves = valid_moves(from, true, board);
        long expected_moves = ;
        Assertions.assertEquals(expected_moves, test_moves);
    }
}