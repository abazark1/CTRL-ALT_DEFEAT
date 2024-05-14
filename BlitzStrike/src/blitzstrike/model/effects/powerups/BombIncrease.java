/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;

public class BombIncrease extends PowerUp {

    public BombIncrease(boolean hasDuration){
        super(hasDuration);
    }
    /**
     * Applies the bomb increase power up to the given player
     *
     * @param player is the given player
     */
    @Override
    public void applyEffect(Player player) {
        System.out.println(player.getName() + " has just got bomb increase!");
        increaseBombs(player);
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
     * Increases the max number of bombs for the given player
     *
     * @param player is the given player
     */
    private void increaseBombs(Player player) {
        int bombs = player.getBombNumber();
        bombs++;
        player.setBombNumber(bombs);
    }
    
    @Override
    public String toString(){
        return "Bomb Increase PowerUp";
    }
}
