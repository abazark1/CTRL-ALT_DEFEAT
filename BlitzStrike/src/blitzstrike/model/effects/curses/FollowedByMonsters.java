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
     * @param player is the given player to apply the speed curse to
     */
    @Override
    public void applyEffect(Player player) {
        activateFollowedByMonsters(player);
        player.addCurse(this);
    }

    /**
     * Activates the speed (followed by monsters) curse
     *
     * @param player to whom the speed curse should get applied
     */
    private void activateFollowedByMonsters(Player player) {
        player.setFollowedByMonsters(true);
        this.startingTime = LocalTime.now();
    }

    /**
     * Resets followedByMonsters to the standard value for the given player
     *
     * @param player is the given player to reset the effect
     */
    @Override
    protected void resetEffect(Player player) {
        player.setFollowedByMonsters(false);
    }
    
    @Override
    public String toString(){
        return "Followed by monsters Curse";
    }
}
