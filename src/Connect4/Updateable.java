/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

interface Updateable {

    public void updateState();

    public void newMoveData(String moveData);

    public void signalGameOver();

}

