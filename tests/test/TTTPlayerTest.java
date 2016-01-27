package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import Minimax.Player;
import TicTacToe.TTTBoard;
import TicTacToe.TTTGame;
import TicTacToe.TTTPlayer;

public class TTTPlayerTest {
	
	private TTTPlayer player;
	private TTTPlayer playerSpy;
	private TTTGame gameMock;

	@Before
	public void setUp() throws Exception {
		gameMock = mock(TTTGame.class);
		player = new TTTPlayer(1);
		playerSpy = new TTTPlayer(1);
	}

	@Test
	public void testMakeMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanMakeMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testUndoMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testDidIWin() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		TTTBoard boardMock = mock(TTTBoard.class);
		
		Field game = Player.class.getDeclaredField("game");
		game.setAccessible(true);
		game.set(player, gameMock);
		
		when(gameMock.getBoard()).thenReturn(boardMock);
		when(boardMock.getBoard()).thenReturn(new int[3][3]);
		
		// cannot private cluster fetch methods (I can only 
		// mock private objects by swapping them with mocks)
		player.didIWin();
	}

	@Test
	public void testPositionValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWINValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testInitialize() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateMoves() {
		
	}

	@Test
	public void testTTTPlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testCommitMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testCommitUndo() {
		fail("Not yet implemented");
	}

	@Test
	public void testYourTurn() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAi() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAi() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateMovesHTT() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsMyTurn() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOponent() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetOponent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsOKToUndo() {
		fail("Not yet implemented");
	}

	@Test
	public void testClearHash() {
		fail("Not yet implemented");
	}

	@Test
	public void testGameStopped() {
		fail("Not yet implemented");
	}

}
