/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;
import blitzstrike.model.effects.Effect;

/**
 *
 * @author medina
 */
public abstract class PowerUp extends Effect {

    protected PowerUp() {
        super();
        this.isPowerup = true;
    }

    public void removeEffect(Player player) {

    }
    
    public abstract void applyEffect(Player player);
}