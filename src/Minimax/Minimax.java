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
public class Minimax extends AMinimax {
    
    @Override
    public Move findBestMove(Game game, int depth) {

        if (timeLimit > 0) {
            timer = new Timer(timeLimit, this);
            timer.start();
            timeIsUp = false;
            return findBestMoveID(game, depth);
        }

        System.out.println("Minimax");

        ArrayList<Move> moveList = new ArrayList<Move>();        
        moveList = game.getPlayerOnTurn().generateMoves();
        
        Move bestMove = null;
        int maxValue = 0;

        for(int i = 0; i < moveList.size(); i++) {
            game.getPlayerOnTurn().makeMove(moveList.get(i));
            game.moveMade();

            int value = min(game, depth-1);
            if (i==0){
                maxValue = value;
                bestMove = moveList.get(i);
            }
            else {
                if (value > maxValue){
                    maxValue = value;
                    bestMove = moveList.get(i);
                }
            }
            
            game.getPlayerOnTurn().undoMove(moveList.get(i));
            game.moveUndo();
        }
        
        System.out.println("max val == " + maxValue);
        return bestMove;
    }

    private int max(Game game, int depth){

        if (timeIsUp) return -INF;

        if (depth == 0 || game.isGameOver()) {
            leavesVisitedCnt++;
            leavesEvaluatedCnt++;
            int posValue = game.getPlayerOnPTurn().positionValue();
            if (winValue!=-1 && (posValue >= winValue || posValue<= -winValue))
                posValue*=depth+1;
            return posValue;
        }
        else {
            ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
            int maxValue = 0;
            for(int i = 0; i < moveList.size(); i++) {
                game.getPlayerOnTurn().makeMove(moveList.get(i));
                game.moveMade();

                int value = min(game, depth-1);
                if (i==0){
                    maxValue = value;
                }
                else {
                    if (value > maxValue) {
                        maxValue = value;
                    }
                }
                game.getPlayerOnTurn().undoMove(moveList.get(i));
                game.moveUndo();
            }
            return maxValue;
        }
    }

    private int min(Game game, int depth){

        if (timeIsUp) return INF;

        if (depth == 0 || game.isGameOver()) {
            leavesVisitedCnt++;
            leavesEvaluatedCnt++;
            int posValue = game.getPlayerOnPTurn().positionValue();
            if (winValue!=-1 && (posValue >= winValue || posValue<= -winValue))
                posValue*=depth+1;

            return posValue;
        }
        else {
            ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
            int minValue = 0;
            for(int i = 0; i < moveList.size(); i++) {
                game.getPlayerOnTurn().makeMove(moveList.get(i));
                game.moveMade();

                int value = max(game, depth-1);
                if (i==0){
                    minValue = value;
                }
                else {
                    if (value < minValue) {
                        minValue = value;
                    }
                }
                game.getPlayerOnTurn().undoMove(moveList.get(i));
                game.moveUndo();
            }
            return minValue;
        }
    }

    private Move findBestMoveID(Game game, int depth) {

        System.out.println("Minimax (iterative deepening)");
        
        ArrayList<Move> moveList = game.getPlayerOnTurn().generateMoves();
        ArrayList<Integer> valueList = new ArrayList<Integer>();
        valueList.ensureCapacity(moveList.size());
        for(int i = 0; i < moveList.size(); i++)
            valueList.add(-INF);

        Move currentBestMove = null;
        Move bestMove = null;
        int bestValue = 0;
        int depthSearched = 0;
        int maxValue = 0;

        for(int d = 2; d <= depth; d++){
            for(int i = 0; i < moveList.size(); i++) {
                game.getPlayerOnTurn().makeMove(moveList.get(i));
                game.moveMade();

                int value = min(game, d-1);
                valueList.set(i, value);

                if (i==0){
                    maxValue = value;
                    currentBestMove = moveList.get(i);
                }
                else {
                    if (value > maxValue){
                        maxValue = value;
                        currentBestMove = moveList.get(i);
                    }
                }

                game.getPlayerOnTurn().undoMove(moveList.get(i));
                game.moveUndo();
            }

            if (!timeIsUp) {
                bestMove = currentBestMove;
                depthSearched = d;
                bestValue = maxValue;
            }
            else {
                break;
            }

            //ovo sortiranje kod minimaxa nema potrebe raditi
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
