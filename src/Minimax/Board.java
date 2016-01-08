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
public abstract class Board {

    private int moveCount;
    private ArrayList<Move> moveList = new ArrayList<Move>();

    public void addMove(Move move){
        moveList.add(move);
        moveCount++;
    }

    public void removeMove(Move move){
        moveList.remove(moveCount-1);
        moveCount--;
    }

    public PositionCode getPositionCode(){
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public PositionHash getPositionHash(){
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void initialize(){
        moveCount = 0;
        moveList.clear();
    }

    public int getMoveCount() {
        return moveCount;
    }

    public ArrayList<Move> getMoveList() {
        return moveList;
    }

    public void updateVisualBoard(){}

    public void updateGameLog(ArrayList<String> log){}

    public void signalGameOver(){}
}
