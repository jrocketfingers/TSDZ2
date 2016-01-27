package test;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Ignore
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TicTacToeTest.class,
	TTTBoardTest.class
})
public class AllTests {
	
}
