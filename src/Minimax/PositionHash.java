/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;

public abstract class PositionHash implements Comparable<PositionHash>{

    protected int tableSize;

    public abstract int getIHash();

    public PositionHash(){
        tableSize = 100000;
    }

    public PositionHash(int tableSize){
        this.tableSize = tableSize;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }
    
}