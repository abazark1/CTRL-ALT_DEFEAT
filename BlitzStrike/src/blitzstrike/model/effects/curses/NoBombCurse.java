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
public class NoBombCurse extends Curse {
    
    public NoBombCurse() {
        super();
    }
    
    @Override
    public void applyEffect(Player p) {
        activateNoBombCurse(p);
        p.addCurse(this);
    }

    /**
     * Activates the no bomb curse
     *
     * @param p to whom the no bomb curse should get applied
     */
    private void activateNoBombCurse(Player p) {
        p.setNoBombCurse(true);
        this.startingTime = LocalTime.now();
    }
    
    @Override
    protected void resetEffect(Player p) {
        p.setNoBombCurse(false);
    }
}
