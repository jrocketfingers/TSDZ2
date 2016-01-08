/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TicTacToe;

import Minimax.PositionCode;

public class TTTCode extends PositionCode{

    private int code;

    public TTTCode(int code){
        this.code = code;
    }

    public int compareTo(PositionCode co) {
        TTTCode c = (TTTCode)co;
        if (code < c.code) return -1;
        else if (code == c.code) return 0;
        else return 1;
    }

    public int getCode(){
        return code;
    }

    

}
