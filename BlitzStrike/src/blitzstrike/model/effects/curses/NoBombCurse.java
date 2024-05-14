/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.curses;

import blitzstrike.model.Player;
import java.time.LocalTime;

public class NoBombCurse extends Curse {

    /**
     * Public constructor
     */
    public NoBombCurse() {
        super();
    }

    /**
     * Applies no bomb curse to the given player
     *
     * @param player is the given player
     */
    @Override
    public void applyEffect(Player player) {
        activateNoBombCurse(player);
        player.addCurse(this);
    }

    /**
     * Resets effect from no bomb curse for the given player
     *
     * @param player is the given player
     */
    @Override
    protected void resetEffect(Player player) {
        player.setNoBombCurse(false);
    }

    /**
     * Activates the no bomb curse
     *
     * @param player to whom the no bomb curse should get applied
     */
    private void activateNoBombCurse(Player player) {
        player.setNoBombCurse(true);
        this.startingTime = LocalTime.now();
    }
    
    @Override
    public String toString(){
        return "No Bomb Curse";
    }
}
