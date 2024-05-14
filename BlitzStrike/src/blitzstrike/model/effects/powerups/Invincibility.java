/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;
import java.time.LocalTime;

public class Invincibility extends PowerUp {

    public Invincibility(boolean hasDuration){
        super(hasDuration);
    }
    
    /**
     * Applies the invincibility power up to the given player
     *
     * @param player is the given player
     */
    @Override
    public void applyEffect(Player player) {
        System.out.println(player.getName() + " has just got invincibility");
        makeInvincible(player);
        player.addPowerup(this);
    }

    /**
     * Removes invincibility from the given player
     *
     * @param player is the given player
     */
    @Override
    public void removeEffect(Player player) {
        player.setInvincible(false);
    }

    /**
     * Makes a given player invincible
     *
     * @param player is the given player
     */
    private void makeInvincible(Player player) {
        player.setInvincible(true);
        this.startingTime = LocalTime.now();
    }
    
    @Override
    public String toString(){
        return "Invincibility PowerUp";
    }
}
