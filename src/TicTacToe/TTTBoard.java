/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TicTacToe;

import Minimax.Board;
import Minimax.PositionCode;
import Minimax.Game;
import Minimax.PositionHash;

public class TTTBoard extends Board{

    private int dimension;
    private int [][] board;
    private Game game;
    private Updateable vBoard = null;

    public TTTBoard(int dimension){
        this.dimension = dimension;
    }

    @Override
    public void initialize(){
        if (board == null || board.length != dimension)
            board = new int[dimension][dimension];
        for(int i = 0; i < dimension; i++)
            for(int j = 0; j < dimension; j++)
                board[i][j] = 0;
    }

    @Override
    public PositionCode getPositionCode() {
        int code = 0;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                code *= 4;
                code += board[i][j];
            }
            
        }
        return new TTTCode(code);
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean isFull(){
        return game.getMoveCount() == dimension*dimension;
    }

    public void setGame(Game game){
        this.game = game;
    }

    @Override
    public void updateVisualBoard() {
        if (vBoard!=null) vBoard.updateState();
    }

    public void setVBoard(Updateable vBoard) {
        this.vBoard = vBoard;
    }

    @Override
    public PositionHash getPositionHash() {
        throw new UnsupportedOperationException("Not supported yet.");
    }







}
