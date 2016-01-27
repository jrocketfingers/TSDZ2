package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Minimax.Move;
import Minimax.PositionCode;
import TicTacToe.TTTBoard;
import TicTacToe.TTTGame;
import TicTacToe.TTTMove;

public class TTTBoardTest {
	TTTBoard tttb;
	int dimension = 3;

	@Before
	public void setUp() throws Exception {
		this.tttb = new TTTBoard(dimension);
		this.tttb.initialize();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTTTBoard() {
		
	}

	@Test
	public void testAddMove() {
		this.tttb.addMove(new TTTMove(0, 0, 0));
		assertEquals(1, this.tttb.getMoveCount());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testRemoveMoveEmpty() {
		this.tttb.removeMove(new TTTMove(0, 0, 0));
	}
	
	@Test
	public void testRemoveMove() {
		TTTMove tttm = new TTTMove(0,0,0);
		this.tttb.addMove(tttm);
		this.tttb.removeMove(tttm);
		assertEquals(new ArrayList<TTTMove>(), tttb.getMoveList());
	}
	
	@Test
	public void testInitialize() {
		assertEquals(0, this.tttb.getMoveCount());
		assertEquals(new ArrayList<Move>(), this.tttb.getMoveList());
	}

	@Test
	public void testInitializeAndGetBoard() {
		assertArrayEquals(new int[dimension][dimension], this.tttb.getBoard());
	}
	
	@Test
	public void testReinitialize() {
		this.testAddMove();
		this.tttb.initialize();
		this.testInitialize();
		// Error detected: doesn't call the initialize method in the superclass
	}
	
	@Test
	public void testGetPositionCode() {
		PositionCode value = tttb.getPositionCode();
		fail("No specification given.");
	}
	
	@Test
	public void testGetPositionHash() {
		tttb.getPositionHash();
		fail("No specification given.");
	}
	
	@Test
	public void testSetGetDimension() {
		tttb.setDimension(10);
		assertEquals(10, tttb.getDimension());
	}

	@Test
	public void testIsFull() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		TTTGame gameMock = mock(TTTGame.class);
		when(gameMock.getMoveCount()).thenReturn(dimension*dimension);
		
		Field game = TTTBoard.class.getDeclaredField("game");
		game.setAccessible(true);
		game.set(tttb, gameMock);
		
		assertEquals(true, tttb.isFull());
	}
	
	@Test
	public void testIsNotFull() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		TTTGame gameMock = mock(TTTGame.class);
		when(gameMock.getMoveCount()).thenReturn(0);
		
		Field game = TTTBoard.class.getDeclaredField("game");
		game.setAccessible(true);
		game.set(tttb, gameMock);
		
		assertEquals(false, tttb.isFull());
	}
	
	@Test
	public void testSetGame() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		TTTGame gameMock = mock(TTTGame.class);
		tttb.setGame(gameMock);
		
		Field game = TTTBoard.class.getDeclaredField("game");
		game.setAccessible(true);
		
		assertEquals(gameMock, game.get(tttb));
	}
	
	@Test
	public void testUpdateVisualBoard() throws NoSuchFieldException, SecurityException {
		// Updateable vBoard = mock(Updateable.class);
		fail("The updateable interface is not public.");
		
		Field vBoard = TTTBoard.class.getDeclaredField("vBoard");
	}
	
	@Test
	public void testSetVBoard() {	
		fail("The updateable interface is not public.");
	}
}
