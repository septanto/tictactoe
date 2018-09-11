package com.septanto.tddjava.ch3tictactoe;

import com.septanto.tddjava.ch3tictactoe.mongo.TicTacToeBean;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TicTacToeSpec {

    private TicTacToe ticTacToe;
    private TicTacToeCollection collection;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public final void before() {
        collection = mock(TicTacToeCollection.class);
        doReturn(true).when(collection).drop();
        doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));
        ticTacToe = new TicTacToe(collection);
    }

    @Test
    public void whenXOutsideBoardThenRuntimeException() {
        exception.expect(RuntimeException.class);
        ticTacToe.play(5,2);
    }

    @Test
    public void whenYOutsideBoardThenRuntimeException() {
        exception.expect(RuntimeException.class);
        ticTacToe.play(2, 5);
    }

    @Test
    public void whenOccupiedThenRuntimeException() {
        ticTacToe.play(2, 1);
        exception.expect(RuntimeException.class);
        ticTacToe.play(2, 1);
    }

    @Test
    public void givenFirstTurnWhenNextPlayerThenX() {
        assertEquals('X', ticTacToe.nextPlayer());
    }

    @Test
    public void givenLastTurnWasXWhenNextPlayerThenO() {
        ticTacToe.play(1, 1);
        assertEquals('O', ticTacToe.nextPlayer());
    }

    @Test
    public void whenPlayThenNoWinner() {
        String actual = ticTacToe.play(1, 1);
        assertEquals("No Winner", actual);
    }

    @Test
    public void whenPlayAndWholeHorizontalLineThenWinner() {
        ticTacToe.play(1, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 1); // X
        ticTacToe.play(2, 2); // O
        String actual = ticTacToe.play(3, 1); //X
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenPlayAndWholeVerticalLineThenWinner() {
        ticTacToe.play(2, 1); // X
        ticTacToe.play(1, 1); // O
        ticTacToe.play(3, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 2); // X
        String actual = ticTacToe.play(1, 3); // O
        assertEquals("O is the winner", actual);
    }

    @Test
    public void whenPlayAndTopBottomDiagonalLineThenWinner() {
        ticTacToe.play(1, 1);
        ticTacToe.play(1, 2);
        ticTacToe.play(2, 2);
        ticTacToe.play(1, 3);
        String actual = ticTacToe.play(3, 3);
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenPlayAndBottomTopDiagonalLineThenWinner() {
        ticTacToe.play(1, 3); // X
        ticTacToe.play(1, 1); // O
        ticTacToe.play(2, 2); // X
        ticTacToe.play(1, 2); // O
        String actual = ticTacToe.play(3, 1); // X
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenAllBoxesAreFilledThenDraw() {
        ticTacToe.play(1, 1);
        ticTacToe.play(1, 2);
        ticTacToe.play(1, 3);
        ticTacToe.play(2, 1);
        ticTacToe.play(2, 3);
        ticTacToe.play(2, 2);
        ticTacToe.play(3, 1);
        ticTacToe.play(3, 3);
        String actual = ticTacToe.play(3, 2);
        assertEquals("The result is draw", actual);
    }

    @Test
    public void whenInstantiatedThenSetCollection() {
        assertNotNull(ticTacToe.getTicTacToeCollection());
    }

    @Test
    public void whenPlayThenSaveMoveIsInvoked() {
        TicTacToeBean move = new TicTacToeBean(1, 1, 3, 'X');
        ticTacToe.play(move.getX(), move.getY());
        verify(collection).saveMove(move);
    }

    @Test
    public void whenPlayAndSaveReturnsFalseThenThrowException() {
        doReturn(false).when(collection).saveMove(any(TicTacToeBean.class));
        TicTacToeBean move = new TicTacToeBean(1, 2, 3, 'X');
        exception.expect(RuntimeException.class);
        ticTacToe.play(move.getX(), move.getY());
    }

    @Test
    public void whenPlayInvokedMultipleTimesThenTurnIncreases() {
        TicTacToeBean move1 = new TicTacToeBean(1, 1, 1, 'X');
        ticTacToe.play(move1.getX(), move1.getY());
        verify(collection, times(1)).saveMove(move1);
        TicTacToeBean move2 = new TicTacToeBean(2, 1, 2, 'O');
        ticTacToe.play(move2.getX(), move2.getY());
        verify(collection, times(1)).saveMove(move2);
    }

    @Test
    public void whenInstantiatedThenCollectionDrop() {
        verify(collection, times(1)).drop();
    }

    @Test
    public void whenInstantiatedAndDropReturnsFalseThenThrowException() {
        doReturn(false).when(collection).drop();
        exception.expect(RuntimeException.class);
        new TicTacToe(collection);
    }
}
