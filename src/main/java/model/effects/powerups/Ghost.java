/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;
import java.time.LocalTime;

public class Ghost extends PowerUp {

    /**
     * Applies the ghost power up to the given player
     *
     * @param player is the given player
     */
    @Override
    public void applyEffect(Player player) {
        System.out.println(player.getName() + " has just got Ghost powerup");
        makeGhost(player);
        player.addPowerup(this);
    }

    /**
     * Removes the ghost power up from the given player
     *
     * @param player is the given player
     */
    @Override
    public void removeEffect(Player player) {
        player.setGhost(false);

    }

    /**
     * Turns a given player into a ghost and sets the starting time
     *
     * @param player is the given player
     */
    private void makeGhost(Player player) {
        player.setGhost(true);
        this.startingTime = LocalTime.now();

    }
}
