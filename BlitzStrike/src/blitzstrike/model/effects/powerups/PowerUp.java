/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;
import blitzstrike.model.effects.Effect;
import java.time.LocalTime;

/**
 *
 * @author medina
 */
public abstract class PowerUp extends Effect {
    private boolean isTerminated;
    protected LocalTime startingTime;
    private static final int invisibilityDuration = 10;

    protected PowerUp() {
        super();
        this.isPowerup = true;
        this.isTerminated = false;
    }

    public boolean isTerminated(){
        return this.isTerminated;
    }
    
    public abstract void removeEffect(Player player);
    
    @Override
    public abstract void applyEffect(Player player);
    
    
    public void terminateEffect(Player player) {
        if (this.startingTime != null) {
            long timePassed = LocalTime.now().toSecondOfDay() - this.startingTime.toSecondOfDay();
            if (timePassed >= invisibilityDuration) {
                removeEffect(player);
                this.isTerminated = true;
            }
        }
    }
}
