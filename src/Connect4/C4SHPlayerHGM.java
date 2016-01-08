/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import Minimax.MTTEntry;
import Minimax.Move;
import Minimax.PositionHash;

public class C4SHPlayerHGM extends C4SHPlayer {

    Map<Integer, MTTEntry> map = new TreeMap<Integer, MTTEntry>();

    public C4SHPlayerHGM(int type){
        super(type);
    }

    @Override
    public void clearHash() {
        map.clear();
    }

    @Override
    public ArrayList<Move> generateMovesHTT() {
        ArrayList<Move> moveList = new ArrayList<Move>();
        MTTEntry entry;
        PositionHash hash = game.getBoard().getPositionHash();

        if ((entry = map.get(hash.getIHash()))==null || (entry!=null && entry.getHash().compareTo(hash)!=0)){
            if (game.getBoard().getMoveCount()==0){
                moveList.add(new C4Move(3, 0, piece));
                return moveList;
            }

            //ako imam igrivu pretnju - samo taj potez - to je pobeda!
            for(int i = 0; i < width; i++)
                if (freePos[i] < height && isRealThreat(freePos[i], i)){
                    moveList.add(new C4Move(i, freePos[i], piece));
                    return moveList;
                }

            //ako protivnik ima igrivu pretnju - samo taj potez - to sprecava poraz!
            for(int i = 0; i < width; i++)
                if (freePos[i] < height && ((C4Player)getOponent()).isRealThreat(freePos[i], i)){
                    moveList.add(new C4Move(i, freePos[i], piece));
                    return moveList;
                }

            //genersii sve moguce poteze
            C4Board board = (C4Board)game.getBoard();
            for(int i = 0; i < board.WIDTH; i++)
                if (freePos[i] < board.HEIGHT){
                    moveList.add(new C4Move(i, freePos[i], piece));
                }
            map.put(hash.getIHash(), new MTTEntry(hash, moveList));
        }
        else {
            moveList = entry.getMoves();
        }

        return moveList;
    }



}
