package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.GreaterOrEqual;

import Minimax.Game;
import Minimax.Move;
import Minimax.Player;
import TicTacToe.TTTBoard;
import TicTacToe.TTTGame;
import TicTacToe.TTTMove;
import TicTacToe.TTTPlayer;

public class TTTGameTest {
	TTTGame game;
	TTTGame gameSpy;
	TTTPlayer whiteMock;
	TTTPlayer blackMock;
	TTTBoard boardMock;
	
	ArrayList<Move> movesMock;

	@Before
	public void setUp() throws Exception {
		whiteMock = mock(TTTPlayer.class);
		blackMock = mock(TTTPlayer.class);
		boardMock = mock(TTTBoard.class);
		
		movesMock = new ArrayList<>();
		movesMock.add(new TTTMove(0,0,0));
		
		game = new TTTGame(boardMock, whiteMock, blackMock, 100);
		gameSpy = spy(new TTTGame(boardMock, whiteMock, blackMock, 100));
	}

	@Test
	public void testGetWinClusterLength() {
		assertEquals(100, game.getWinClusterLength());
	}
	
	@Test
	public void testIsGameOverWhite() {
		when(whiteMock.didIWin()).thenReturn(true);
		assertEquals(true, game.isGameOver());
	}
	
	@Test
	public void testIsGameOverBlack() {
		when(blackMock.didIWin()).thenReturn(true);
		assertEquals(true, game.isGameOver());
	}
	
	@Test
	public void testIsGameOverFull() {
		when(boardMock.isFull()).thenReturn(true);
		assertEquals(true, game.isGameOver());
	}
	
	@Test
	public void testIsGameOverNot() {
		when(whiteMock.didIWin()).thenReturn(false);
		when(blackMock.didIWin()).thenReturn(false);
		when(boardMock.isFull()).thenReturn(false);
		assertEquals(false, game.isGameOver());
	}
	
	@Test
	public void testSetGetBlack() {
		TTTPlayer black = mock(TTTPlayer.class);
		game.setBlack(black);
		
		assertEquals(black, game.getBlack());
	}
	
	@Test
	public void testSetGetWhite() {
		TTTPlayer white = mock(TTTPlayer.class);
		game.setWhite(white);
		
		assertEquals(white, game.getWhite());
	}
	
	@Test
	public void testSetGetBoard() {
		TTTBoard board = mock(TTTBoard.class);
		game.setBoard(board);
		
		assertEquals(board, game.getBoard());
	}
	
	@Test
	public void testStartGame() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		game = spy(new TTTGame(boardMock, whiteMock, blackMock, 100));
		game.startGame();
		
		verify(game).stopGame();
		assertEquals(0, game.getMoveCount());
		assertEquals(whiteMock, game.getPlayerOnTurn());
		assertEquals(whiteMock, game.getPlayerOnPTurn());
		
		Field undoCount = Game.class.getDeclaredField("undoCount");
		undoCount.setAccessible(true);
		int value = (int) undoCount.get(game);
		assertEquals(0, value);
		
		Field log = Game.class.getDeclaredField("log");
		log.setAccessible(true);
		ArrayList<String> valueArray = (ArrayList) log.get(game);
		assertEquals(0, valueArray.size());
		
		verify(boardMock).initialize();
		verify(whiteMock).initialize();
		verify(blackMock).initialize();
	}
	
	@Test
	public void testMoveMade() {
		game.moveMade();
		
		assertEquals(1, game.getMoveCount());
		assertEquals(false, game.isWhitesTurn());
	}
	
	@Test
	public void testMoveUndo() {
		game.moveUndo();
		assertFalse(game.getMoveCount() < 0);
		
		// Code contains incorrect responsibilities.
		// Move undo should consider the scenario of undoing with 0 moves,
		// and raise an error accordingly. Currently this is done in the
		// moveCommit method.
		// It's better to beg for forgiveness than to ask for permission.
		game.moveMade();
		assertEquals(1, game.getMoveCount());
		
		game.moveUndo();
		assertEquals(0, game.getMoveCount());
		
		assertEquals(true, game.isWhitesTurn());
	}
	
	@Test
	public void testUndoMove() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ArrayList<String> logMock = mock(ArrayList.class);

		when(gameSpy.isWhitesPTurn()).thenReturn(true);
		when(gameSpy.getMoveCount()).thenReturn(1);
		when(whiteMock.isOKToUndo()).thenReturn(true);
		when(boardMock.getMoveList()).thenReturn(movesMock);
		
		Field log = Game.class.getDeclaredField("log");
		log.setAccessible(true);
		log.set(gameSpy, logMock);
		
		gameSpy.undoMove(1, true);
		verify(whiteMock, atLeastOnce()).undoMove(any(TTTMove.class));
		verify(logMock, atLeastOnce()).remove(anyInt());
		
		gameSpy.undoMove(1, false);
		verify(blackMock, atLeastOnce()).undoMove(any(TTTMove.class));
		verify(logMock, atLeastOnce()).remove(anyInt());
	}
	
	@Test
	public void testMoveCommit() {		
		game.moveCommit();
		
		assertEquals(1, game.getMoveCount());
		assertFalse(game.isWhitesTurn());
		assertFalse(game.isWhitesPTurn());
		
		verify(boardMock, atLeastOnce()).updateVisualBoard();
		verify(boardMock, atLeastOnce()).updateGameLog(any(ArrayList.class));
		
		when(gameSpy.isGameOver()).thenReturn(true);
		gameSpy.moveCommit();
		verify(boardMock).signalGameOver();
	}
}
