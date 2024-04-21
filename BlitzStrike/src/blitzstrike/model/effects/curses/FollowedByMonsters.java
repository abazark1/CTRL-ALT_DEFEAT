/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.curses;

import blitzstrike.model.Player;
import java.time.LocalTime;

public class FollowedByMonsters extends Curse {
    
    /**
     * Public constructor
     */
    public FollowedByMonsters() {
        super();
    }
    
    /**
     * Applies the speed curse effect to the given player
     *
     * @param p is the given player to apply the speed curse to
     */
    @Override
    public void applyEffect(Player p) {
        activateFollowedByMonsters(p);
        p.addCurse(this);
    }
    
    /**
     * Activates the speed (followed by monsters) curse
     *
     * @param p to whom the speed curse should get applied
     */
    private void activateFollowedByMonsters(Player p) {
        p.setFollowedByMonsters(true);
        this.startingTime = LocalTime.now();
    }
    
    /**
     * Resets followedByMonsters to the standard value for the given player
     *
     * @param p is the given player to reset the effect
     */
    @Override
    protected void resetEffect(Player p) {
        p.setFollowedByMonsters(false);
    }
}
