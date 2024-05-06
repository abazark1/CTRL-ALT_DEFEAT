/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.curses;

import blitzstrike.model.effects.Effect;
import blitzstrike.model.Player;
import java.time.LocalTime;

public abstract class Curse extends Effect {

    public static final int CURSE_DURATION = 10;
    private final int duration;
    protected LocalTime startingTime;
    protected boolean isTerminated;

    /**
     * Protected constructor
     */
    protected Curse() {
        super();
        this.duration = CURSE_DURATION;
        this.isCurse = true;
        this.isTerminated = false;
    }

    /**
     * Returns duration of the curse
     *
     * @return duration value
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Returns true if curse is terminated, false, otherwise
     *
     * @return isTerminated value
     */
    public boolean isTerminated() {
        return this.isTerminated;
    }

    /**
     * Checks the time passed, sets isTerminated to true and resets the effect
     * if the time has reached the duration of the effect, false, otherwise
     *
     * @param player player to whom the effect belongs
     */
    public void terminateEffect(Player player) {
        if (this.startingTime != null) {
            long timePassed = LocalTime.now().toSecondOfDay() - this.startingTime.toSecondOfDay();
            if (timePassed >= this.getDuration()) {
                resetEffect(player);
                this.isTerminated = true;
            }
        }
    }

    /**
     * Resets the effect from the player
     *
     * @param player player
     */
    protected abstract void resetEffect(Player player);

}
