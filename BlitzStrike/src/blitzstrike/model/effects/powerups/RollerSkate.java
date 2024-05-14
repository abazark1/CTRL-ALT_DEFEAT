/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;
import java.time.LocalTime;

public class RollerSkate extends PowerUp {

    public RollerSkate(boolean hasDuration){
        super(hasDuration);
    }
    
    /**
     * Applies the rollerskate power up to the given player
     *
     * @param player is the given player
     */
    @Override
    public void applyEffect(Player player) {
        System.out.println(player.getName() + " has just got roler skate!");
        activateRollerSkate(player);
        player.addPowerup(this);
    }

    /**
     * Removes the roller skate effect from the given player
     *
     * @param player is the given player
     */
    @Override
    public void removeEffect(Player player) {
        player.setRollerSkate(false);
    }

    /**
     * Activates roller skate effect to the given player
     *
     * @param player is the given player
     */
    private void activateRollerSkate(Player player) {
        player.setRollerSkate(true);
        this.startingTime = LocalTime.now();
    }
    
    @Override
    public String toString(){
        return "Ignored by Monsters PowerUp";
    }
}
