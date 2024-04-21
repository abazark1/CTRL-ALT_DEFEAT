/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;
import java.time.LocalTime;

/**
 *
 * @author medina
 */

//advanced power up

public class Invincibility extends PowerUp{
    
    public Invincibility(){
        super();
    }
    
    @Override
    public void applyEffect(Player player){
        System.out.println(player.getName() + " has just got invincibility");
        makeInvincible(player);
        player.addPowerup(this);
    }
    
    @Override
    public void removeEffect(Player player){
        player.setInvincible(false);
    }
    
    private void makeInvincible(Player player){
        player.setInvincible(true);
        this.startingTime = LocalTime.now();
    }
}
