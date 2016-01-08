/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;


public class WTMapEntry {
    
    int value;
    int type;

    public WTMapEntry(int value, int type) {
        this.value = value;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
    
}
