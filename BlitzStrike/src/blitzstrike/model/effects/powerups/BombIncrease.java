/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;

/**
 *
 * @author medina
 */

//basic power up

public class BombIncrease extends PowerUp{
    
    @Override
    public void applyEffect(Player player){
        System.out.println(player.getName() + " has just got bomb increase!");
        increaseBombs(player);
    }
    
    @Override 
    public void removeEffect(Player player) {}
    
    private void increaseBombs(Player player){
        int bombs =player.getBombNumber();
        bombs++;
        player.setBombNumber(bombs);
    }
}

