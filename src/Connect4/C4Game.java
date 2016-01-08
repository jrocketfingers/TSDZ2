/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import Minimax.Game;


public class C4Game extends Game {
    
    public C4Game(C4Board board, C4Player white, C4Player black) {
        super(board, white, black);
    }

    public C4Game(C4SHBoard board, C4SHPlayer white, C4SHPlayer black) {
        super(board, white, black);
    }

    @Override
    public boolean isGameOver() {
        return white.didIWin() || black.didIWin() || moveCount==42;
    }

}
