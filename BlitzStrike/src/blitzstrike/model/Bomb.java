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
        for (int d = -this.owner.getBlastRange(); d <= this.owner.getBlastRange(); d++) {
                // Skip the center (no explosion at the bomb's location)
                // Check horizontal positions (left and right of the bomb)
                checkAndProcessCell(new Position(this.position.getX() + d, this.position.getY()), player1, player2, monsters);
                // Check vertical positions (above and below the bomb)
                checkAndProcessCell(new Position(this.position.getX(), this.position.getY() + d), player1, player2, monsters);
                        
        }
         this.owner.getBombs().remove(this);
    }
    
    private void checkAndProcessCell(Position position, Player player1, Player player2, ArrayList<Monster> monsters) {
    if (isOutOfBounds(position)) return;

    Cell cell = space[position.getY()][position.getX()];
    if (cell instanceof Wall) {
        // Walls block the explosion and are not affected
        return;
    }
    if (cell instanceof Box) {
        // Boxes get destroyed and may reveal a power-up
        ((Box) cell).getDestroyed();
    }

    // Check for entities at the affected position
    checkAndAffectEntitiesAt(position, player1, player2, monsters);
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
