/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Administrator
 */
public class AlphaBetaVDHTT extends AMinimax{

    private Map<Integer, TTEntry> transpositionTable = new TreeMap<Integer, TTEntry>();

    @Override
    public Move findBestMove(Game game, int depth) {

        if (timeLimit > 0) {
            timer = new Timer(timeLimit, this);
            timer.start();
            timeIsUp = false;
            return findBestMoveID(game, depth);
        }

        System.out.println("AlphaBetaVDHTT");

        transpositionTable.clear();

        ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
        Move bestMove = null;

        int alpha = -INF;
        int beta = INF;

        for(int i = 0; i < moveList.size(); i++) {
            game.getPlayerOnTurn().makeMove(moveList.get(i));
            game.moveMade();

            int value = min(game, depth-1, alpha, beta, true);
            if (value > alpha){
                alpha = value;
                bestMove = moveList.get(i);
            }
            
            game.getPlayerOnTurn().undoMove(moveList.get(i));
            game.moveUndo();

            if (alpha >= beta) {
                break;
            }
        }
        
        System.out.println("max val == " + alpha);
        return bestMove;
    }

    private int max(Game game, int depth, int alpha, int beta, boolean nestingAllowed){

        if (timeIsUp) return -INF;

        TTEntry entry;
        PositionHash hash = game.getBoard().getPositionHash();
        Integer iHash = hash.getIHash();
        if ((entry = transpositionTable.get(iHash)) != null &&
             entry.getDepth() <= depth &&
             entry.getPositionHash().compareTo(hash) == 0)
             {
            if (entry.getType() == 0) {//exact value
                int posValue = entry.getValue();
                if (depth == 0 && winValue!=-1 && (posValue >= winValue || posValue <= -winValue))
                    posValue*=depth+1;
                return posValue;
            }
            else if (entry.getType() == -1 && entry.getValue() > alpha) {//lower bound
                alpha = entry.getValue();
            }
            else if (entry.getType() == 1 && entry.getValue() < beta) {//upper bound
                beta = entry.getValue();
            }
            if (alpha >= beta){
                int posValue = entry.getValue();
                if (depth == 0 && winValue!=-1 && (posValue >= winValue || posValue <= -winValue))
                    posValue*=depth+1;
                return posValue;
            }
        }

        if (depth == 0 || game.isGameOver()) {
            leavesVisitedCnt++;
            leavesEvaluatedCnt++;
            int posValue = game.getPlayerOnPTurn().positionValue();

            if (nestingAllowed && winValue!=0 && 
                    ((posValue>= lowBound && posValue <= highBound) ||
                    (posValue>= -highBound && posValue <= -lowBound))){
                posValue = max(game, additionalDepth, alpha, beta, false);
            }

            if (posValue <= alpha){
                //store as lowbound
                transpositionTable.put(iHash, new TTEntry(hash, posValue, -1, depth));
            }
            else if (posValue >= beta){
                //store as upbound
                transpositionTable.put(iHash, new TTEntry(hash, posValue, 1, depth));
            }
            else {
                transpositionTable.put(iHash, new TTEntry(hash, posValue, 0, depth));
            }

            if (winValue!=-1 && (posValue >= winValue || posValue <= -winValue) && nestingAllowed)
                posValue*=depth+1;
            
            return posValue;
        }
        else {
            ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
            int best = -INF;
            for(int i = 0; i < moveList.size(); i++) {
                game.getPlayerOnTurn().makeMove(moveList.get(i));
                game.moveMade();

                int value = min(game, depth-1, alpha, beta, nestingAllowed);
                if (value > best){
                    best = value;
                }
                if (best > alpha) {
                    alpha = best;
                }

                game.getPlayerOnTurn().undoMove(moveList.get(i));
                game.moveUndo();

                if (best >= beta) {
                    break;
                }
            }

            if (best >= beta) 
                transpositionTable.put(iHash, new TTEntry(hash, best, -1, depth));
            else {
                transpositionTable.put(iHash, new TTEntry(hash, best, 1, depth));
            }

            return best;
        }
    }

