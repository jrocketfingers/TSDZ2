package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Minimax.PositionCode;
import TicTacToe.TTTCode;

public class TTTCodeTest {
	TTTCode tttc;
	
	@Before
	public void setUp() throws Exception {
		tttc = new TTTCode(100);
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, tttc.compareTo(new TTTCode(100)));
		assertEquals(1, tttc.compareTo(new TTTCode(99)));
		assertEquals(-1, tttc.compareTo(new TTTCode(101)));
	}

	@Test
	public void testGetCode() {
		assertEquals(100, tttc.getCode());
	}

}
