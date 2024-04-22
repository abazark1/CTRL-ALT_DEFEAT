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
// import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JPanel;
import res.ResourceLoader;
import java.awt.image.BufferedImage;
import blitzstrike.model.Game;
import blitzstrike.model.effects.Effect;
import blitzstrike.model.monsters.Monster;
import blitzstrike.model.Player;
import blitzstrike.model.Box;
import blitzstrike.model.Wall;
import blitzstrike.model.Empty;
import blitzstrike.model.Bomb;

public class View extends JPanel {

    private final Game game;
    private final Image player1, player2, monster1, monster2, monster3, monster4, bomb, powerUp, curse, box, wall, empty, explosion, emptyeffect;
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
        emptyeffect = ResourceLoader.loadImage("blitzstrike/res/emptyeffect.png");
        buffer = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
    }

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
                        if (effect.isPowerup()) {
                            img = powerUp;
                        } else if (effect.isCurse()) {
                            img = curse;
                        } else if (effect.isEmpty()) {
                            img = emptyeffect;
//                            img = empty;
                        }
                    } else {
                        img = box;
                    }
                } else {

                    Monster m = game.getMonster(x, y);
                    Player p1 = game.getPlayer1(x, y);
                    Player p2 = game.getPlayer2(x, y);
                    Wall w = game.getWall(x, y);
                    Empty e = game.getEmpty(x, y);

                    if (m != null) {
                        switch (m.monsterType) {
                            case BASIC:
                                img = monster1;
                                break;
                            case MONSTER2:
                                img = monster2;
                                break;
                            case MONSTER3:
                                img = monster3;
                                break;
                            case MONSTER4:
                                img = monster4;
                                break;

                        }
                        //img = mo {nster1;
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

                for (Bomb bomb1 : game.getPlayer11().getBombs()) {
                    gr.drawImage(this.bomb, bomb1.getPosition().getX() * scaled_size, bomb1.getPosition().getY() * scaled_size, scaled_size, scaled_size, null);

                }
                for (Bomb bomb2 : game.getPlayer22().getBombs()) {
                    gr.drawImage(this.bomb, bomb2.getPosition().getX() * scaled_size, bomb2.getPosition().getY() * scaled_size, scaled_size, scaled_size, null);
                }

                if (game.isBombAtPosition(x, y)) {
                    Bomb bomb = game.getBomb(x, y);
                    if (bomb != null) {

                        System.out.println("if (bomb != null && bomb.getExploding()) {");
                        drawExplosion(gr, bomb, scaled_size);
                    }
                }
            }
        }
    }

    private void drawExplosion(Graphics2D gr, Bomb bomb, int scaled_size) {
        int blastRange = bomb.getOwner().getBlastRange();
        Position bombPos = bomb.getPosition();
        int x = bombPos.getX();
        int y = bombPos.getY();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            for (int i = 1; i < blastRange; i++) {
                int newX = x + dir[0] * i;
                int newY = y + dir[1] * i;
                if (game.canExplode(newX, newY)) {
                    System.out.println("IN VIEW CLASS: game.canExplode(x,y)" + newX + " " + newY);
                    gr.drawImage(explosion, newX * scaled_size, newY * scaled_size, scaled_size, scaled_size, null);
                    //gr.fillRect(newX * scaled_size, newY * scaled_size, scaled_size, scaled_size);
                } else {
                    break; // Stop drawing in this direction if explosion is blocked
                }
            }
        }

    }
}
