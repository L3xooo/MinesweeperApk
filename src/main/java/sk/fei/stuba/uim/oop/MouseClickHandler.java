package sk.fei.stuba.uim.oop;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickHandler implements MouseListener {
    private final Tile tile;
    MouseClickHandler (Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() { return this.tile; }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) {
            /*this.getTile().getBoard().dispose(); //restart hry
            new Board();*/

            if (this.getTile().getBtn().getIcon() != null) {
                this.getTile().getBtn().setIcon(null);
                this.getTile().setFlagStatus(false);
            } else {
                this.getTile().getBtn().setIcon(this.getTile().scaleFlagImage());
                this.getTile().setFlagStatus(true);
            }
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            if (this.getTile().getDiscovered()) {
                if (this.getTile().getBombStatus()) {
                    this.getTile().getBtn().setIcon(this.getTile().scaleBombImage());
                    this.getTile().getBtn().removeMouseListener(this);
                    this.getTile().getBoard().showBombs();
                    this.getTile().getBoard().disabledBoard();
                    JOptionPane.showMessageDialog(this.getTile().getBoard(),"LOSER");

                } else {
                    Tile actualTile = this.getTile();
                    actualTile.checkAround();
                    actualTile.getBoard().checkWin();
                }
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
