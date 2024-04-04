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
import java.awt.image.BufferedImage;
import blitzstrike.model.Cell;
import blitzstrike.model.Game;
import blitzstrike.model.Effect;
import blitzstrike.model.Monster;
import blitzstrike.model.Player;
import blitzstrike.model.Box;
import blitzstrike.model.PowerUp;
import blitzstrike.model.Curse;
import blitzstrike.model.EmptyEffect;
import blitzstrike.model.Wall;
import blitzstrike.model.Empty;
import blitzstrike.model.Bomb;

public class View extends JPanel {

    private final Game game;
    private final Image player1, player2, monster1, monster2, monster3, monster4, bomb, powerUp, curse, box, wall, empty, explosion;
    private double scale;
    private int scaled_size;
    private final int tile_size = 42;

    private BufferedImage buffer;

    public View(Game g) throws IOException {
        game = g;
        scale = 1.0;
        scaled_size = (int) (scale * tile_size);
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
        explosion = ResourceLoader.loadImage("blitzstrike/res/explosion.png");
        buffer = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
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
        super.paintComponent(g);
        //if (!game.isLevelLoaded()) return;
        Graphics2D gr = (Graphics2D) g;
        for (int y = 0; y < game.MAP_SIZE; y++) {
            for (int x = 0; x < game.MAP_SIZE; x++) {
                Image img = null;
                Box b = game.getBox(x, y);

                if (b != null) {
                    if (b.isDestroyed()) {
                        Effect effect = b.getEffect();
//                        System.out.println("Empty? " + effect.isEmpty());
//                        System.out.println("Curse? " + effect.isCurse());
//                        System.out.println("Powerup? " + effect.isPowerup());
                        if (effect.isPowerup()) {
                            System.out.println("YES IT IS POWER UP");
                            img = powerUp;
                        } else if (effect.isCurse()) {
                            System.out.println("YES IT IS CURSE");
                            img = curse;
                        } else if (effect.isEmpty()) {
                            System.out.println("YES IT IS EMPTY CELL");
                            img = empty;
                        }
                    } else {
                        img = box;
                    }
                } else {
                    if (game.isBombAtPosition(x, y)) {
//                        if(game.isExplosionInProgress()){
//                            drawExplosion(gr, game.getBomb(x, y), scaled_size);
//                        }
                        img = bomb;
                    }
                    Monster m = game.getMonster(x, y);
                    Player p1 = game.getPlayer1(x, y);
                    Player p2 = game.getPlayer2(x, y);
                    Wall w = game.getWall(x, y);
                    Empty e = game.getEmpty(x, y);

                    if (m != null) {
                        img = monster1;
                    } else if (p1 != null) {
                        img = player1;
                    } else if (p2 != null) {
                        img = player2;
                    } else if (w != null) {
                        img = wall;
                    } else if (e != null) {
                        img = empty;
                    }
                }

                if (img != null) {
                    gr.drawImage(img, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
                }
                //placing bomb
                for (Bomb bomb1 : game.getPlayer11().getBombs()) {
                    gr.drawImage(this.bomb, bomb1.getPosition().getX() * scaled_size, bomb1.getPosition().getY() * scaled_size, scaled_size, scaled_size, null);
                }
                for (Bomb bomb2 : game.getPlayer22().getBombs()) {
                    gr.drawImage(this.bomb, bomb2.getPosition().getX() * scaled_size, bomb2.getPosition().getY() * scaled_size, scaled_size, scaled_size, null);
                }
            }
        }
        //g.drawImage(buffer, 0, 0, null);
    }

    public void drawExplosion(Graphics2D gr, Bomb bomb, int scaled_size) {
        int bombX = bomb.getPosition().getX();
        int bombY = bomb.getPosition().getY();

        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                if (Math.abs(dx) + Math.abs(dy) <= 2) {
                    int x = bombX + dx;
                    int y = bombY + dy;
                    if (x >= 0 && x < game.MAP_SIZE && y >= 0 && y < game.MAP_SIZE) {
                        gr.drawImage(explosion, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
                    }
                }
            }
        }
    }

}
