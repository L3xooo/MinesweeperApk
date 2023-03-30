package sk.fei.stuba.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tile extends JPanel {
    private final ImageIcon FLAG_ICON = new ImageIcon(getClass().getResource("/flag.png").getPath());
    private final ImageIcon BOMB_ICON = new ImageIcon(getClass().getResource("/bomb.png").getPath());
    int bombsAround;
    boolean bombStatus;
    boolean discovered;
    boolean flagStatus;
    JButton btn;
    List<Tile> around;
    Board board;
    MouseClickHandler handler;

    public Tile(Board board){
        setLayout(new GridLayout());
        this.discovered = false;
        this.bombsAround = 0;
        this.bombStatus = false;
        this.board = board;
        this.flagStatus = false;
        this.around = new ArrayList<>();
        this.btn = new JButton(){
            @Override
            public void setEnabled(boolean b) {
                if(!getDiscovered()) {
                    super.setEnabled(b);
                }
            }
        };
        this.handler = new MouseClickHandler(this);
        btn.addMouseListener(handler);
        this.add(btn);
    }

    public MouseClickHandler getHandler() { return this.handler; }
    public JButton getBtn() { return this.btn; }
    public boolean getDiscovered() { return !this.discovered; }
    public void setDiscovered() { this.discovered = true; }
    public List<Tile> getAround() { return this.around; }
    public void addBombsAround() { this.bombsAround += 1; }
    public int getBombsAround() { return this.bombsAround; }
    public void setBombStatus() { this.bombStatus = true; }
    public boolean getBombStatus(){
        return this.bombStatus;
    }
    public Board getBoard() { return this.board; }
    public ImageIcon getFlagIcon() { return FLAG_ICON; }
    public ImageIcon getBombIcon() { return BOMB_ICON; }

    public void setFlagStatus(boolean status) { this.flagStatus = status;} //asi pojde prec
    public void checkAround() {
        this.getBtn().setIcon(null);
        this.setFlagStatus(false);
        if (this.getBombsAround() != 0) {
            this.getBoard().addGoodBoxes();
            this.setDiscovered();
            this.getBtn().setEnabled(false);
            this.getBtn().setText(Integer.toString(this.getBombsAround()));
            return;
        }
        List<Tile> tiles = new ArrayList<>();
        tiles.add(this);
        while (!tiles.isEmpty()) {
            Tile centerTile = tiles.get(0);
            if (centerTile.getDiscovered()) {
                this.getBoard().addGoodBoxes();
                centerTile.setDiscovered();
                centerTile.getBtn().setEnabled(false);
            }
            for (int a = 0; a < centerTile.getAround().size(); a++) {
                Tile actual = centerTile.getAround().get(a);
                if (actual.getBombsAround() != 0 && actual.getDiscovered()) {
                    actual.setDiscovered();
                    actual.getBtn().setIcon(null);
                    this.getBoard().addGoodBoxes();
                    actual.getBtn().setText(Integer.toString(actual.getBombsAround()));
                    actual.getBtn().setEnabled(false);
                    continue;
                }
                if (actual.getDiscovered()) {
                    actual.getBtn().setIcon(null);
                    this.getBoard().addGoodBoxes();
                    tiles.add(actual);
                    actual.setDiscovered();
                    actual.getBtn().setEnabled(false);
                }
            }
            tiles.remove(centerTile);
        }
    }

    public ImageIcon scaleBombImage() {
        ImageIcon icon = this.getBombIcon();
        Image scaledImage = icon.getImage().getScaledInstance(this.getBtn().getWidth(),
                this.getBtn().getHeight(),Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }
    public ImageIcon scaleFlagImage() {
        ImageIcon icon = this.getFlagIcon();
        Image scaledImage = icon.getImage().getScaledInstance(this.getBtn().getWidth(),
                this.getBtn().getHeight(),Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }

   /* public ImageIcon flagIconInitialization() {
        ImageIcon icon = new ImageIcon("C:\\Users\\likav\\OneDrive\\Documents\\VSCODE\\Java\\FEI\\Cvicenie6Minesweeper\\flag.png");
        Image scaledImage = icon.getImage().getScaledInstance(this.getBtn().getWidth(),
                this.getBtn().getHeight(),Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }
    public ImageIcon bombIconInitialization() {
        Image scaledImage = icon.getImage().getScaledInstance(this.getBtn().getWidth(),
                this.getBtn().getHeight(),Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }*/
}
