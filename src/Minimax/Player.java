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
public abstract class Player {

    protected Game game;
    protected Player oponent;
    protected AMinimax ai = null;

    public abstract void makeMove(Move move);

    public abstract boolean canMakeMove(Move move);

    public void commitMove(){
        game.moveCommit();
    }

    public abstract void undoMove(Move move);

    public void commitUndo(){}
    
    public void yourTurn() {
        if (ai!=null){
            new AIThread(ai, game, 11, this).start();
        }
    }

    public AMinimax getAi() {
        return ai;
    }

    public void setAi(AMinimax ai) {
        this.ai = ai;
    }

    public abstract boolean didIWin();

    public abstract int positionValue();

    public abstract int getWINValue();

    public void initialize(){}

    public abstract ArrayList<Move> generateMoves();

    public ArrayList<Move> generateMovesHTT(){
        return generateMoves();
    }

    public boolean isMyTurn(){
        return this == game.getPlayerOnTurn();
    }

    public Player getOponent() {
        return oponent;
    }

    public void setOponent(Player oponent) {
        this.oponent = oponent;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isOKToUndo(){
        return ai==null || (ai!=null && game.isGameOver());
    }

    public void clearHash(){
        
    }

    public void gameStopped(){}

    




}
