/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Timer extends Thread {

    private int time;
    private AMinimax iMinimax;

    public Timer(int time, AMinimax iMinimax){
        this.time = time;
        this.iMinimax = iMinimax;
    }

    @Override
    public void run() {
        try {
            sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
        }
        iMinimax.timeIsUp();
    }

}
