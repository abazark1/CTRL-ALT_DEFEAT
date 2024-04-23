/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.curses;

import blitzstrike.model.Player;
import java.time.LocalTime;

public class BombRangeCurse extends Curse {

    /**
     * Public constructor
     */
    public BombRangeCurse() {
        super();
    }

    /**
     * Applies the bomb range curse effect to the given player
     *
     * @param player is the given player to apply the bomb range curse to
     */
    @Override
    public void applyEffect(Player player) {
        activateBombRangeCurse(player);
        player.addCurse(this);
    }

    /**
     * Resets bomb range to the standard value for the given player
     *
     * @param player is the given player to reset the effect
     */
    @Override
    protected void resetEffect(Player player) {
        player.resetBombRange();
    }

    /**
     * Activates the bomb range curse
     *
     * @param player to whom the bomb range curse should get applied
     */
    private void activateBombRangeCurse(Player player) {
        System.out.println("Activated bomb range curse");
        player.setBombRangeCurse(1);
        this.startingTime = LocalTime.now();
    }
}
