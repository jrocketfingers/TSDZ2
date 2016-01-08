/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import java.util.ArrayList;
import Minimax.AIThread;
import Minimax.Move;
import Minimax.Player;

public class C4Player extends Player {

    //1 for white, 2 for black
    protected int piece;
    //protected boolean myTurn;
    protected boolean iWin;
    protected final int WINVALUE = 100000;

    protected final int XL = 1000;
    protected final int L = 100;
    protected final int M = 10;
    protected final int S = 1;

    protected final int WINLINELENGTH = 4;

    protected int width;
    protected int height;
    protected int [][] board;
    protected int [] freePos;

    protected int [] movesMade;
    protected int [] searchDepth;

    protected int depth = 9;

    protected boolean fixedDepth = true;

    protected AIThread aiThread;

    public C4Player(int type){
        this.piece = type;
        initialize();
    }

    @Override
    public boolean didIWin() {
//        int [][] board = ((C4Board)game.getBoard()).getBoard();
//
//        return findHWinCLuster(board) ||
//               findVWinCLuster(board) ||
//               findRUWinCLuster(board) ||
//               findRDWinCLuster(board);
        ArrayList<Move> moveList = game.getBoard().getMoveList();
        if (moveList.size()==0) return false;
        C4Move lastMove = (C4Move)moveList.get(moveList.size()-1);
        if (lastMove.getPiece()!=piece) return false;
        int [][] board = ((C4Board)game.getBoard()).getBoard();
        return iWin(lastMove.getX(), board);
    }

