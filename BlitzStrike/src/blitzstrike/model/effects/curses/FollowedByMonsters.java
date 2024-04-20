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
public class FollowedByMonsters extends Curse {
    
    public FollowedByMonsters() {
        super();
    }
    
    @Override
    public void applyEffect(Player p) {
        activateFollowedByMonsters(p);
        p.addCurse(this);
    }
    
    private void activateFollowedByMonsters(Player p) {
        p.setFollowedByMonsters(true);
        this.startingTime = LocalTime.now();
    }
    
    @Override
    protected void resetEffect(Player p) {
        p.setFollowedByMonsters(false);
    }
}
