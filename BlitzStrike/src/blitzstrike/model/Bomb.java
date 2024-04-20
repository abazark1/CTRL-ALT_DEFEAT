/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import static blitzstrike.model.Game.MAP_SIZE;
import blitzstrike.model.monsters.Monster;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author artur
 */
public class Bomb {

    public static final int BOMB_COUNTDOWN = 3;
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
        // System.out.println(Duration.between(this.startingTime, LocalTime.now()).getSeconds() >= BOMB_COUNTDOWN);
        this.exploding = Duration.between(this.startingTime, LocalTime.now()).getSeconds() >= BOMB_COUNTDOWN;
//        System.out.println("The bomb is set to exploding");
    }

    public boolean getExploding() {
        return this.exploding;
    }
    
 /**
 * Handles the explosion of a bomb. It triggers the explosion in four directions
 * (right, left, down, up) from the bomb's position and removes the bomb after the explosion.
 *
 * @param player1  The first player in the game.
 * @param player2  The second player in the game.
 * @param monsters The list of monsters present in the game.
 */
    public void handleExplosion(Player player1, Player player2, ArrayList<Monster> monsters) {
    // Explode in each direction separately
    explodeDirection(this.position, new Position(1, 0), player1, player2, monsters);  // Right
    explodeDirection(this.position, new Position(-1, 0), player1, player2, monsters); // Left
    explodeDirection(this.position, new Position(0, 1), player1, player2, monsters);  // Down
    explodeDirection(this.position, new Position(0, -1), player1, player2, monsters); // Up
    this.owner.getBombs().remove(this);
    }
    
    
/**
 * Handles the explosion in a single direction. It checks each cell in the direction
 * of the explosion for a wall, box, or entity, affecting them as needed.
 *
 * @param bombPosition The starting position of the bomb.
 * @param direction    The direction of the explosion.
 * @param player1      The first player in the game.
 * @param player2      The second player in the game.
 * @param monsters     The list of monsters present in the game.
 */
    private void explodeDirection(Position bombPosition, Position direction, Player player1, Player player2, ArrayList<Monster> monsters) {
        for (int i = 0; i < this.owner.getBlastRange(); i++) {
            Position targetPosition = new Position(bombPosition.getX() + direction.getX() * i, bombPosition.getY() + direction.getY() * i);
            if (!checkAndProcessCell(targetPosition, player1, player2, monsters)) {
                // If false is returned, it hit a wall or the edge, so stop further explosion in this direction
                break;
            }
        }
    }

    
 /**
 * Checks and processes a single cell in the path of the explosion. Boxes get destroyed, and walls neglected. Checks for the affected entities.
 *
 * @param position The position of the cell to check.
 * @param player1  The first player in the game.
 * @param player2  The second player in the game.
 * @param monsters The list of monsters present in the game.
 * @return true if the explosion can continue past this cell, false if it is blocked.
 */
    private boolean checkAndProcessCell(Position position, Player player1, Player player2, ArrayList<Monster> monsters) {
        Cell cell = space[position.getY()][position.getX()];

        if (cell instanceof Box) {
        // Boxes get destroyed and may reveal a power-up
        ((Box) cell).getDestroyed();
        }
        if (isOutOfBounds(position)) return false;

        if (cell instanceof Wall) {
        // Walls block the explosion and are not affected
            return false;
        }

        // Check for entities at the affected position
        checkAndAffectEntitiesAt(position, player1, player2, monsters);
        return true;
    }
    
    
/**
 * Checks and affects entities at a specific position during an explosion. If a player
 * or monster is at the affected position, they will be marked as dead or exploded, respectively.
 *
 * @param affectedPos The position being affected by the explosion.
 * @param player1     The first player in the game.
 * @param player2     The second player in the game.
 * @param monsters    The list of monsters present in the game.
 */
    
    

    private void checkAndAffectEntitiesAt(Position affectedPos, Player player1, Player player2, ArrayList<Monster> monsters) {

        if (player1.getPosition().equals(affectedPos)) {
            player1.die();
            System.out.println(player1.getName() +" died from the explosion at " + affectedPos.getX() + " " + affectedPos.getY());
        }

        if (player2.getPosition().equals(affectedPos)) {
            player2.die();
            System.out.println(player2.getName()+" died from the explosion at " + affectedPos.getX() + " " + affectedPos.getY());
        }

        for (Monster monster : monsters) {
            if (monster.getPosition().equals(affectedPos)) {
                monster.die();
                System.out.println("Monster died from the explosion at " + affectedPos.getX() + " " + affectedPos.getY());
            }
        }
    }
    
    
 /**
 * Determines if a given position is out of the bounds of the game field.
 *
 * @param position The position to check.
 * @return true if the position is out of bounds, false otherwise.
 */

    private boolean isOutOfBounds(Position position) {
        // Returns true if the position is outside the game field.
        return position.getX() < 0 || position.getX() >= MAP_SIZE || position.getY() < 0 || position.getY() >= MAP_SIZE;
    }

    // getter
    public Position getPosition() {
        return this.position;
    }
    
    public Player getOwner(){
        return this.owner;
    }
}
