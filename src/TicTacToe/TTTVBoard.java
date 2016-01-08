/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class TTTVBoard extends JPanel implements Updateable{

    private TTTGame game;
    private TTTBoard board;

    private boolean whitePlayerEnabled = false;
    private boolean blackPlayerEnabled = false;
    private int cellSize = 30;
    private int offset = 3;
    private boolean yIsComputer = false;
    private boolean xIsComputer = false;

    public TTTVBoard(TTTGame game) {
        this.game = game;
        this.board = (TTTBoard)game.getBoard();
        setSize(cellSize*board.getDimension()+1, cellSize*board.getDimension()+1);

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TTTVBoard.this.mouseReleased(evt);
            }
        });
    }

    public TTTVBoard(TTTGame game, int cellSize) {
        this(game);
        this.cellSize = cellSize;
        setSize(cellSize*board.getDimension()+1, cellSize*board.getDimension()+1);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int dim = board.getDimension();
        int [][] b = board.getBoard();

        //dim+1 vertikalnih linija
        //i dim+1 horizontalnih linija
        for(int i = 0; i <= dim; i++){
            g.drawLine(i*cellSize, 0, i*cellSize, dim*cellSize);
            g.drawLine(0, i*cellSize, dim*cellSize, i*cellSize);
        }
        for(int i = 0; i < dim; i++)
            for(int j = 0; j < dim; j++){
                if (b[i][j] == 1){
                    g.setColor(Color.GREEN);
                    g.fillOval(i*cellSize+offset, j*cellSize+offset, cellSize-2*offset, cellSize-2*offset);
                }
                else if (b[i][j] == 2) {
                    g.setColor(Color.RED);
                    g.fillOval(i*cellSize+offset, j*cellSize+offset, cellSize-2*offset, cellSize-2*offset);
                }
            }
    }

    private void mouseReleased(MouseEvent evt) {

        if (game.isGameOver()) return;

        int [][] b = ((TTTBoard)game.getBoard()).getBoard();
        if (game.getWhite().isMyTurn()){
            if (whitePlayerEnabled && game.getWhite().isMyTurn()) {
                int x = evt.getPoint().x / cellSize;
                int y = evt.getPoint().y / cellSize;
                if (b[x][y] == 0){
                    game.getWhite().makeMove(new TTTMove(x, y, 1));
                    game.getWhite().commitMove();
                    //repaint();
                }
            }
        }
        else {
            if (blackPlayerEnabled && game.getBlack().isMyTurn()) {
                int x = evt.getPoint().x / cellSize;
                int y = evt.getPoint().y / cellSize;
                if (b[x][y] == 0){
                    game.getBlack().makeMove(new TTTMove(x, y, 2));
                    game.getBlack().commitMove();
                    //repaint();
                }
            }
        }
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
        setSize(cellSize*board.getDimension()+1, cellSize*board.getDimension()+1);
    }

    public void setBlackPlayerEnabled(boolean blackPlayerEnabled) {
        this.blackPlayerEnabled = blackPlayerEnabled;
    }

    public void setWhitePlayerEnabled(boolean whitePlayerEnabled) {
        this.whitePlayerEnabled = whitePlayerEnabled;
    }

    public void updateState() {
        repaint();
    }





}
