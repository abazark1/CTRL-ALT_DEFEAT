/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import static blitzstrike.model.Game.MAP_SIZE;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author artur
 */
public class Bomb {

    private LocalTime startingTime;
    private Position position;
    private Player owner;
    private Cell[][] space;

    public Bomb(Position position, Player owner, Cell[][] space) {
        this.startingTime = LocalTime.now();
        this.position = position;
        this.owner = owner;
        this.space = space;
    }

    public void handleExplosion(Position bombPosition, int blastRadius, Player player1, Player player2, ArrayList<Monster> monsters) {
        for (int dx = -blastRadius; dx <= blastRadius; dx++) {
            for (int dy = -blastRadius; dy <= blastRadius; dy++) {
                Position affectedPos = new Position(bombPosition.getX() + dx, bombPosition.getY() + dy);

                if (isOutOfBounds(affectedPos)) {
                    continue;
                }

                Cell cell = space[affectedPos.getY()][affectedPos.getX()];
                if (cell instanceof Wall) {
                    continue; //block if its wall
                }
                if (cell instanceof Box) {
                    // Destroy the box and potentially reveal a power-up
                    ((Box) cell).getDestroyed();
                    continue;
                }
                // Check for players or monsters at the affected position
                checkAndAffectEntitiesAt(affectedPos, player1, player2, monsters);
            }
        }
    }

    private void checkAndAffectEntitiesAt(Position affectedPos, Player player1, Player player2, ArrayList<Monster> monsters) {

        if (player1.getPosition().equals(affectedPos)) {
            player1.getExploded();
            //System.out.println("Player 1 hit by explosion at " + affectedPos);
        }

        if (player2 != null && player2.getPosition().equals(affectedPos)) {
            player2.getExploded();
            //System.out.println("Player 2 hit by explosion at " + affectedPos);
        }
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(affectedPos)) {
                monster.getExploded();
                //System.out.println("Monster hit by explosion at " + affectedPos);
            }
        }
    }

    private boolean isOutOfBounds(Position position) {
        // Returns true if the position is outside the game field.
        return position.getX() < 0 || position.getX() >= MAP_SIZE || position.getY() < 0 || position.getY() >= MAP_SIZE;
    }

}
