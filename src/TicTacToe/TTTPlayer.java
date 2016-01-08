/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TicTacToe;

import java.util.ArrayList;
import Minimax.Move;
import Minimax.Player;

public class TTTPlayer extends Player {

    //1 for x (white), 2 for o (black)
    private int piece;
    private boolean myTurn;
    private boolean iWin;
    private final int WINVALUE = 1000;


    public TTTPlayer(int type){
        this.piece = type;
        initialize();
    }


    @Override
    public boolean didIWin() {
        int [][] board = ((TTTBoard)game.getBoard()).getBoard();
        int winLineLength = ((TTTGame)game).getWinClusterLength();
        int dim = board.length;

        return findHWinCluster(dim, board, winLineLength) ||
               findWWinCluster(dim, board, winLineLength) ||
               findRUWinCluster(dim, board, winLineLength) ||
               findRDWinCluster(dim, board, winLineLength);

    }

    @Override
    public ArrayList<Move> generateMoves() {
        int [][] board = ((TTTBoard)game.getBoard()).getBoard();
        int dim = board.length;
        ArrayList<Move> moveList = new ArrayList<Move>();

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if (board[i][j] == 0)
                    moveList.add(new TTTMove(i, j, piece));
            }
        }
        return moveList;
    }

    @Override
    public int getWINValue() {
        return WINVALUE;
    }

    @Override
    public void makeMove(Move move) {
        game.getBoard().addMove(move);
        int [][] board = ((TTTBoard)game.getBoard()).getBoard();
        TTTMove m = (TTTMove)move;
        board[m.getX()][m.getY()] = piece;
    }

    @Override
    public int positionValue() {
        int dim = ((TTTBoard)game.getBoard()).getDimension();
        int [][] b = ((TTTBoard)game.getBoard()).getBoard();

        return myPositionValue() -
               ((TTTPlayer)getOponent()).myPositionValue() +
               coordValue(dim, b);
    }

    private int myPositionValue(){
        int [][] board = ((TTTBoard)game.getBoard()).getBoard();
        int winLineLength = ((TTTGame)game).getWinClusterLength();
        int dim = board.length;

        if (findHWinCluster(dim, board, winLineLength)) return WINVALUE;
        if (findWWinCluster(dim, board, winLineLength)) return WINVALUE;
        if (findRUWinCluster(dim, board, winLineLength)) return WINVALUE;
        if (findRDWinCluster(dim, board, winLineLength)) return WINVALUE;
        return 0;
    }

    @Override
    public void undoMove(Move move) {
        game.getBoard().removeMove(move);
        int [][] board = ((TTTBoard)game.getBoard()).getBoard();
        TTTMove m = (TTTMove)move;
        board[m.getX()][m.getY()] = 0;
    }

    @Override
    public void initialize() {
        myTurn = piece == 1 ? true : false;
        iWin = false;
    }

    private boolean findHWinCluster(int dim, int[][] b, int winLineLength) {
        for (int i = 0; i < dim; i++) {
            boolean inCluster = false;
            int clusterSize = 0;
            for (int j = 0; j < dim; j++) {
                if (b[i][j] == piece) {
                    if (inCluster) {
                        clusterSize++;
                        if (clusterSize == winLineLength) {
                            return true;
                        }
                    } else {
                        inCluster = true;
                        clusterSize = 1;
                    }
                } else {
                    inCluster = false;
                    clusterSize = 0;
                }
            }
        }
        return false;
    }

    private boolean findRDWinCluster(int dim, int[][] b, int winLineLength) {
        int diagCount = (dim - winLineLength) * 2 + 1;

        for (int i = 0; i < diagCount; i++) {
            int x = (i <= diagCount / 2) ? 0 : i - (diagCount / 2);
            int y = (i <= diagCount / 2) ? (diagCount / 2) - i : 0;
            boolean inCluster = false;
            int clusterSize = 0;
            while (x < dim && y < dim) {
                if (b[x][y] == piece) {
                    if (inCluster) {
                        clusterSize++;
                        if (clusterSize == winLineLength) {
                            return true;
                        }
                    } else {
                        inCluster = true;
                        clusterSize = 1;
                    }
                } else {
                    inCluster = false;
                    clusterSize = 0;
                }
                x++;
                y++;
            }
        }
        return false;
    }

    private boolean findRUWinCluster(int dim, int[][] b, int winLineLength) {
        int diagCount = (dim - winLineLength) * 2 + 1;

        for (int i = 0; i < diagCount; i++) {
            int x = (i <= diagCount / 2) ? 0 : i - diagCount / 2;
            int y = (i <= diagCount / 2) ? dim - diagCount / 2 - 1 + i : dim - 1;
            boolean inCluster = false;
            int clusterSize = 0;
            while (x < dim && y >= 0) {
                if (b[x][y] == piece) {
                    if (inCluster) {
                        clusterSize++;
                        if (clusterSize == winLineLength) {
                            return true;
                        }
                    } else {
                        inCluster = true;
                        clusterSize = 1;
                    }
                } else {
                    inCluster = false;
                    clusterSize = 0;
                }
                x++;
                y--;
            }
        }
        return false;
    }

    private boolean findWWinCluster(int dim, int[][] b, int winLineLength) {
        for (int j = 0; j < dim; j++) {
            boolean inCluster = false;
            int clusterSize = 0;
            for (int i = 0; i < dim; i++) {
                if (b[i][j] == piece) {
                    if (inCluster) {
                        clusterSize++;
                        if (clusterSize == winLineLength) {
                            return true;
                        }
                    } else {
                        inCluster = true;
                        clusterSize = 1;
                    }
                } else {
                    inCluster = false;
                    clusterSize = 0;
                }
            }
        }
        return false;
    }

    private int coordValue(int dim, int b[][]) {

        int result = 0;
        if (dim%2 == 1){
            for(int i = 0; i < dim; i++)
                for(int j = 0; j < dim; j++){
                    if (b[i][j] == piece){
                        int x = Math.abs(i-dim/2)+1;
                        int y = Math.abs(j-dim/2)+1;
                        result -= x*x + y*y;
                    }
                }
        }
        else {
            for(int i = 0; i < dim/2; i++)
                for(int j = 0; j < dim/2; j++)
                    if (b[i][j] == piece){
                        int x = Math.abs(i-dim/2+1)+1;
                        int y = Math.abs(j-dim/2+1)+1;
                        result -= x*x + y*y;
                    }
            for(int i = 0; i < dim/2; i++)
                for(int j = dim/2; j < dim; j++)
                    if (b[i][j] == piece){
                        int x = Math.abs(i-dim/2+1)+1;
                        int y = Math.abs(j-dim/2)+1;
                        result -= x*x + y*y;
                    }
            for(int i = dim/2; i < dim; i++)
                for(int j = 0; j < dim/2; j++)
                    if (b[i][j] == piece){
                        int x = Math.abs(i-dim/2)+1;
                        int y = Math.abs(j-dim/2+1)+1;
                        result -= x*x + y*y;
                    }
            for(int i = dim/2; i < dim; i++)
                for(int j = dim/2; j < dim; j++)
                    if (b[i][j] == piece){
                        int x = Math.abs(i-dim/2)+1;
                        int y = Math.abs(j-dim/2)+1;
                        result -= x*x + y*y;
                    }
        }
        return result;
    }

    @Override
    public boolean canMakeMove(Move move) {
        throw new UnsupportedOperationException("Not supported yet.");
    }






}
