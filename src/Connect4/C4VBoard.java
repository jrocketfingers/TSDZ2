/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class C4VBoard extends JPanel implements Updateable {

    private C4Game game;
    private C4Board board;

    private boolean whitePlayerEnabled = false;
    private boolean blackPlayerEnabled = false;
    private int cellSize = 30;
    private int offset = 3;
    private boolean yIsComputer = false;
    private boolean xIsComputer = false;

    private JTextArea gameLog = null;
    private boolean testMode;
    private JTable tableLog;

    public C4VBoard(C4Game game){
        this.game = game;
        this.board = (C4Board)game.getBoard();
        setSize(cellSize * board.WIDTH + 1, cellSize * board.HEIGHT + 1);
        setBackground(new Color(130, 130, 130));

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                C4VBoard.this.mouseReleased(evt);
            }
        });

        setDoubleBuffered(true);
    }

    public C4VBoard(C4Game game, int cellSize) {
        this(game);
        setSize(cellSize*board.WIDTH+1, cellSize*board.HEIGHT+1);
    }

    public void updateState() {
        repaint();
    }

    private void mouseReleased(MouseEvent evt) {
        

        int [][] b = ((C4Board)game.getBoard()).getBoard();
        int [] freePos = ((C4Board)game.getBoard()).getFreePos();

        if (!testMode){
            if (game.isGameOver()) return;
            if (game.isWhitesPTurn()){
                if (whitePlayerEnabled && game.isWhitesPTurn()) {
                    int x = evt.getPoint().x / cellSize;
                    //int y = evt.getPoint().y / cellSize;
                    if (freePos[x] < board.HEIGHT){
                        game.getWhite().makeMove(new C4Move(x, freePos[x], 1));
                        game.getWhite().commitMove();
                    }
                }
            }
            else {
                if (blackPlayerEnabled && !game.isWhitesPTurn()) {
                    int x = evt.getPoint().x / cellSize;
                    //int y = evt.getPoint().y / cellSize;
                    if (freePos[x] < board.HEIGHT){
                        game.getBlack().makeMove(new C4Move(x, freePos[x], 2));
                        game.getBlack().commitMove();
                    }
                }
            }
        }
        else {
            int x = evt.getPoint().x / cellSize;
            int y = evt.getPoint().y / cellSize;

            if (evt.getButton() == MouseEvent.BUTTON1) {
                if (b[5-y][x] == 0) b[5-y][x] = 1;
                else b[5-y][x] = 0;
            }
            else {
                if (b[5-y][x] == 0) b[5-y][x] = 2;
                else b[5-y][x] = 0;
            }
            repaint();

        }
    }

    private boolean signal = false;
    private boolean signalled = false;

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        int height = board.HEIGHT;
        int width = board.WIDTH;
        int [][] b = board.getOutputBoard();

        for(int i = 0; i <= height; i++){
            g.drawLine(0, i*cellSize, width*cellSize, i*cellSize);
        }
        for(int i = 0; i <= width; i++){
            g.drawLine(i*cellSize, 0, i*cellSize, height*cellSize);
        }

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if (b[i][j] == 1){
                    g.setColor(new Color(255, 255, 0));
                    g.fillOval(j*cellSize+offset, (height-1-i)*cellSize+offset, cellSize-2*offset, cellSize-2*offset);
                }
                else if (b[i][j] == 2) {
                    g.setColor(new Color(90, 150, 220));
                    g.fillOval(j*cellSize+offset, (height-1-i)*cellSize+offset, cellSize-2*offset, cellSize-2*offset);
                }   
            }
        }

        
    }

    public void setBlackPlayerEnabled(boolean blackPlayerEnabled) {
        this.blackPlayerEnabled = blackPlayerEnabled;
    }

    public void setWhitePlayerEnabled(boolean whitePlayerEnabled) {
        this.whitePlayerEnabled = whitePlayerEnabled;
    }

    public void newMoveData(String moveData) {
        if(gameLog != null)
            gameLog.append(moveData);
    }

    public void setGameLog(JTextArea gameLog, JTable tableLog){
        this.gameLog = gameLog;
        this.tableLog = tableLog;
    }

    public void updateGameLog(ArrayList<String> log){
        gameLog.setText("");
        for(int i = 0; i < log.size(); i++)
            gameLog.append(log.get(i));

        gameLog.setCaretPosition(gameLog.getText().length() - 1);

//        DefaultTableModel model = new DefaultTableModel();
//        model.addColumn("beli");
//        model.addColumn("crni");
//        for(int i = 0; i < log.size()/2; i++){
//            String [] row = {""+log.get(2*i),((2*i+1<log.size())?log.get(2*i+1):" ")};
//            model.insertRow(model.getRowCount(), row);
//        }
//        if (log.size()%2 == 1){
//            String [] row = {""+log.get(log.size()-1)," "};
//            model.insertRow(model.getRowCount(), row);
//        }
//        tableLog.setModel(model);
//        Rectangle r = tableLog.getCellRect(tableLog.getRowCount()-1, 0, true);
//        tableLog.scrollRectToVisible(r);
    }

    void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public void setSignal(boolean signal) {
        this.signal = signal;
    }

    public void signalGameOver() {
        if (!signal && game.isGameOver()) {
            signal = true;
            new SignalGameOver(game, this).start();
        }
    }



    
    
}