    private int min(Game game, int depth, int alpha, int beta, boolean nestingAllowed){

        if (timeIsUp) return INF;

        TTEntry entry;
        PositionHash hash = game.getBoard().getPositionHash();
        Integer iHash = hash.getIHash();
        
        if ((entry = transpositionTable.get(iHash)) != null &&
             entry.getDepth() <= depth &&
             entry.getPositionHash().compareTo(hash) == 0
             ){
            if (entry.getType() == 0) {//exact value
                int posValue = entry.getValue();
                if (depth == 0 && winValue!=-1 && (posValue >= winValue || posValue <= -winValue))
                    posValue*=depth+1;
                return posValue;
            }
            else if (entry.getType() == -1 && entry.getValue() > alpha) {//lower bound
                alpha = entry.getValue();
            }
            else if (entry.getType() == 1 && entry.getValue() < beta) {//upper bound
                beta = entry.getValue();
            }
            if (alpha >= beta){
                int posValue = entry.getValue();
                if (depth == 0 && winValue!=-1 && (posValue >= winValue || posValue <= -winValue))
                    posValue*=depth+1;
                return posValue;
            }
        }
        
        if (depth == 0 || game.isGameOver()) {
            leavesVisitedCnt++;
            leavesEvaluatedCnt++;
            int posValue = game.getPlayerOnPTurn().positionValue();

            if (nestingAllowed && winValue!=0 &&
                    ((posValue>= lowBound && posValue <= highBound) ||
                    (posValue>= -highBound && posValue <= -lowBound))){
                posValue = min(game, additionalDepth, alpha, beta, false);
            }

            if (posValue <= alpha){
                //store as lowbound
                transpositionTable.put(iHash, new TTEntry(hash, posValue, -1, depth));
            }
            else if (posValue >= beta){
                //store as upbound
                transpositionTable.put(iHash, new TTEntry(hash, posValue, 1, depth));
            }
            else {
                transpositionTable.put(iHash, new TTEntry(hash, posValue, 0, depth));
            }

            if (winValue!=-1 && (posValue >= winValue || posValue <= -winValue) && nestingAllowed)
                posValue*=depth+1;
            
            return posValue;
        }
        else {
            ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
            int best = +INF;
            for(int i = 0; i < moveList.size(); i++) {
                game.getPlayerOnTurn().makeMove(moveList.get(i));
                game.moveMade();

                int value = max(game, depth-1, alpha, beta, nestingAllowed);
                if (value < best) {
                    best = value;
                }
                if (best < beta) {
                    beta = value;
                }

                game.getPlayerOnTurn().undoMove(moveList.get(i));
                game.moveUndo();
                
                if (alpha >= beta) {
                    break;
                }
            }

            if (best <= alpha)
                transpositionTable.put(iHash, new TTEntry(hash, best, 1, depth));
            else {
                transpositionTable.put(iHash, new TTEntry(hash, best, -1, depth));
            }

            return best;
        }
    }

    private Move findBestMoveID(Game game, int depth) {
        
        System.out.println("AlphaBetaVDHTT (iterative deepening)");
        transpositionTable.clear();

        ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
        ArrayList<Integer> valueList = new ArrayList<Integer>();
        valueList.ensureCapacity(moveList.size());
        for(int i = 0; i < moveList.size(); i++)
            valueList.add(-INF);
        Move currentBestMove = null;
        Move bestMove = null;
        int bestValue = 0;
        int depthSearched = 0;

        int alpha = -INF;
        int beta = INF;

        for(int d = 1; d <= depth; d++){

            alpha = -INF;
            beta = INF;
            transpositionTable.clear();

            for(int i = 0; i < moveList.size(); i++) {
                game.getPlayerOnTurn().makeMove(moveList.get(i));
                game.moveMade();

                int value = min(game, d-1, alpha, beta, true);
                valueList.set(i, value);
                
                if (value > alpha){
                    alpha = value;
                    currentBestMove = moveList.get(i);
                }

                game.getPlayerOnTurn().undoMove(moveList.get(i));
                game.moveUndo();

                if (alpha >= beta) {
                    break;
                }
            }

            if (!timeIsUp) {
                bestMove = currentBestMove;
                depthSearched = d;
                bestValue = alpha;
            }
            else {
                break;
            }

            boolean swapped;
            do{
                swapped = false;
                for(int i = 0; i < moveList.size()-1; i++)
                    if (valueList.get(i) < valueList.get(i+1)){
                        int tmpInt = valueList.get(i);
                        Move tmpMove = moveList.get(i);
                        valueList.set(i, valueList.get(i+1));
                        moveList.set(i, moveList.get(i+1));
                        valueList.set(i+1, tmpInt);
                        moveList.set(i+1, tmpMove);
                        swapped = true;
                    }
            }while (swapped);
        }

        System.out.println("depth searched: " + depthSearched);
        System.out.println("max val == " + bestValue);
        return bestMove;
    }
}
