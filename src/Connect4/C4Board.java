/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import java.util.ArrayList;
import Minimax.Board;
import Minimax.PositionCode;
import Minimax.Game;
import Minimax.PositionHash;

public class C4Board extends Board {

    public final int WIDTH = 7;
    public final int HEIGHT = 6;

    protected int [][] outputBoard = new int[HEIGHT][WIDTH];
    protected int [][] board = new int[HEIGHT][WIDTH];
    protected int [] freePos = new int[WIDTH];

    protected C4VBoard vBoard = null;

    protected Game game;

    @Override
    public PositionCode getPositionCode() {
        int [] r = new int[6];
        
        for(int i = 0; i < HEIGHT; i++){
            r[i] = 0;
            for (int j = 0; j < WIDTH; j++){
                r[i] *= 4;
                r[i] += board[i][j];
            }
        }
        
        return new C4PositionCode(r[0]*65536 + r[1], r[2]*65536 + r[3], r[4]*65536 + r[5]);

    }

    @Override
    public void initialize() {
        super.initialize();
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                board[i][j] = outputBoard[i][j] = 0;
            }
        }
        for(int i = 0; i < WIDTH; i++){
            freePos[i] = 0;
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int[][] getOutputBoard() {
        return outputBoard;
    }

    public int[] getFreePos() {
        return freePos;
    }

    public boolean isFull() {
        return game.getMoveCount() == WIDTH * HEIGHT;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void updateVisualBoard() {
        vBoard.updateState();
    }

    public void setVBoard(C4VBoard vBoard) {
        this.vBoard = vBoard;
    }

    @Override
    public void updateGameLog(ArrayList<String> log){
        vBoard.updateGameLog(log);
    }

    @Override
    public PositionHash getPositionHash() {
        int hash = 0;
//        for(int i = 0; i < HEIGHT; i++)
//            for(int j = 0; j < WIDTH; j++)
//                if (board[i][j] != 0)
//                    hash = hash ^ zorbistArray[board[i][j]-1][i][j];

        for(int j = 0; j < WIDTH; j++)
            for(int i = 0; i < HEIGHT; i++){
                if (board[i][j] == 0) break;
                hash = hash ^ zorbistArray[board[i][j]-1][i][j];
            }
        
        return new C4PositionHash(hash);
    }

    public void copyBoard() {
        for(int i = 0; i < HEIGHT; i++)
            for(int j = 0; j < WIDTH; j++)
                outputBoard[i][j] = board[i][j];
    }





    @Override
    public void signalGameOver() {
        vBoard.signalGameOver();
    }

    //    long [][][] zorbistArray =
//    {
//      {
//        {883516694839602176L, 990331328341801984L, 9035029934073159680L, 6973002997971673088L, 8653623795958150144L, 1493837635072347136L, 5823550085774658560L},
//    {1211141022322828288L, 8326052385531800576L, 592435321542678528L, 3310198328905844736L, 4125117514731601920L, 5896877395705920512L, 6776054255576083456L},
//    {4234423641876310016L, 4090604715209643008L, 8143203076425859072L, 1846103767112449024L, 5382989619208370176L, 7339516668510859264L, 6454674777249848320L},
//    {2857011981536410624L, 524754322073819136L, 4872100318635207680L, 83911460543107072L, 1383202293889623040L, 5730214634838738944L, 2557597507193090048L},
//    {7392922277546460160L, 185815025278425088L, 7865173055519637504L, 4568582425012555776L, 5503027889192851456L, 3003264380737347584L, 1688409720851294208L},
//    {8161246810773622784L, 1811066840721454080L, 8294662127172433920L, 580116798102292480L, 6537931320243794944L, 6660642381054989312L, 668505772807075840L}
//
//      }
//       ,
//      {
//        {2818302391314120704L, 1256981151175434240L, 2346638027371750400L, 2589065720495673344L, 569486885229717504L, 4154815596610719744L, 1226310184182859776L},
//        {1047761989275389952L, 1783523851750149120L, 7132820153221267456L, 1127806546843722752L, 4564042209133254656L, 351534122899429376L, 8670988050071348224L},
//        {1824160925923900416L, 1049762963827476480L, 6327969849872616448L, 2732820599842344960L, 6145835615735349248L, 2960481615899859968L, 8543418567329471488L},
//        {4457853577966450688L, 6451541876203835392L, 4112697191730676736L, 8881121075297878016L, 6723060255655329792L, 1177239859846501376L, 8307442425856370688L},
//        {7839643347572113408L, 7055518893970348032L, 4803170855859722240L, 4525535762066882560L, 3210779943128824832L, 2382316675692856320L, 1509330947731079168L},
//        {646380980602542080L, 5236029789811561472L, 5153385520691241984L, 4828273981390011392L, 489641786645621760L, 3051198875905431552L, 5939309640998409216L}
//
//      }
//    };

    int [][][] zorbistArray = {
        {
            {161477003, 1814772188, 1238105978, 628907185, 923101616, 1710809578, 38325287},
            {2147183004, 15178828, 685002636, 1507796377, 102378491, 765527567, 243185513},
            {923925485, 756430380, 1925117224, 998222367, 1008039396, 1891512387, 2129364257},
            {1811417525, 1593078157, 1238758029, 1241703367, 956664082, 441305957, 680241029},
            {980737248, 1430237514, 1994026926, 823588525, 305377044, 653750371, 588067290},
            {1119565308, 1016145700, 1929523001, 1195208089, 1065723049, 25888002, 2063167456}
        },
        {
            {1106079267, 490777986, 1378330644, 100136028, 2027790272, 2117415151, 1420034751},
            {1123904418, 1891468972, 1785910987, 240375710, 1346480569, 1718321152, 1081633202},
            {70597745, 1927308271, 835996279, 372586543, 1784178738, 778467494, 1268995362},
            {159154070, 2103231866, 1944698105, 1679088064, 1763958316, 1751404524, 208949906},
            {307936059, 774229308, 620640307, 850167559, 1092955759, 1849762655, 296341478},
            {63060207, 503327496, 1202626393, 246595196, 130729677, 1985389292, 1067808992}
        }};

}
