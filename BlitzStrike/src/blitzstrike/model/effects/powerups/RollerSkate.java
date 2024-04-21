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
public class RollerSkate extends PowerUp {
    

    public RollerSkate() {
        super();
    }

    @Override
    public void applyEffect(Player player) {
        System.out.println(player.getName() + " has just got roler skate!");
        activateRollerSkate(player);
        player.addPowerup(this);
    }

    private void activateRollerSkate(Player player) {
        player.setRollerSkate(true);
        this.startingTime = LocalTime.now();
    }
    
    @Override
    public void removeEffect(Player player){
        player.setRollerSkate(false);
    }
}
