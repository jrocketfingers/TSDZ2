/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;

/**
 *
 * @author Drazen Draskovic
 */
public abstract class AMinimax {

    protected int additionalDepth = 0;
    protected int winValue = 0;
    protected int lowBound = 0;
    protected int highBound = 0;
    protected int INF = 2000000000;

    protected Timer timer = null;
    protected int timeLimit;
    protected boolean timeIsUp;

    protected int leavesVisitedCnt;
    protected int leavesEvaluatedCnt;
    protected int additionalSearchCnt;

    public abstract Move findBestMove(Game game, int depth);

    public void setWinValue(int winValue) {
        this.winValue = winValue;
    }

    public void resetWinValue() {
        winValue = 0;
    }

    public void setBoundsForAdditionalSearch(int lowBound, int highBound) {
        this.lowBound = lowBound;
        this.highBound = highBound;
    }

    public void resetBoundsForAdditionalSearch() {
        this.lowBound = 0;
        this.highBound = 0;
    }

    public void setAdditionalDepth(int additionalDepth) {
        this.additionalDepth = additionalDepth;
    }

    public int getAdditionalDepth() {
        return additionalDepth;
    }
    
    public void setTimeLimit(int miliseconds){
        if (miliseconds > 0) {
            timeLimit = miliseconds;
        }
        else {
            timeLimit = 0;
        }
    }

    public void resetTimeLimit(){
        timeLimit = 0;
    }

    public void timeIsUp(){
        timeIsUp = true;
    }

}
