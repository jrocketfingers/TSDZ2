/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;

import java.util.ArrayList;

public class AlphaBeta extends AMinimax{

    @Override
    public Move findBestMove(Game game, int depth) {

        if (timeLimit > 0) {
            timer = new Timer(timeLimit, this);
            timer.start();
            timeIsUp = false;
            return findBestMoveID(game, depth);
        }

        System.out.println("AlphaBeta");

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

        if (depth == 0 || game.isGameOver()) {
            leavesVisitedCnt++;
            leavesEvaluatedCnt++;
            int posValue = game.getPlayerOnPTurn().positionValue();
            if (winValue!=-1 && (posValue >= winValue || posValue <= -winValue))
                posValue*=depth+1;
            return posValue;
        }
        else {
            ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
            for(int i = 0; i < moveList.size(); i++) {
                game.getPlayerOnTurn().makeMove(moveList.get(i));
                game.moveMade();

                int value = min(game, depth-1, alpha, beta);
                if (value > alpha) {
                    alpha = value;
                }

                game.getPlayerOnTurn().undoMove(moveList.get(i));
                game.moveUndo();

                if (alpha >= beta) {
                    return alpha;
                }
            }
            return alpha;
        }
    }

    private int min(Game game, int depth, int alpha, int beta){

        if (timeIsUp) return INF;

        if (depth == 0 || game.isGameOver()) {
            leavesVisitedCnt++;
            leavesEvaluatedCnt++;
            int posValue = game.getPlayerOnPTurn().positionValue();
            if (winValue!=-1 && (posValue >= winValue || posValue <= -winValue))
                posValue*=depth+1;
            return posValue;
        }
        else {
            ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();

            for(int i = 0; i < moveList.size(); i++) {
                game.getPlayerOnTurn().makeMove(moveList.get(i));
                game.moveMade();

                int value = max(game, depth-1, alpha, beta);
                if (value < beta) {
                    beta = value;
                }

                game.getPlayerOnTurn().undoMove(moveList.get(i));
                game.moveUndo();
                
                if (alpha >= beta) {
                    return beta;
                }
            }
            return beta;
        }
    }

    private Move findBestMoveID(Game game, int depth) {
        
        System.out.println("AlphaBeta (iterative depening)");

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
            beta = +INF;

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
