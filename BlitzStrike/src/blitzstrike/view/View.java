/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.view;

/**
 *
 * @author aliia
 */
import blitzstrike.model.Position;
import java.awt.Color;
// import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JPanel;
import res.ResourceLoader;

public class View extends JPanel {
//    private final Game game;
//    private final Image player1, player2, monster1, monster2, monster3, monster4, bomb, powerUp, curse, box, wall, empty;
    private double scale;
    private int scaled_size;
    private final int tile_size = 48;
    
//    public Board(Game g) throws IOException{
//        game = g;
//        scale = 1.0;
//        scaled_size = (int)(scale * tile_size);
//        player1 = ResourceLoader.loadImage("res/player1.png");
//        player2 = ResourceLoader.loadImage("res/player2.png");
//        monster1 = ResourceLoader.loadImage("res/monster1.png");
//        monster2 = ResourceLoader.loadImage("res/monster2.png");
//        monster3 = ResourceLoader.loadImage("res/monster3.png");
//        monster4 = ResourceLoader.loadImage("res/monster4.png");
//        bomb = ResourceLoader.loadImage("res/bomb.png");
//        powerUp = ResourceLoader.loadImage("res/powerup.png");
//        curse = ResourceLoader.loadImage("res/curse.png");
//        box = ResourceLoader.loadImage("res/box.png");
//        wall = ResourceLoader.loadImage("res/wall.png");
//        empty = ResourceLoader.loadImage("res/empty.png");
//    }
    
    /*
    Probably not needed, if the user cannot set up the scale

    public boolean setScale(double scale){
        this.scale = scale;
        scaled_size = (int)(scale * tile_size);
        return refresh();
    }
    
     
    public boolean refresh(){
        Dimension dim = new Dimension(game.getLevelCols() * scaled_size, game.getLevelRows() * scaled_size);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
        return true;
    }*/
    
//    @Override
//    protected void paintComponent(Graphics g) {
//        Color semiTransparentBlack = new Color(0, 0, 0);
//        if (!game.isLevelLoaded()) return;
//        Graphics2D gr = (Graphics2D) g;
//        int w = game.getLevelCols();
//        int h = game.getLevelRows();
//        Position p1 = game.getPlayer1Pos();
//        Position p2 = game.getPlayer2Pos();
//        
//        // The rest is to be implemented
//    }
}


