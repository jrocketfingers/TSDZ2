/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TicTacToe;

import Minimax.Game;

public class TTTGame extends Game {

    private int winClusterLength;

    public TTTGame(TTTBoard board, TTTPlayer white, TTTPlayer black, int winClusterLength){
        super(board, white, black);
        this.winClusterLength = winClusterLength;
    }

    @Override
    public boolean isGameOver() {

        return white.didIWin() || black.didIWin() || ((TTTBoard)board).isFull();
    }

    public int getWinClusterLength() {
        return winClusterLength;
    }

}
