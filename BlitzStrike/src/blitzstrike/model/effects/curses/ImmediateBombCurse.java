/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.curses;

import blitzstrike.model.Player;
import java.time.LocalTime;

/**
 *
 * @author medina
 */
public class ImmediateBombCurse extends Curse {
    
    public ImmediateBombCurse() {
        super();
    }
    
    @Override
    public void applyEffect(Player p){
        activateImmediateBomb(p);
        p.addCurse(this);
    }
    
    /**
     * Activates the immediate bomb curse to the given player
     * @param p given player
     */
    private void activateImmediateBomb(Player p){
        p.setImmediateBombCurse(true);
        this.startingTime = LocalTime.now();
    }

    /**
     * Calls the reset method of the given player to reset the effect
     * @param p given player
     */
    @Override
    protected void resetEffect(Player p) {
        p.setImmediateBombCurse(false);
    }
}
