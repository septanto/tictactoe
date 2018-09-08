package com.septanto.tddjava.ch3tictactoe;

import org.junit.Test;

import java.net.UnknownHostException;

import static junit.framework.TestCase.assertEquals;

public class TicTacToeCollectionSpec {
    @Test
    public void whenInstantiatedThenMongoHasDbNameTicTacToe() throws UnknownHostException {
        TicTacToeCollection collection = new TicTacToeCollection();
        assertEquals("tic-tac-toe", collection.getMongoCollection().getDBCollection().getDB().getName());
    }
}
