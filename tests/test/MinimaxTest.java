package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import org.mockito.Mockito;

import Minimax.AMinimax;
import Minimax.Minimax;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import TicTacToe.TTTGame;
import TicTacToe.TTTMove;
import TicTacToe.TTTPlayer;

public class MinimaxTest {
	Minimax m;
	Class minimaxClass;
	Class aMinimaxClass;
	
	@Before
	public void setUp() throws Exception {
		m = new Minimax();
		minimaxClass = Minimax.class;
		aMinimaxClass = AMinimax.class;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testFindBestMove() {
		ArrayList<TTTMove> moves = new ArrayList();
		moves.add(new TTTMove(0,0,1));
		
		TTTGame gameMock = Mockito.mock(TTTGame.class);
		TTTPlayer playerMock = Mockito.mock(TTTPlayer.class);
		
		m.findBestMove(gameMock, 1);
	}

	@Test
	public void testSetWinValue() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		m.setWinValue(1);
		
		Field winValue = aMinimaxClass.getDeclaredField("winValue");
		winValue.setAccessible(true);
		int value = (int) winValue.get(m);
		
		assertEquals(1, value);
	}

	@Test
	public void testResetWinValue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		m.setWinValue(1);
		m.resetWinValue();
		
		Field winValue = aMinimaxClass.getDeclaredField("winValue");
		winValue.setAccessible(true);
		int value = (int) winValue.get(m);
		
		assertEquals(0, value);
	}

	@Test
	public void testSetBoundsForAdditionalSearch() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		m.setBoundsForAdditionalSearch(10, 100);
		
		Field lowBound = aMinimaxClass.getDeclaredField("lowBound");
		lowBound.setAccessible(true);
		int valueLow = (int) lowBound.get(m);
		
		assertEquals(10, valueLow);
		
		Field highBound = aMinimaxClass.getDeclaredField("highBound");
		highBound.setAccessible(true);
		int valueHigh = (int) highBound.get(m);
		
		assertEquals(100, valueHigh);
	}

	@Test
	public void testResetBoundsForAdditionalSearch() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		m.setBoundsForAdditionalSearch(10, 100);
		
		m.resetBoundsForAdditionalSearch();
		
		Field lowBound = aMinimaxClass.getDeclaredField("lowBound");
		lowBound.setAccessible(true);
		int valueLow = (int) lowBound.get(m);
		
		assertEquals(0, valueLow);
		
		Field highBound = aMinimaxClass.getDeclaredField("highBound");
		highBound.setAccessible(true);
		int valueHigh = (int) highBound.get(m);
		
		assertEquals(0, valueHigh);
	}

	@Test
	public void testGetSetAdditionalDepth() {
		m.setAdditionalDepth(10);
		assertEquals(10, m.getAdditionalDepth());
	}

	@Test
	public void testSetTimeLimit() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		m.setTimeLimit(10);
		
		Field timeLimit = aMinimaxClass.getDeclaredField("timeLimit");
		timeLimit.setAccessible(true);
		int value = (int) timeLimit.get(m);
		
		assertEquals(10, value);
	}

	@Test
	public void testResetTimeLimit() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		m.setTimeLimit(10);
		m.resetTimeLimit();
		
		Field timeLimit = aMinimaxClass.getDeclaredField("timeLimit");
		timeLimit.setAccessible(true);
		int value = (int) timeLimit.get(m);
		
		assertEquals(0, value);
	}

	@Test
	public void testTimeIsUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		m.timeIsUp();
		
		Field timeIsUp = aMinimaxClass.getDeclaredField("timeIsUp");
		timeIsUp.setAccessible(true);
		Boolean value = (Boolean) timeIsUp.get(m);
		
		assertEquals(true, value);
	}

}
