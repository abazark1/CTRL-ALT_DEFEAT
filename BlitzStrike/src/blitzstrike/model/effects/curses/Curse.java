/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.curses;

import blitzstrike.model.effects.Effect;
import blitzstrike.model.Player;
import java.time.LocalTime;

/**
 *
 * @author aliia
 */
public abstract class Curse extends Effect {

    public static final int CURSE_DURATION = 10;
    protected int duration;
    protected LocalTime startingTime;
    protected boolean isTerminated;

    public Curse() {
        super();
        this.duration = CURSE_DURATION;
        this.isCurse = true;
        this.isTerminated = false;
    }

    public int getDuration() {
        return this.duration;
    }

    /**
     * Returns true if curse is terminated, false, otherwise
     *
     * @return
     */
    public boolean isTerminated() {
        return this.isTerminated;
    }

    /**
     * Checks the time passed and sets isTerminated to true if the time has
     * reached the duration of the effect, false, otherwise
     *
     * @param p player to whom the effect belongs
     */
    public void terminateEffect(Player p) {
        if (this.startingTime != null) {
            long timePassed = LocalTime.now().toSecondOfDay() - this.startingTime.toSecondOfDay();
            if (timePassed >= this.getDuration()) {
                resetEffect(p);
                this.isTerminated = true;
            }
        }
    }

    /**
     * Resets the effect from the player
     * @param p player
     */
    protected abstract void resetEffect(Player p);

    public abstract void applyEffect(Player p);

}
