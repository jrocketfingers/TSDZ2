/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Minimax;



//Entry for transposition table that will keep position values
public class VTTEntry {

    private PositionHash positionHash;
    private Integer value;

    public VTTEntry(PositionHash positionHash, Integer value) {
        this.positionHash = positionHash;
        this.value = value;
    }

    public PositionHash getPositionHash() {
        return positionHash;
    }

    public Integer getValue() {
        return value;
    }

}
