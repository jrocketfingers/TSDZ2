/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class AlphaBetaTT extends AMinimax {

    private Map<PositionCode, TTEntry> transpositionTable = new TreeMap<PositionCode, TTEntry>();

    @Override
    public Move findBestMove(Game game, int depth) {

        if (timeLimit > 0) {
            timer = new Timer(timeLimit, this);
            timer.start();
            timeIsUp = false;
            return findBestMoveID(game, depth);
        }

        System.out.println("AlphaBetaTT");
        transpositionTable.clear();

        ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
        Move bestMove = null;

        int alpha = -INF;
        int beta = INF;

        for(int i = 0; i < moveList.size(); i++) {
            game.getPlayerOnTurn().makeMove(moveList.get(i));
            game.moveMade();

            int value = min(game, depth-1, alpha, beta);
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

    private int max(Game game, int depth, int alpha, int beta){

        if (timeIsUp) return -INF;

        TTEntry entry;
        PositionCode code = game.getBoard().getPositionCode();
        if ((entry = transpositionTable.get(code)) != null && entry.getDepth() <= depth){
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
            
            
            if (posValue <= alpha){
                transpositionTable.put(code, new TTEntry(game.getBoard().getPositionHash(), posValue, -1, depth));
            }
            else if (posValue >= beta){
                transpositionTable.put(code, new TTEntry(game.getBoard().getPositionHash(), posValue, 1, depth));
            }
            else {
                transpositionTable.put(code, new TTEntry(game.getBoard().getPositionHash(), posValue, 0, depth));
            }

            if (winValue!=-1 && (posValue >= winValue || posValue <= -winValue))
                posValue*=depth+1;

            return posValue;
        }
        else {
            ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
            int best = -INF;
            for(int i = 0; i < moveList.size(); i++) {
                game.getPlayerOnTurn().makeMove(moveList.get(i));
                game.moveMade();

                int value = min(game, depth-1, alpha, beta);
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
                transpositionTable.put(code, new TTEntry(game.getBoard().getPositionHash(), best, -1, depth));
            else {
                transpositionTable.put(code, new TTEntry(game.getBoard().getPositionHash(), best, 1, depth));
            }

            return best;
        }
    }

    private int min(Game game, int depth, int alpha, int beta){

        if (timeIsUp) return INF;

        TTEntry entry;
        PositionCode code = game.getBoard().getPositionCode();
        if ((entry = transpositionTable.get(code)) != null && entry.getDepth() <= depth){
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

            if (posValue <= alpha){
                //store as lowbound
                transpositionTable.put(code, new TTEntry(game.getBoard().getPositionHash(), posValue, -1, depth));
            }
            else if (posValue >= beta){
                //store as upbound
                transpositionTable.put(code, new TTEntry(game.getBoard().getPositionHash(), posValue, 1, depth));
            }
            else {
                transpositionTable.put(code, new TTEntry(game.getBoard().getPositionHash(), posValue, 0, depth));
            }

            if (winValue!=-1 && (posValue >= winValue || posValue <= -winValue))
                posValue*=depth+1;
            return posValue;
        }
        else {
            ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
            int best = +INF;
            for(int i = 0; i < moveList.size(); i++) {
                game.getPlayerOnTurn().makeMove(moveList.get(i));
                game.moveMade();

                int value = max(game, depth-1, alpha, beta);
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
                transpositionTable.put(code, new TTEntry(game.getBoard().getPositionHash(), best, 1, depth));
            else {
                transpositionTable.put(code, new TTEntry(game.getBoard().getPositionHash(), best, -1, depth));
            }

            return best;
        }
    }

    private Move findBestMoveID(Game game, int depth) {
        System.out.println("AlphaBetaTT");
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

                int value = min(game, d-1, alpha, beta);
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
