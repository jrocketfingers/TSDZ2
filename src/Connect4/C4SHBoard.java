/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import Minimax.PositionHash;

public class C4SHBoard extends C4Board{
   
    int hash;

    @Override
    public void initialize() {
        super.initialize();
        hash = 0;
    }

    @Override
    public PositionHash getPositionHash() {
        return new C4PositionHash(hash);
    }





}
