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
        this.duration = 3;
    }
    
    @Override
    public void applyEffect(Player p){
        activateImmediateBomb(p);
    }
    
    @Override
    public void terminateEffect(Player p){
        long timePassed = LocalTime.now().toSecondOfDay() - p.getImmediateBombCurseTimer().toSecondOfDay();
        
        if(timePassed >= this.getDuration()){
            p.setImmediateBombCurse(false);
            p.setImmediateBombCurseTimer(null);
        }
    }
    
    private void activateImmediateBomb(Player p){
        p.setImmediateBombCurse(true);
        p.setImmediateBombCurseTimer(LocalTime.now());
    }
}
