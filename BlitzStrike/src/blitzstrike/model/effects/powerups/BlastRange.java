/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;

public class BlastRange extends PowerUp {

    public BlastRange(boolean hasDuration){
        super(hasDuration);
    }
    /**
     * Applies the blast range power up to the given player
     *
     * @param player is the given player
     */
    @Override
    public void applyEffect(Player player) {
        System.out.println(player.getName() + " has just got blast range!");
        increaseRange(player);
        player.addPowerup(this);
    }

    /**
     * Overrides the abstract method, but since the blast range doesn't get
     * removed, the method doesn't do anything
     *
     * @param player is the given player
     */
    @Override
    public void removeEffect(Player player) {
    }

    /**
     * Increases blast range to the given player
     *
     * @param player is the given player
     */
    private void increaseRange(Player player) {
        int range = player.getBlastRange();
        range++;
        player.setBlastRange(range);
    }
    
    @Override
    public String toString(){
        return "Blast Range PowerUp";
    }
}
