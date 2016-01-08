/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import java.util.logging.Level;
import java.util.logging.Logger;


public class SignalGameOver extends Thread {

    C4Game game;
    C4VBoard vBoard;

    int WINLINELENGTH = 4;

    SignalGameOver(C4Game game, C4VBoard vBoard) {
        this.game = game;
        this.vBoard = vBoard;
    }

    @Override
    public void run() {

        int [][] board = ((C4Board)game.getBoard()).getOutputBoard();
        int [] topPos = ((C4Board)game.getBoard()).getFreePos();

        int dir = 0;
        int i = 5;
        int j = 5;
        int result;

        boolean found = false;

        result = findHWinCluster(board, 1);
        if (result != -1) {
            found = true;
            dir = result / 100;
            i = result / 10 % 10;
            j = result % 10;
        }

        if (!found){
            result = findHWinCluster(board, 2);
            if (result != -1) {
                found = true;
                dir = result / 100;
                i = result / 10 % 10;
                j = result % 10;
            }
        }

        if (!found){
            result = findVWinCluster(board, 1);
            if (result != -1) {
                found = true;
                dir = result / 100;
                i = result / 10 % 10;
                j = result % 10;
            }
        }

        if (!found){
            result = findVWinCluster(board, 2);
            if (result != -1) {
                found = true;
                dir = result / 100;
                i = result / 10 % 10;
                j = result % 10;
            }
        }

        if (!found){
            result = findRDCluster(board, 1);
            if (result != -1) {
                found = true;
                dir = result / 100;
                i = result / 10 % 10;
                j = result % 10;
            }
        }

        if (!found){
            result = findRDCluster(board, 2);
            if (result != -1) {
                found = true;
                dir = result / 100;
                i = result / 10 % 10;
                j = result % 10;
            }
        }

        if (!found){
            result = findRUWinCLuster(board, 1);
            if (result != -1) {
                found = true;
                dir = result / 100;
                i = result / 10 % 10;
                j = result % 10;
            }
        }

        if (!found){
            result = findRUWinCLuster(board, 2);
            if (result != -1) {
                found = true;
                dir = result / 100;
                i = result / 10 % 10;
                j = result % 10;
            }
        }

        if (!found) return;
        
        int [] ii = iIndexes(i, j, dir);
        int [] jj = jIndexes(i, j, dir);

        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(SignalGameOver.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread.yield();

        for(int p = 0; p < 3; p++){
            int piece = board[ii[0]][jj[0]];

            for(int q = 0; q < 4; q++){
                board[ii[q]][jj[q]] = 0;
            }

            vBoard.updateState();

            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                Logger.getLogger(SignalGameOver.class.getName()).log(Level.SEVERE, null, ex);
            }

            for(int q = 0; q < 4; q++){
                board[ii[q]][jj[q]] = piece;
            }

            vBoard.updateState();

            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                Logger.getLogger(SignalGameOver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        vBoard.setSignal(false);
    }

    private int findHWinCluster(int [][] board, int piece) {
        for(int i = 0; i < board.length; i++){

            boolean inCluster = false;
            int clusterSize = 0;

            for(int j = 0; j < board[i].length; j++){
                if (board[i][j] == piece){
                    if (inCluster){
                        clusterSize++;
                        if (clusterSize == WINLINELENGTH) return i*10+j;
                    }
                    else {
                        inCluster = true;
                        clusterSize = 1;
                    }
                }
                else {
                    inCluster = false;
                    clusterSize = 0;
                }
            }
        }
        
        return -1;
    }

    private int findVWinCluster(int [][] board, int piece){

        int height = board.length;
        int width = board[0].length;

        for(int j = 0; j < width; j++){

            boolean inCluster = false;
            int clusterSize = 0;

            for(int i = 0; i < height; i++){
                if (board[i][j] == piece){
                    if (inCluster){
                        clusterSize++;
                        if (clusterSize == WINLINELENGTH) return 100 + i*10 + j;
                    }
                    else {
                        inCluster = true;
                        clusterSize = 1;
                    }
                }
                else {
                    inCluster = false;
                    clusterSize = 0;
                }
            }
        }
        
        return -1;
    }

    private int findRDCluster(int [][] board, int piece){
        int height = board.length;
        int width = board[0].length;

        for(int k = 0; k < 6; k++){
            int i = k<2 ? 2-k : 0;
            int j = k<3 ? 0 : k-2;

            boolean inCluster = false;
            int clusterSize = 0;

            while(i < height && j < width){
                if (board[i][j] == piece){
                    if (inCluster){
                        clusterSize++;
                        if (clusterSize == WINLINELENGTH) return 200 + 10*i + j;
                    }
                    else {
                        inCluster = true;
                        clusterSize = 1;
                    }
                }
                else {
                    inCluster = false;
                    clusterSize = 0;
                }
                i++;
                j++;
            }
        }

        return -1;
    }

    protected int findRUWinCLuster(int[][] board, int piece) {
        int height = board.length;
        int width = board[0].length;

        for(int k = 0; k < 6; k++){
            int i = k<2 ? k+3 : 5;
            int j = k<3 ? 0 : k-2;

            boolean inCluster = false;
            int clusterSize = 0;

            while(i >= 0 && j < width){
                if (board[i][j] == piece){
                    if (inCluster){
                        clusterSize++;
                        if (clusterSize == WINLINELENGTH) return 300 + 10*i + j;
                    }
                    else {
                        inCluster = true;
                        clusterSize = 1;
                    }
                }
                else {
                    inCluster = false;
                    clusterSize = 0;
                }
                i--;
                j++;
            }
        }

        return -1;
    }

    private int[] iIndexes(int i, int j, int dir) {

        int [] ind = new int[4];

        if (dir == 0) {
            ind[0] = ind[1] = ind[2] = ind[3] = i;
        }
        else if (dir == 1) {
            ind[0] = i;
            ind[1] = i-1;
            ind[2] = i-2;
            ind[3] = i-3;
        }
        else if (dir == 2) {
            ind[0] = i;
            ind[1] = i-1;
            ind[2] = i-2;
            ind[3] = i-3;
        }
        else if (dir == 3) {
            ind[0] = i;
            ind[1] = i+1;
            ind[2] = i+2;
            ind[3] = i+3;
        }

        return ind;
    }

    private int[] jIndexes(int i, int j, int dir) {

        int [] ind = new int[4];

        if (dir == 0) {
            ind[0] = j;
            ind[1] = j-1;
            ind[2] = j-2;
            ind[3] = j-3;
        }
        else if (dir == 1) {
            ind[0] = ind[1] = ind[2] = ind[3] = j;
        }
        else if (dir == 2) {
            ind[0] = j;
            ind[1] = j-1;
            ind[2] = j-2;
            ind[3] = j-3;
        }
        else if (dir == 3) {
            ind[0] = j;
            ind[1] = j-1;
            ind[2] = j-2;
            ind[3] = j-3;
        }

        return ind;
    }

}
