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
     * @return
     */
    public boolean isTerminated(){
        return this.isTerminated;
    }

    public abstract void terminateEffect(Player p);
    
    public abstract void applyEffect(Player p);

}
