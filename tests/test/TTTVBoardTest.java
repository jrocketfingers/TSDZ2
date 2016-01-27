package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import TicTacToe.TTTBoard;
import TicTacToe.TTTGame;
import TicTacToe.TTTVBoard;

public class TTTVBoardTest {
	TTTVBoard tttvb;
	TTTGame gameMock;
	TTTBoard boardMock;
	int cellSize = 100;

	@Before
	public void setUp() throws Exception {
		boardMock = mock(TTTBoard.class);
		gameMock = mock(TTTGame.class);
		when(gameMock.getBoard()).thenReturn(boardMock);
		
		tttvb = new TTTVBoard(gameMock, cellSize);
	}

	@Test
	public void testTTTVBoardTTTGame() {
		TTTVBoard tttvb = new TTTVBoard(gameMock);
		assertEquals(30, tttvb.getCellSize());
	}

	@Test
	public void testTTTVBoardTTTGameInt() {
		TTTVBoard tttvb = new TTTVBoard(gameMock, 100);
		assertEquals(100, tttvb.getCellSize());
	}

	@Test
	public void testPaintGraphics() {
		fail("Tested with abbot.");
	}

	@Test
	public void testGetCellSize() {
		assertEquals(cellSize, tttvb.getCellSize());
	}

	@Test
	public void testSetCellSize() {
		tttvb.setCellSize(101);
		assertEquals(101, tttvb.getCellSize());
	}

	@Test
	public void testSetBlackPlayerEnabled() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		tttvb.setBlackPlayerEnabled(true);
		
		Field blackPlayerEnabled = TTTVBoard.class.getDeclaredField("blackPlayerEnabled");
		blackPlayerEnabled.setAccessible(true);
		boolean value = (boolean) blackPlayerEnabled.get(tttvb);
		
		assertEquals(true, value);
	}

	@Test
	public void testSetWhitePlayerEnabled() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		tttvb.setWhitePlayerEnabled(true);
		
		Field whitePlayerEnabled = TTTVBoard.class.getDeclaredField("whitePlayerEnabled");
		whitePlayerEnabled.setAccessible(true);
		boolean value = (boolean) whitePlayerEnabled.get(tttvb);
		
		assertEquals(true, value);
	}

	@Test
	public void testUpdateState() {
		fail("Tested with abbot.");
	}

}
