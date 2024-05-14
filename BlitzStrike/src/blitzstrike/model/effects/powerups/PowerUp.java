/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;
import blitzstrike.model.effects.Effect;
import java.time.LocalTime;

public abstract class PowerUp extends Effect {

    public int currentPowerupTime;
    public static final int POWER_UP_DURATION = 10;
    private boolean isTerminated;
    protected boolean hasDuration;
    protected int duration;
    protected LocalTime startingTime;

    /**
     * Protected constructor
     */
    protected PowerUp(boolean hasDuration) {
        super();
        this.isPowerup = true;
        this.isTerminated = false;
        this.currentPowerupTime = POWER_UP_DURATION;
        this.hasDuration = hasDuration;
        this.duration = POWER_UP_DURATION;
    }
    

    /**
     * Returns true if power up is terminated, otherwise, false
     *
     * @return isTerminated value
     */
    public boolean isTerminated() {
        return this.isTerminated;
    }

    /**
     * Removes the effect from the given player
     *
     * @param player is the given player
     */
    public abstract void removeEffect(Player player);

    /**
     * Applies the effect to the given player
     *
     * @param player is the given player
     */
    @Override
    public abstract void applyEffect(Player player);

    /**
     * Terminates effects from the given player
     *
     * @param player is the given player
     */
    public void terminateEffect(Player player) {
        if (this.startingTime != null) {
            long timePassed = LocalTime.now().toSecondOfDay() - this.startingTime.toSecondOfDay();
            if (timePassed >= POWER_UP_DURATION) {
                removeEffect(player);
                this.isTerminated = true;
            }
        }
    }
    
    public int getCurrentPowerupTime(){
        if (this.hasDuration && this.startingTime != null) {
            long timePassed = LocalTime.now().toSecondOfDay() - this.startingTime.toSecondOfDay();
            if (timePassed < this.getDuration()) {
                currentPowerupTime = this.getDuration() - (int)timePassed;
            } else {
                currentPowerupTime = 0;
            }
        }
        return currentPowerupTime;
    }
    
    public int getDuration(){
        return this.duration;
    }
    
    public boolean hasDuration(){
        return this.hasDuration;
    }
    
    @Override
    public abstract String toString();
}
