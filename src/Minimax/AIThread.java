/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;

public class AIThread extends Thread {

    private Game game;
    private int depth;
    private Player player;
    private AMinimax ai;
    
    public AIThread(AMinimax ai, Game game, int depth, Player player){
        this.game = game;
        this.depth = depth;
        this.player = player;
        this.ai = ai;
    }

    public void run(){
        Move move = ai.findBestMove(game, depth);
        player.makeMove(move);
        player.commitMove();
    }

}
