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
import blitzstrike.model.Cell;
import blitzstrike.model.Game;
import blitzstrike.model.Effect;
import blitzstrike.model.Monster;
import blitzstrike.model.Player;
import javax.swing.Box;
//import blitzstrike.model.Curse;

public class View extends JPanel {
    private final Game game;
    private final Image player1, player2, monster1, monster2, monster3, monster4, bomb, powerUp, curse, box, wall, empty;
    private double scale;
    private int scaled_size;
    private final int tile_size = 48;
    
    public View(Game g) throws IOException{
        game = g;
        scale = 1.0;
        scaled_size = (int)(scale * tile_size);
        player1 = ResourceLoader.loadImage("blitzstrike/res/player1.png");
        player2 = ResourceLoader.loadImage("blitzstrike/res/player2.png");
        monster1 = ResourceLoader.loadImage("blitzstrike/res/monster1.png");
        monster2 = ResourceLoader.loadImage("blitzstrike/res/monster2.png");
        monster3 = ResourceLoader.loadImage("blitzstrike/res/monster3.png");
        monster4 = ResourceLoader.loadImage("blitzstrike/res/monster4.png");
        bomb = ResourceLoader.loadImage("blitzstrike/res/bomb.png");
        powerUp = ResourceLoader.loadImage("blitzstrike/res/powerup.png");
        curse = ResourceLoader.loadImage("blitzstrike/res/curse.png");
        box = ResourceLoader.loadImage("blitzstrike/res/box.png");
        wall = ResourceLoader.loadImage("blitzstrike/res/wall.png");
        empty = ResourceLoader.loadImage("blitzstrike/res/empty.png");
    }
    
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
    
    @Override
    protected void paintComponent(Graphics g) {
        Color semiTransparentBlack = new Color(0, 0, 0);
        //if (!game.isLevelLoaded()) return;
        Graphics2D gr = (Graphics2D) g;
        //int w = game.getLevelCols();
        //int h = game.getLevelRows();
        //Position p1 = game.getPlayer1Pos();
        //Position p2 = game.getPlayer2Pos();
        
//        // The rest is to be implemented
//        for (int y = 0; y < game.MAP_SIZE; y++) {
//            for (int x = 0; x < game.MAP_SIZE; x++) {
//                Image img = null;
//                Box box = game.getBox(x, y);
//
//                // Check if there's a box at this position
//                if (box != null) {
//                    if (box.getEffect() instanceof PowerUp) {
//                        gr.drawImage(powerUp, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
//                    } else if (box.getEffect() instanceof Curse) {
//                        gr.drawImage(curse, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
//                    } else if (box.getEffect() instanceof EmptyEffect) {
//                        //gr.drawImage(p, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
//                        continue;
//                    }
//                } else {
//                    Monster monster = game.getMonster(x, y);
//                    Player player = game.getPlayer(x, y);
//                    Wall wall = game.getWall(x, y);
//                    Empty empty = game.getEmpty(x, y);
//
//                    if (monster != null) {
//                        gr.drawImage(monster1, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
//                    } else if (player != null) {
//                        gr.drawImage(player, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
//                    } else if (wall != null) {
//                        gr.drawImage(wall, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
//                    } else if (empty != null) {
//                        gr.drawImage(empty, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
//                    }
//                }
//            }
//        }

    }
}


// The problem with monsterType and which player is playing i cannot specify which one
// Also the inherited classes are not being seen, but strangely power up and curse classes as well


