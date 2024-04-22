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
public class Ghost extends PowerUp {

    private int duration;

    public Ghost() {
        super();
        //this.duration = 5;
    }

    @Override
    public void applyEffect(Player player) {
        System.out.println(player.getName() + " has just got Ghost powerup");
        makeGhost(player);
        player.addPowerup(this);
    }

    @Override
    public void removeEffect(Player player) {
        player.setGhost(false);

    }

    private void makeGhost(Player player) {
        player.setGhost(true);
        this.startingTime = LocalTime.now();
        
    }
}
