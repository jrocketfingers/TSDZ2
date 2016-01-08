package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Minimax.Move;
import TicTacToe.TTTBoard;
import TicTacToe.TTTMove;

public class TTTBoardTest {
	TTTBoard tttb;

	@Before
	public void setUp() throws Exception {
		this.tttb = new TTTBoard(3);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdateVisualBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testTTTBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDimension() {
		
	}

	@Test
	public void testSetDimension() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsFull() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetVBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMove() {
		this.testInitializeAndGetBoard();
		this.tttb.addMove(new TTTMove(0, 0, 0));
		assertEquals(1, this.tttb.getMoveCount());
	}

	@Test
	public void testRemoveMoveEmpty() {
		this.testInitializeAndGetBoard();
		this.tttb.removeMove(new TTTMove(0, 0, 0));
	}

	@Test
	public void testGetMoveCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMoveList() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateGameLog() {
		fail("Not yet implemented");
	}

	@Test
	public void testSignalGameOver() {
		fail("Not yet implemented");
	}

	@Test
	public void testInitializeAndGetBoard() {
		this.tttb.initialize();
		
		assertEquals(0, this.tttb.getMoveCount());
		assertEquals(new ArrayList<Move>(), this.tttb.getMoveList());

		int board[][] = this.tttb.getBoard();
		// defined in the setup
		for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
            	assertEquals(0, board[i][j]);
            }
		}
	}
	
	@Test
	public void testReinitialize() {
		this.testAddMove();
		this.testInitializeAndGetBoard();
	}

}
