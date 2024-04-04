/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import static blitzstrike.model.Game.MAP_SIZE;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author artur
 */
public class Bomb {

    public int BOMB_COUNTDOWN = 3;
    private LocalTime startingTime;
    private Position position;
    private Player owner;
    private Cell[][] space;
    private boolean exploding;

    public Bomb(Position position, Player owner, Cell[][] space) {
        this.startingTime = LocalTime.now();
        this.position = position;
        this.owner = owner;
        this.space = space;
        this.exploding = false;
    }

    /**
     * It sets true if the time to explode has been reached, false, otherwise
     *
     */
    public void setExploding() {
        System.out.println(Duration.between(this.startingTime, LocalTime.now()).getSeconds() >= BOMB_COUNTDOWN);
        this.exploding = Duration.between(this.startingTime, LocalTime.now()).getSeconds() >= BOMB_COUNTDOWN;
        System.out.println("The bomb is set to exploding");
    }

    public boolean getExploding() {
        return this.exploding;
    }

    public void handleExplosion(Player player1, Player player2, ArrayList<Monster> monsters) {
        for (int dx = -this.owner.getBlastRange(); dx <= this.owner.getBlastRange(); dx++) {
            for (int dy = -this.owner.getBlastRange(); dy <= this.owner.getBlastRange(); dy++) {
                // Skip the center (no explosion at the bomb's location)
                if (dx == 0 && dy == 0) {
                    continue;
                }
                Position affectedPos = new Position(this.position.getX() + dx, this.position.getY() + dy);

                if (isOutOfBounds(affectedPos)) {
                    continue;
                }

                Cell cell = space[affectedPos.getY()][affectedPos.getX()];
                if (cell instanceof Wall) {
                    continue; //block if its wall
                }
                if (cell instanceof Box) {
//                    if (((Box) cell).isDestroyed()) {
                    // Destroy the box and potentially reveal a power-up
                    ((Box) cell).getDestroyed();
                    continue;
//                    }
                }
                // Check for players or monsters at the affected position
                checkAndAffectEntitiesAt(affectedPos, player1, player2, monsters);
                this.owner.getBombs().remove(this);
            }
        }
    }

    private void checkAndAffectEntitiesAt(Position affectedPos, Player player1, Player player2, ArrayList<Monster> monsters) {

        if (player1.getPosition().equals(affectedPos)) {
            player1.die();
            System.out.println("Player 1 died from the explosion at " + affectedPos.getX() + " " + affectedPos.getY());
        }

        if (player2.getPosition().equals(affectedPos)) {
            player2.die();
            System.out.println("Player 2 died from the explosion at " + affectedPos.getX() + " " + affectedPos.getY());
        }

        for (Monster monster : monsters) {
            if (monster.getPosition().equals(affectedPos)) {
                monster.getExploded();
                System.out.println("Monster died from the explosion at " + affectedPos.getX() + " " + affectedPos.getY());
            }
        }
    }

    private boolean isOutOfBounds(Position position) {
        // Returns true if the position is outside the game field.
        return position.getX() < 0 || position.getX() >= MAP_SIZE || position.getY() < 0 || position.getY() >= MAP_SIZE;
    }

    // getter
    public Position getPosition() {
        return this.position;
    }

}
