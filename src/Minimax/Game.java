/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Minimax;

import Connect4.IGameMonitor;
import java.util.ArrayList;

/**
 *
 * @author Drazen Draskovic
 */
public abstract class Game {

    protected Player white = null;
    protected Player black = null;
    protected Board board = null;
    protected boolean whitesTurn = true;
    protected boolean whitesPTurn = true;
    protected int moveCount = 0;
    private int undoCount;
    private boolean whitesUndo;

    private IGameMonitor gameMonitor;

    private ArrayList<String> log = new ArrayList<String>();

    public Game(Board board, Player white, Player black) {
        this.board = board;
        this.white = white;
        this.black = black;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getWhite() {
        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public void startGame() {
        stopGame();
        moveCount = 0;
        whitesTurn = true;
        whitesPTurn = true;
        undoCount = 0;
        log.clear();
        board.initialize();
        white.initialize();
        black.initialize();
        getPlayerOnPTurn().yourTurn();
    }

    public Player getPlayerOnTurn() {
        if (whitesTurn) {
            return white;
        }
        return black;
    }

    public Player getPlayerOnPTurn() {
        if (whitesPTurn) {
            return white;
        }
        return black;
    }

    public abstract boolean isGameOver();

    public void moveMade() {
        moveCount++;
        whitesTurn = !whitesTurn;
    }

    public void moveUndo(){
        moveCount--;
        whitesTurn = !whitesTurn;
    }

    public void askForUndo(Player player) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //mislim da ovo moze da se realizuje na ovom mestu u potpunosti!
        //ali neka za sad ostane prazno
    }

    public void moveCommit() {

        moveCount++;
        whitesTurn = !whitesTurn;
        whitesPTurn = !whitesPTurn;
        board.updateVisualBoard();
        String newLog = "";
        if (getBoard().getMoveList().size() > 0){
            newLog = "" + getBoard().getMoveList().get(getBoard().getMoveList().size()-1);
        }
        if (whitesTurn) newLog = "\t" + newLog + "\n";
        log.add(newLog);
        board.updateGameLog(log);

        while(undoCount > 0 && board.getMoveCount()>0){
            if (whitesPTurn){
                if (whitesUndo){
                    black.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                    black.commitUndo();
                    moveCount--;
                    whitesTurn = !whitesTurn;
                    whitesPTurn = !whitesPTurn;
                    log.remove(log.size()-1);
                }
                else {
                    black.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                    black.commitUndo();
                    undoCount--;
                    moveCount--;
                    whitesTurn = !whitesTurn;
                    whitesPTurn = !whitesPTurn;
                    log.remove(log.size()-1);
                }
            }
            else {
                if (whitesUndo){
                    white.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                    black.commitUndo();
                    undoCount--;
                    moveCount--;
                    whitesTurn = !whitesTurn;
                    whitesPTurn = !whitesPTurn;
                    log.remove(log.size()-1);
                }
                else {
                    white.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                    black.commitUndo();
                    moveCount--;
                    whitesTurn = !whitesTurn;
                    whitesPTurn = !whitesPTurn;
                    log.remove(log.size()-1);
                }
            }
        }

        board.updateVisualBoard();
        
        
        if (!isGameOver()){
            getPlayerOnPTurn().yourTurn();
        }
        else {
            board.signalGameOver();
            if (gameMonitor!=null){
                if (white.didIWin()) gameMonitor.setGameOver(" - Pobedio je zuti");
                else if (black.didIWin()) gameMonitor.setGameOver(" - Pobedio je plavi");
                else gameMonitor.setGameOver(" - nereseno");
            }
        }
    }

    public int getMoveCount(){
        return moveCount;
    }

    public boolean isWhitesTurn(){
        return whitesTurn;
    }

    public boolean isWhitesPTurn(){
        return whitesPTurn;
    }

    public void undoMove(int n, boolean whiteAsks){
        undoCount = n;
        whitesUndo = whiteAsks;

        if (isWhitesPTurn()){
            if (white.isOKToUndo()){
                while(undoCount > 0 && getMoveCount()>0){
                    if (whitesPTurn){
                        if (whitesUndo){
                            black.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                            black.commitUndo();
                            moveCount--;
                            whitesTurn = !whitesTurn;
                            whitesPTurn = !whitesPTurn;
                            log.remove(log.size()-1);
                        }
                        else {
                            black.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                            black.commitUndo();
                            undoCount--;
                            moveCount--;
                            whitesTurn = !whitesTurn;
                            whitesPTurn = !whitesPTurn;
                            log.remove(log.size()-1);
                        }
                    }
                    else {
                        if (whitesUndo){
                            white.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                            black.commitUndo();
                            undoCount--;
                            moveCount--;
                            whitesTurn = !whitesTurn;
                            whitesPTurn = !whitesPTurn;
                            log.remove(log.size()-1);
                        }
                        else {
                            white.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                            black.commitUndo();
                            moveCount--;
                            whitesTurn = !whitesTurn;
                            whitesPTurn = !whitesPTurn;
                            log.remove(log.size()-1);
                        }
                    }
                }
            }
        }
        else {
            if (black.isOKToUndo()){
                while(undoCount > 0 && getMoveCount()>0){
                    if (whitesPTurn){
                        if (whitesUndo){
                            black.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                            black.commitUndo();
                            moveCount--;
                            whitesTurn = !whitesTurn;
                            whitesPTurn = !whitesPTurn;
                        }
                        else {
                            black.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                            black.commitUndo();
                            undoCount--;
                            moveCount--;
                            whitesTurn = !whitesTurn;
                            whitesPTurn = !whitesPTurn;
                            log.remove(log.size()-1);
                        }
                    }
                    else {
                        if (whitesUndo){
                            white.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                            black.commitUndo();
                            undoCount--;
                            moveCount--;
                            whitesTurn = !whitesTurn;
                            whitesPTurn = !whitesPTurn;
                            log.remove(log.size()-1);
                        }
                        else {
                            white.undoMove(board.getMoveList().get(board.getMoveList().size()-1));
                            black.commitUndo();
                            moveCount--;
                            whitesTurn = !whitesTurn;
                            whitesPTurn = !whitesPTurn;
                            log.remove(log.size()-1);
                        }
                    }
                }
            }
        }

        if (undoCount == 0){
            board.updateVisualBoard();
            getPlayerOnPTurn().yourTurn();
            board.updateGameLog(log);
        }
    }

    public void undoCommit(){
    }

    public void updateGameInfo(){}

    public void stopGame(){
        white.gameStopped();
        black.gameStopped();
        if (gameMonitor!=null){
            gameMonitor.setGameOver("Igra je prekinuta");
        }
    }

    public IGameMonitor getGameMonitor() {
        return gameMonitor;
    }

    public void setGameMonitor(IGameMonitor gameMonitor) {
        this.gameMonitor = gameMonitor;
    }
    
}
