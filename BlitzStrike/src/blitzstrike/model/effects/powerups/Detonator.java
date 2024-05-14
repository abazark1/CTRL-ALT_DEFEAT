/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;

public class Detonator extends PowerUp {

    public Detonator(boolean hasDuration){
        super(hasDuration);
    }
    
    /**
     * Applies the detonator power up to the given player
     *
     * @param player is the given player
     */
    @Override
    public void applyEffect(Player player) {
        activateDetonator(player);
        player.addPowerup(this);
    }

    /**
     * Removes the detonator power up from the given player
     *
     * @param player is the given player
     */
    @Override
    public void removeEffect(Player player) {

    }

    /**
     * Activates the detonator to the given player
     *
     * @param player is the given player
     */
    private void activateDetonator(Player player) {
        System.out.println("Detonator was activated");
        player.setIsDetonatorOn(true); 
    }
    
    @Override
    public String toString(){
        return "Detonator PowerUp";
    }
}
