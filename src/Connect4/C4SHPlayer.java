/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import Minimax.Move;

public class C4SHPlayer extends C4Player {


    public C4SHPlayer(int type){
        super(type);
        initialize();
    }

    @Override
    public void makeMove(Move move) {
        game.getBoard().addMove(move);
        int [][] board = ((C4Board)game.getBoard()).getBoard();
        C4Move m = (C4Move)move;
        int i = ((C4Board)game.getBoard()).getFreePos()[m.getX()];
        int j = m.getX();
        board[i][j] = piece;
        ((C4SHBoard)game.getBoard()).hash ^= ((C4SHBoard)game.getBoard()).zorbistArray[piece-1][i][j];
        ((C4Board)game.getBoard()).getFreePos()[m.getX()]++;
    }


    @Override
    public void undoMove(Move move) {
        game.getBoard().removeMove(move);
        C4Move m = (C4Move)move;
        ((C4Board)game.getBoard()).getFreePos()[m.getX()]--;
        int [][] board = ((C4Board)game.getBoard()).getBoard();
        int i = ((C4Board)game.getBoard()).getFreePos()[m.getX()];
        int j = m.getX();
        ((C4SHBoard)game.getBoard()).hash ^= ((C4SHBoard)game.getBoard()).zorbistArray[piece-1][i][j];
        board[i][j] = 0;
    }

    @Override
    public void initialize() {
        iWin = false;
        if (game!=null && game.getBoard()!=null){
            width = ((C4Board)game.getBoard()).WIDTH;
            height = ((C4Board)game.getBoard()).HEIGHT;
            board = ((C4Board)game.getBoard()).getBoard();
            freePos = ((C4Board)game.getBoard()).getFreePos();
        }
    }

   

}
