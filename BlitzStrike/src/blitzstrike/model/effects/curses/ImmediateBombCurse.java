/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.curses;

import blitzstrike.model.Player;
import java.time.LocalTime;

public class ImmediateBombCurse extends Curse {

    /**
     * Public constructor
     */
    public ImmediateBombCurse() {
        super();
    }

    /**
     * Applies immediate bomb curse to the given player
     *
     * @param player is the given player
     */
    @Override
    public void applyEffect(Player player) {
        activateImmediateBomb(player);
        player.addCurse(this);
    }

    /**
     * Resets the effect from the immediate bomb curse to the given player
     *
     * @param player given player
     */
    @Override
    protected void resetEffect(Player player) {
        player.setImmediateBombCurse(false);
    }

    /**
     * Activates the immediate bomb curse to the given player
     *
     * @param player is the given player
     */
    private void activateImmediateBomb(Player player) {
        player.setImmediateBombCurse(true);
        this.startingTime = LocalTime.now();
    }
    @Override
    public String toString(){
        return "Immediate Bomb Curse";
    }
}
