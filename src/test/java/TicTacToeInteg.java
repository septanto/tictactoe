import com.septanto.tddjava.ch3tictactoe.TicTacToe;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class TicTacToeInteg {
    @Test
    public void givenMongoDbIsRunningWhenPlayThenNoException()
            throws UnknownHostException {
        TicTacToe ticTacToe = new TicTacToe();
        assertEquals("No Winner", ticTacToe.play(1, 1));
    }
}
