/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;

public class TTEntry {

    private PositionHash positionHash;
    private int value;
    private int type;
    private int depth;

    public TTEntry(PositionHash positionHash, int value, int type, int depth) {
        this.positionHash = positionHash;
        this.value = value;
        this.type = type;
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public PositionHash getPositionHash() {
        return positionHash;
    }

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
    
}
