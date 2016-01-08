/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import Minimax.PositionCode;

public class C4PositionCode extends PositionCode{

    private int code1;
    private int code2;
    private int code3;

    public C4PositionCode(int code1, int code2, int code3){
        this.code1 = code1;
        this.code2 = code2;
        this.code3 = code3;
    }


    public int compareTo(PositionCode code) {
        C4PositionCode c = (C4PositionCode)code;
        if (code1 < c.code1){
            return -1;
        }
        else if (code1 == c.code1 && code2 < c.code2){
            return -1;
        }
        else if (code1 == c.code1 && code2 == c.code2 && code3 < c.code3){
            return -1;
        }
        else if (code1 == c.code1 && code2 == c.code2 && code3 == c.code3){
            return 0;
        }
        else return 1;
    }
    
    @Override
    public String toString() {
        return "" + code1 + " " + code2 + " " + code3;
    }





}
