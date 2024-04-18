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
        this.duration = 3;
    }
    
    @Override
    public void applyEffect(Player p){
        activateNoBombCurse(p);
    }
    
    @Override
    public void terminateEffect(Player p){
        long timePassed = LocalTime.now().toSecondOfDay() - p.getNoBombCurseTimer().toSecondOfDay();
        
        if(timePassed >= this.getDuration()){
            p.setNoBombCurse(false);
            p.setNoBombCurseTimer(null);
        }
    }
    
    private void activateNoBombCurse(Player p){
        p.setNoBombCurse(true);
        p.setNoBombCurseTimer(LocalTime.now());
    }
}
