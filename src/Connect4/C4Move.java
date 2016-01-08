/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import Minimax.Move;

public class C4Move extends Move {

    private int x;
    private int y;
    private int piece;

    public C4Move(int x, int y, int piece){
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

    @Override
    public String toString(){
        return ""+Character.valueOf((char)('A' + x)) + (y+1);
    }

}
