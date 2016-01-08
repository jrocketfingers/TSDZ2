/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TicTacToe;

import Minimax.Move;

public class TTTMove extends Move {

    private int x;
    private int y;
    private int piece;

    public TTTMove(int x, int y, int piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPiece() {
        return piece;
    }

}
