/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import Minimax.PositionHash;

public class C4PositionHash extends PositionHash {

    private int hashCode;

    public C4PositionHash(int hashCode){
        this.hashCode = hashCode;
    }

    public C4PositionHash(int hashCode, int tableSize){
        super(tableSize);
        this.hashCode = hashCode;
    }

    public long getHashCode() {
        return hashCode;
    }

    public int compareTo(PositionHash positionHash) {
        C4PositionHash c4ph = (C4PositionHash)positionHash;
        if (hashCode < c4ph.hashCode) return -1;
        else if (hashCode == c4ph.hashCode) return 0;
        else return 1;
    }

    @Override
    public int getIHash() {
        return (int)(hashCode / tableSize);
    }
    
}