    @Override
    public ArrayList<Move> generateMoves() {

        ArrayList<Move> moveList = new ArrayList<Move>();

        if (game.getBoard().getMoveCount()==0){
            moveList.add(new C4Move(3, 0, piece));
            return moveList;
        }

        //ako imam igrivu pretnju - samo taj potez - to je pobeda!
        for(int i = 0; i < width; i++)
            if (freePos[i] < height && isRealThreat(freePos[i], i)){
                moveList.add(new C4Move(i, freePos[i], piece));
                return moveList;
            }

        //ako protivnik ima igrivu pretnju - samo taj potez - to sprecava poraz!
        for(int i = 0; i < width; i++)
            if (freePos[i] < height && ((C4Player)getOponent()).isRealThreat(freePos[i], i)){
                moveList.add(new C4Move(i, freePos[i], piece));
                return moveList;
            }

        //genersii sve moguce poteze
        C4Board board = (C4Board)game.getBoard();
        for(int i = 0; i < board.WIDTH; i++)
            if (freePos[i] < board.HEIGHT){
                moveList.add(new C4Move(i, freePos[i], piece));
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
        int [][] board = ((C4Board)game.getBoard()).getBoard();
        C4Move m = (C4Move)move;
        int i = ((C4Board)game.getBoard()).getFreePos()[m.getX()];
        int j = m.getX();
        board[i][j] = piece;
        ((C4Board)game.getBoard()).getFreePos()[m.getX()]++;
//        myTurn = !myTurn;
//        ((C4Player)getOponent()).myTurn = !myTurn;
    }

    @Override
    public boolean canMakeMove(Move move) {
        C4Move m = (C4Move)move;
        return ((C4Board)game.getBoard()).getFreePos()[m.getX()] < height;
    }

    @Override
    public int positionValue() {
        return myPositionValue();
    }

    @Override
    public void undoMove(Move move) {
        game.getBoard().removeMove(move);
        C4Move m = (C4Move)move;
        ((C4Board)game.getBoard()).getFreePos()[m.getX()]--;
        int [][] board = ((C4Board)game.getBoard()).getBoard();
        int i = ((C4Board)game.getBoard()).getFreePos()[m.getX()];
        int j = m.getX();
        board[i][j] = 0;
//        myTurn = !myTurn;
//        ((C4Player)getOponent()).myTurn = !myTurn;
    }

    @Override
    public void initialize() {
//        myTurn = piece == 1 ? true : false;
        iWin = false;
        if (game!=null && game.getBoard()!=null){
            width = ((C4Board)game.getBoard()).WIDTH;
            height = ((C4Board)game.getBoard()).HEIGHT;
            board = ((C4Board)game.getBoard()).getBoard();
            freePos = ((C4Board)game.getBoard()).getFreePos();
        }
    }

    protected boolean findHWinCLuster(int[][] board) {

        for(int i = 0; i < board.length; i++){

            boolean inCluster = false;
            int clusterSize = 0;

            for(int j = 0; j < board[i].length; j++){
                if (board[i][j] == piece){
                    if (inCluster){
                        clusterSize++;
                        if (clusterSize == WINLINELENGTH) return true;
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

        return false;
    }

    protected boolean findVWinCLuster(int[][] board) {

        int height = board.length;
        int width = board[0].length;


        for(int j = 0; j < width; j++){

            boolean inCluster = false;
            int clusterSize = 0;

            for(int i = 0; i < height; i++){
                if (board[i][j] == piece){
                    if (inCluster){
                        clusterSize++;
                        if (clusterSize == WINLINELENGTH) return true;
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

        return false;
    }

    protected boolean findRDWinCLuster(int[][] board) {

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
                        if (clusterSize == WINLINELENGTH) return true;
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

        return false;
    }

    protected boolean findRUWinCLuster(int[][] board) {
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
                        if (clusterSize == WINLINELENGTH) return true;
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

        return false;
    }

    protected int[] getRealThreats() {
        ArrayList<Integer> threatList = new ArrayList<Integer>();
        int [][] board = ((C4Board)game.getBoard()).getBoard();
        int [] freePos = ((C4Board)game.getBoard()).getFreePos();
        int height = board.length;
        int width = board[0].length;

        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                if (isRealThreat(i, j)){
                    if (freePos[j] == i) threatList.add(new Integer(100 + 10*i + j));
                    else threatList.add(new Integer(10*i + j));
                }

        int [] result = new int[threatList.size()];
        for(int i = 0; i < result.length; i++)
            result[i] = threatList.get(i);
        
        return result;
    }

    protected boolean isPotentialThreat(int x, int y, int [][] board){

        if (x<0 || x>=height || y<0 || y>=width) return false;

//        int [][] board = ((C4Board)game.getBoard()).getBoard();
//        int height = board.length;
//        int width = board[0].length;

        //check vertical
        int clusterSize = 0;
        int i = x;
        int j = y;
        while(i < height && (board[i][j]==0 || board[i][j]==piece)){
            clusterSize++;
            i++;
        }
        i = x-1;
        j = y;
        while(i >= 0 && (board[i][j]==0 || board[i][j]==piece)){
            clusterSize++;
            i--;
        }
        if (clusterSize >= 4) return true;

        //check horizontal
        clusterSize = 0;
        i = x;
        j = y;
        while(j < width && (board[i][j]==0 || board[i][j]==piece)){
            clusterSize++;
            j++;
        }
        i = x;
        j = y-1;
        while(j >= 0 && (board[i][j]==0 || board[i][j]==piece)){
            clusterSize++;
            j--;
        }
        if (clusterSize >= 4) return true;

        //check RU
        clusterSize = 0;
        i = x;
        j = y;
        while(j < width && i < height && (board[i][j]==0 || board[i][j]==piece)){
            clusterSize++;
            j++;
            i++;
        }
        i = x-1;
        j = y-1;
        while(j >= 0 && i>=0 && (board[i][j]==0 || board[i][j]==piece)){
            clusterSize++;
            j--;
            i--;
        }
        if (clusterSize >= 4) return true;
        
        //check RD
        clusterSize = 0;
        i = x;
        j = y;
        while(j < width && i >= 0 && (board[i][j]==0 || board[i][j]==piece)){
            clusterSize++;
            j++;
            i--;
        }
        i = x+1;
        j = y-1;
        while(j>=0 && i<height && (board[i][j]==0 || board[i][j]==piece)){
            clusterSize++;
            j--;
            i++;
        }
        if (clusterSize >= 4) return true;

        return false;
    }

    protected boolean isRealThreat(final int x, final int y) {
        int [][] board = ((C4Board)game.getBoard()).getBoard();
        int height = board.length;
        int width = board[0].length;
        if (board[x][y] != 0) return false;

        //check vertical
        int clusterSize = 1;
        int i = x+1;
        int j = y;
        while(i < height && board[i][j]==piece){
            clusterSize++;
            i++;
        }
        i = x-1;
        j = y;
        while(i >= 0 && board[i][j]==piece) {
            clusterSize++;
            i--;
        }
        if (clusterSize >= 4) return true;

        //check horizontal
        clusterSize = 1;
        i = x;
        j = y+1;
        while(j < width && board[i][j]==piece){
            clusterSize++;
            j++;
        }
        i = x;
        j = y-1;
        while(j >= 0 && board[i][j]==piece){
            clusterSize++;
            j--;
        }
        if (clusterSize >= 4) return true;

        //check RU
        clusterSize = 1;
        i = x+1;
        j = y+1;
        while(j < width && i < height && board[i][j]==piece){
            clusterSize++;
            j++;
            i++;
        }
        i = x-1;
        j = y-1;
        while(j >= 0 && i>=0 && board[i][j]==piece){
            clusterSize++;
            j--;
            i--;
        }
        if (clusterSize >= 4) return true;

        //check RD
        clusterSize = 1;
        i = x-1;
        j = y+1;
        while(j < width && i >= 0 && board[i][j]==piece){
            clusterSize++;
            j++;
            i--;
        }
        i = x+1;
        j = y-1;
        while(j>=0 && i<height && board[i][j]==piece){
            clusterSize++;
            j--;
            i++;
        }
        if (clusterSize >= 4) return true;

        return false;
    }

    //ovde mi je jako bitno da znam da li sam upravo ja odigrao ili protivnik!
    protected int myPositionValue(){
        int posVal = 0;

        int [] threatList = getRealThreats();
        int [] oponentThreatList = ((C4Player)getOponent()).getRealThreats();
        board = ((C4Board)game.getBoard()).getBoard();
        freePos = ((C4Board)game.getBoard()).getFreePos();
        boolean myTurn = myTurn();
        

        //ako imam 4 u nizu POBEDIO SAM
        if (!myTurn){
            //mogu imati 4 u nizu samo ako sam ja odigrao prosli potez
            ArrayList<Move> moveList = game.getBoard().getMoveList();
            if (moveList.size() > 0){
                C4Move lastMove = (C4Move)moveList.get(moveList.size()-1);

                if (iWin(lastMove.getX(), board))
                    return WINVALUE;
            }

        }

        //ako protivnik ima 4 u nizu IZGUBIO SAM
        if (myTurn){
            //protivnik moze imati 4 u nizu samo ako je on odigaro prosli potez
            ArrayList<Move> moveList = game.getBoard().getMoveList();
            if (moveList.size() > 0){
                C4Move lastMove = (C4Move)moveList.get(moveList.size()-1);

                if (((C4Player)getOponent()).iWin(lastMove.getX(), board))
                    return -WINVALUE;
            }
        }

        //ako je protivnik na potezu i ima direktno igrivu pretnju
        //IZGUBIO SAM
        if (!myTurn){
            if (((C4Player)getOponent()).playableThreatExists(board, freePos))
                return -WINVALUE;
        }

        //ako sam ja na potezu i imam direktno igrivu pretnju
        //POBEDIO SAM
        if (myTurn){
            if (playableThreatExists(board, freePos))
                return WINVALUE;
        }

        //ako je protivnik na potezu a ja imam dve direktno igrive pretnje
        //POBEDIO SAM
        if (!myTurn && threatList.length > 1){
            int count = 0;
            for(int i = 0; i < threatList.length; i++){
                if (threatList[i]/100==1)count++;
            }
            if (count >= 2)
                return WINVALUE;
        }

        //ako sam ja na potezu a protivnik ima dve direktno igrive pretnje
        //IZGUBIO SAM
        if (myTurn){
            if (((C4Player)getOponent()).haveTwoDirectRealThreats(oponentThreatList, freePos))
                return -WINVALUE;
        }

        //ako imam dve pretnje tacno jednu iznad druge
        //to je pobeda samo ako u istoj situaciji nije i protivnik
        //a da su pritom njegove pretnje na relativno nizim mestima!
        //ako je pak ta situacija - izgubio sam
        boolean haveTwoConnVerThreats = haveTwoConnVerThreats(threatList, oponentThreatList);
        boolean oponentHasTwoConnVerThreats = ((C4Player)getOponent()).haveTwoConnVerThreats(oponentThreatList, threatList);

        if (haveTwoConnVerThreats && !oponentHasTwoConnVerThreats){
            //System.out.println("\t\t!!!");
            haveTwoConnVerThreats(threatList, oponentThreatList);
            return WINVALUE-1;
        }
        if (!haveTwoConnVerThreats && oponentHasTwoConnVerThreats)
            return -WINVALUE+1;
        if (haveTwoConnVerThreats && oponentHasTwoConnVerThreats){
            //0System.out.println("ERROR!!! situation not yet implemented in C4Player.myPositionValue");
        }

        if (piece == 1){
        //ako sam beli
            //ako imam pretnje koje su otvorene na neparnim mestima to je
            //dosta poena, ako protivnik ispod te neparne nema parnu pretnju!
            //ako imam pretnje koje su otvorene na parnim mestima to je
            //nesto malo poena
            boolean haveOddThreats = false;
            for(int i = 0; i < threatList.length; i++){
                if ((threatList[i] / 10) % 2 == 0){
                    boolean OponentThreatBelow = false;
                    for(int j = 0; j < oponentThreatList.length; j++){
                        if ((oponentThreatList[j]%10 == threatList[i]%10) &&
                            ((threatList[i] / 10) % 2 + 1 == oponentThreatList[j] / 10 % 2)){
                                OponentThreatBelow = true;
                                break;
                            }
                    }
                    if (!OponentThreatBelow){
                        posVal += XL;
                        haveOddThreats = true;
                    }
                }
                else {
                    posVal += M;
                }
            }

            //ako nemam neparne pretnje a crni ima parne to je
            //dosta degativnih poena
            if (!haveOddThreats)
                for(int i = 0; i < oponentThreatList.length; i++){
                    if (oponentThreatList[i] / 10 % 2 == 1){
                        posVal -= XL;
                    }
                }

            //svako neparno mesto koje potencijalno moze da zatvori 4
            //je nesto malo poena
            for(int i = 0; i < ((C4Board)game.getBoard()).HEIGHT; i+=2)
                for(int j = 0; j < ((C4Board)game.getBoard()).WIDTH; j++)
                    if (isPotentialThreat(i, j, board))
                        posVal += S;

            //svako parno mesto koje protivnik potencijalno moze da zatvori 4
            //je nesto malo negativnih poena
            //ako uz to postoji i mesto iznad/ispod koje takodje moze da udje u
            //potencijalnu 4 za protivnika to je onda malo vise negativnih poena
            for(int i = 1; i < height; i+=2)
                for(int j = 0; j < width; j++)
                    if (((C4Player)getOponent()).isPotentialThreat(i, j, board))
                        if (((C4Player)getOponent()).isPotentialThreat(i-1, j, board)){
                            posVal -= 2*M;
                        }
                        else if (((C4Player)getOponent()).isPotentialThreat(i+1, j, board)){
                            posVal -= 2*M;
                        }
                        else {
                            posVal -= 2*S;
                        }

        }
        else{
        //ako sam crni

            //ako beli ima pretnje na neparnim mestima to je
            //dosta negativnih poena
            boolean whiteHasOddThreats = false;
            for(int i = 0; i < oponentThreatList.length; i++){
                if (oponentThreatList[i]/10 % 2 == 0){
                    whiteHasOddThreats = true;
                    posVal -= 10*XL;
                }
            }

            //ako protivnik nema neparne a ja imam parne pretnje
            //to je dosta poena
            //ako samo ja imam pretnje i to neparne
            //to je nesto malo poena
            
            for(int i = 0; i < threatList.length; i++){
                if (threatList[i]/10 % 2 == 1){
                    posVal += XL;
                }
                else {
                    posVal += M;
                }
            }

            
            //svako parno mesto koje potencijalno moze da zatvori 4
            //je nesto malo poena
            for(int i = 1; i < ((C4Board)game.getBoard()).HEIGHT; i+=2)
                for(int j = 0; j < ((C4Board)game.getBoard()).WIDTH; j++)
                    if (isPotentialThreat(i, j, board))
                        posVal += S;

            //svako neparno mesto koje protivnik potencijalno moze da zatvori 4
            //je nesto malo negativnih poena
            //ako uz to postoji i mesto iznad/ispod koje takodje moze da udje u
            //potencijalnu 4 za protivnika to je onda malo vise negativnih poena
            for(int i = 0; i < height; i+=2)
                for(int j = 0; j < width; j++)
                    if (((C4Player)getOponent()).isPotentialThreat(i, j, board))
                        if (((C4Player)getOponent()).isPotentialThreat(i-1, j, board)){
                            posVal -= 2*M;
                        }
                        else if (((C4Player)getOponent()).isPotentialThreat(i+1, j, board)){
                            posVal -= 2*M;
                        }
                        else {
                            posVal -= 2*S;
                        }
        }

        //ako protivnik ima nesto poput ovoga dole to treba da se zatvori ako moze!
        //
        //                                 .xx.   ..xx  xx..
        //                                 .xx.   ..xx  xx..

        boolean found2x2 = false;
        for(int i = 0; i < ((C4Board)game.getBoard()).HEIGHT-1 && !found2x2; i++)
            for(int j = 1; j < ((C4Board)game.getBoard()).WIDTH-2 && !found2x2; j++){
                if (oponentHas2x2cluster(i, j, board)){
                    posVal -= L;
                    found2x2 = true;
                }
            }
       
        return posVal;
    }

    protected boolean iWin(final int x, int [][] board) {
        //int [][] board = ((C4Board)game.getBoard()).getBoard();
        //final int height = board.length;
        //final int width = board[0].length;
        final int y = ((C4Board)game.getBoard()).getFreePos()[x]-1;//###dodao -1

        //check vertical
        int i = y - 1;
        int j = x;
        int clusterSize = 1;
        while(i >= 0 && board[i][j]==piece){
            clusterSize++;
            i--;
        }
        if (clusterSize >= 4) return true;

        //check horizontal
        clusterSize = 1;
        i = y;
        j = x-1;
        while(j>=0 && board[i][j]==piece){
            clusterSize++;
            j--;
        }
        j = x+1;
        while(j<width && board[i][j]==piece){
            clusterSize++;
            j++;
        }
        if (clusterSize >= 4) return true;

        //check RU
        clusterSize = 1;
        i = y+1;
        j = x+1;
        while(i<height && j<width && board[i][j]==piece){
            clusterSize++;
            i++;
            j++;
        }
        i = y-1;
        j = x-1;
        while(i>=0 && j>=0 && board[i][j]==piece){
            clusterSize++;
            i--;
            j--;
        }
        if (clusterSize >= 4) return true;

        //check RD
        clusterSize = 1;
        i = y+1;
        j = x-1;
        while(i<height && j>=0 && board[i][j]==piece){
            clusterSize++;
            i++;
            j--;
        }
        i = y-1;
        j = x+1;
        while(i>=0 && j<width && board[i][j]==piece){
            clusterSize++;
            i--;
            j++;
        }
        if (clusterSize >= 4) return true;
        

        return false;
    }

    protected boolean playableThreatExists(int [][] board, int [] freePos){
//        int [][] board = ((C4Board)game.getBoard()).getBoard();
//        int [] freePos = ((C4Board)game.getBoard()).getFreePos();
//        int height = board.length;
//        int width = board[0].length;

        for(int i = 0; i < width; i++){
            if (freePos[i] < height && freePos[i] < height &&
                isRealThreat(freePos[i], i))
                
                return true;
        }

        return false;
        
    }

    protected boolean haveTwoDirectRealThreats(int [] threatList, int [] freePos){
        int count = 0;

        for(int k = 0; k < threatList.length; k++){
//            int i = threatList.get(k) / 10 % 10;
//            int j = threatList.get(k) % 10;
//            if (freePos[j] == i) count++;
            if (threatList[k]/100 == 1) count++;
        }

        return count >= 2;
    }

    protected boolean haveTwoConnVerThreats(ArrayList<Integer> threatList, ArrayList<Integer> oponentThreatList){
        for(int i = 0; i < threatList.size(); i++)
            for(int j = 0; j < threatList.size(); j++)
                if (Math.abs(threatList.get(i)%100 - threatList.get(j)%100) == 10){
                    //ako imam dva napada jedan za drugim
                    //a protivnik ima bar jedan ispod
                    //beli na neparnom a crni na parnom mestu
                    //onda ovaj napad ipak ne vredi
                    int h = Math.min(threatList.get(i)/10%10, threatList.get(j)/10%10);
                    if (piece == 1){
                        for(int k = 0; k < oponentThreatList.size(); k++){
                            if (oponentThreatList.get(k)/10%2==1 &&
                                oponentThreatList.get(k)/10%10 <= h)
                                return false;
                        }
                    }
                    else {
                        for(int k = 0; k < oponentThreatList.size(); k++){
                            if (oponentThreatList.get(k)/10%2==0 &&
                                oponentThreatList.get(k)/10%10 <= h)
                                return false;
                        }
                    }
                    
                    return true;
                }

        return false;
    }

    protected boolean haveTwoConnVerThreats(int [] threatList, int [] oponentThreatList){

        for(int i = 0; i < threatList.length; i++)
            for(int j = 0; j < threatList.length; j++)
                if (Math.abs(threatList[i]%100 - threatList[j]%100) == 10){
                    //ako imam dva napada jedan za drugim
                    //a protivnik ima bar jedan ispod
                    //beli na neparnom a crni na parnom mestu
                    //onda ovaj napad ipak ne vredi
                    int h = Math.min(threatList[i]/10%10, threatList[j]/10%10);
                    if (piece == 1){
                        for(int k = 0; k < oponentThreatList.length; k++){
                            if (oponentThreatList[k]/10%2==1 &&
                                oponentThreatList[k]/10%10 <= h)
                                return false;
                        }
                    }
                    else {
                        for(int k = 0; k < oponentThreatList.length; k++){
                            if (oponentThreatList[k]/10%2==0 &&
                                oponentThreatList[k]/10%10 <= h)
                                return false;
                        }
                    }

                    return true;
                }
        
        return false;
    }

    @Override
    public void yourTurn() {

        if (ai!=null){
            if (game.getGameMonitor() != null)
                game.getGameMonitor().setWhosOnMove("na potezu: " + (piece==1 ? "zuti" : "plavi") + " (razmislja...)");
            if (fixedDepth){
                aiThread = new AIThread(ai, game, depth, this);
                aiThread.start();
            }
            else {
                int i = 0;
                while(i < movesMade.length && game.getMoveCount() > movesMade[i]){
                    i++;
                }
                if (i == movesMade.length){
                    aiThread = new AIThread(ai, game, searchDepth[i-1], this);
                    aiThread.start();
                }
                else {
                    aiThread = new AIThread(ai, game, searchDepth[i], this);
                    aiThread.start();
                }
            }
        }
        else {
            if (game.getGameMonitor() != null)
                game.getGameMonitor().setWhosOnMove(" - na potezu: " + (piece==1 ? "zuti" : "plavi"));
        }
    }

    protected boolean oponentHas2x2cluster(int i, int j, int [][] board) {
//        int [][] board = ((C4Board)game.getBoard()).getBoard();
        int oponentPiece = piece == 1 ? 2 : 1;
//        int width = ((C4Board)game.getBoard()).WIDTH;

        if (board[i][j] == oponentPiece &&
            board[i+1][j] == oponentPiece &&
            board[i][j+1] == oponentPiece &&
            board[i+1][j+1] == oponentPiece){

            if ((board[i][j-1] == 0 || board[i][j-1] == oponentPiece) &&
                (board[i+1][j-1] == 0 || board[i+1][j-1] == oponentPiece) &&
                (board[i][j+2] == 0 || board[i][j+2] == oponentPiece) &&
                (board[i+1][j+2] == 0 || board[i+1][j+2] == oponentPiece))
                return true;

            if (j>=2)
                if ((board[i][j-1] == 0 || board[i][j-1] == oponentPiece) &&
                    (board[i+1][j-1] == 0 || board[i+1][j-1] == oponentPiece) &&
                    (board[i][j-2] == 0 || board[i][j-2] == oponentPiece) &&
                    (board[i+1][j-2] == 0 || board[i+1][j-2] == oponentPiece))
                    return true;

            if (j<width-3)
                if ((board[i][j+2] == 0 || board[i][j+2] == oponentPiece) &&
                    (board[i+1][j+2] == 0 || board[i+1][j+2] == oponentPiece) &&
                    (board[i][j+3] == 0 || board[i][j+3] == oponentPiece) &&
                    (board[i+1][j+3] == 0 || board[i+1][j+3] == oponentPiece))
                    return true;
        }

        return false;
    }

    @Override
    public void commitMove(){
//        myTurn = false;
        ((C4Board)game.getBoard()).copyBoard();
        game.moveCommit();
    }

    @Override
    public void commitUndo() {
        ((C4Board)game.getBoard()).copyBoard();
    }

    protected boolean myTurn(){
        return (game.isWhitesTurn() && piece==1) || (!game.isWhitesTurn() && piece==2);
    }

    public void setVariableDepthSearch(int [] movesMade, int [] searchDepth){
        if(movesMade.length != searchDepth.length || movesMade.length<=0) return;
        int n = movesMade.length;
        this.movesMade = new int[n];
        this.searchDepth = new int[n];

        for(int i = 0; i < movesMade.length; i++){
            this.movesMade[i] = movesMade[i];
            this.searchDepth[i] = searchDepth[i];
        }

        fixedDepth = false;
    }

    public void setSearchDepth(int searchDepth){
        fixedDepth = true;
        this.depth = searchDepth;
    }

    @Override
    public void gameStopped() {
        if (aiThread!=null) {
            aiThread.stop();
        }
    }


    

}
