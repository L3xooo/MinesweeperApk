package sk.fei.stuba.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JFrame {
    int columns;
    int rows;
    int bombs;
    Tile[][] tileArray;
    List<Tile> bombTiles;
    Random rand;
    int goodBoxes;

    public Board(){
        setTitle("Minesweeper");
        setSize(450,450);
        setResizable(false);
        this.rows = 10;
        this.columns = 10;
        setLayout(new GridLayout(rows,columns));
        this.bombs = 16;
        this.rand = new Random();
        this.goodBoxes = 0;
        this.tileArray = new Tile[rows][columns];
        this.bombTiles = new ArrayList<>();

        for(int a = 0; a < this.rows; a++){
            for(int b = 0; b < this.columns; b++){
                this.tileArray[a][b] = new Tile(this);
                this.add(tileArray[a][b]);
            }
        }
        this.initBombs();
        this.initAround();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public Tile[][] getTileArray() {
        return tileArray;
    }
    public List<Tile> getBombTiles() { return this.bombTiles; }
    public int getColumns() { return this.columns; }
    public int getRows() { return this.rows; }
    public int getBombs() { return this.bombs; }
    public int getGoodBoxes() { return this.goodBoxes; }
    public void addGoodBoxes() { this.goodBoxes += 1; }
    public void initBombs() {
        int count = 0;
        while ( count < this.getBombs()) {
            int row = this.rand.nextInt(this.getRows()-1);
            int col = this.rand.nextInt(this.getColumns()-1);
            Tile tile = this.getTileArray()[row][col];
            this.getBombTiles().add(tile);
            if ( tile.getBombStatus() ) {
                continue;
            } else {
                tile.setBombStatus();
                count++;
            }
            initBombsAround(row,col);
        }
    }
    public void initBombsAround(int row,int col) {
        if(row-1 >= 0) {
            this.getTileArray()[row-1][col].addBombsAround();
            if(col-1 >= 0) {
                this.getTileArray()[row-1][col-1].addBombsAround();
            }
            if (col+1 <= this.getColumns()) {
                this.getTileArray()[row-1][col+1].addBombsAround();
            }
        }
        if (row+1 <= this.getRows()) {
            this.getTileArray()[row+1][col].addBombsAround();
            if(col-1 >= 0) {
                this.getTileArray()[row+1][col-1].addBombsAround();
            }
            if (col+1 <= this.getColumns()) {
                this.getTileArray()[row+1][col+1].addBombsAround();
            }
        }
        if(col-1 >= 0) {
            this.getTileArray()[row][col-1].addBombsAround();
        }
        if (col+1 <= this.getColumns()) {
            this.getTileArray()[row][col+1].addBombsAround();
        }
    }
    public void initAround() {
        for (int a = 0; a < this.getRows(); a++) {
            for (int b = 0; b < this.getColumns(); b++) {
                Tile tmp = this.getTileArray()[a][b];

                if(a-1 >= 0) {
                    tmp.getAround().add(this.getTileArray()[a-1][b]);
                    if(b-1 >= 0) {
                        tmp.getAround().add(this.getTileArray()[a-1][b-1]);
                    }
                    if (b+1 < this.getColumns()) {
                        tmp.getAround().add(this.getTileArray()[a-1][b+1]);
                    }
                }
                if (a+1 < this.getRows()) {
                    tmp.getAround().add(this.getTileArray()[a+1][b]);
                    if(b-1 >= 0) {
                        tmp.getAround().add(this.getTileArray()[a+1][b-1]);
                    }
                    if (b+1 < this.getColumns()) {
                        tmp.getAround().add(this.getTileArray()[a+1][b+1]);
                    }
                }
                if(b-1 >= 0) {
                    tmp.getAround().add(this.getTileArray()[a][b-1]);
                }
                if (b+1 < this.getColumns()) {
                    tmp.getAround().add(this.getTileArray()[a][b+1]);
                }
            }
        }
    }

    public void disabledBoard() {
        for (int a = 0; a < this.getRows(); a++) {
            for (int b = 0; b < this.getColumns(); b++) {
                Tile tile = this.getTileArray()[a][b];
                tile.getBtn().setEnabled(false);

                tile.getBtn().removeMouseListener(tile.getHandler());
            }
        }
    }

    public void showBombs() {
        for(Tile tile : this.getBombTiles()) {
            tile.getBtn().setIcon(tile.scaleBombImage());
            tile.getBtn().removeMouseListener(tile.getHandler());
        }
    }

    public void checkWin() {
        if (this.getGoodBoxes() == getColumns()*getRows()-getBombs()) {
            System.out.println("You win!!!");
            disabledBoard();
            JOptionPane.showMessageDialog(this,"YOU WON");

        }
    }
}
