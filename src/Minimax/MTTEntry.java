/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;

import java.util.ArrayList;

/**
 *
 * @author Drazen Draskovic
 */

//Entry for transposition table that will keep generated moves data
public class MTTEntry {

    PositionHash hash;
    ArrayList<Move> moves;

    public MTTEntry(PositionHash hash, ArrayList<Move> moves){
        this.hash = hash;
        this.moves = moves;
    }

    public PositionHash getHash() {
        return hash;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }   

}
