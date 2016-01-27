package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import TicTacToe.TTTMove;

public class TTTMoveTest {
	TTTMove tttm;

	@Before
	public void setUp() throws Exception {
		this.tttm = new TTTMove(0, 1, 2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals(0, this.tttm.getX());
		assertEquals(1, this.tttm.getY());
		assertEquals(2, this.tttm.getPiece());
		
		assertEquals("", this.tttm.toString());
	}
}
