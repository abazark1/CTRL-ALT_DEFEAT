/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import java.time.LocalTime;

/**
 *
 * @author medina
 */
public class BombRangeCurse extends Curse {
    
    private BombRangeCurse() {
        super();
        this.duration = 3;
    }
    
    @Override
    public void applyEffect(Player p){
        activateBombRangeCurse(p);
    }
    
    @Override
    public void removeEffect(Player p){
        long timePassed = LocalTime.now().toSecondOfDay() - p.getBombRangeCurseTimer().toSecondOfDay();
        
        if(timePassed >= this.getDuration()){
            p.setBombRangeCurse(3);
            p.setBombRangeCurseTimer(null);
        }
    }
    
    private void activateBombRangeCurse(Player p){
        p.setBombRangeCurse(1);
        p.setBombRangeCurseTimer(LocalTime.now());
    }
}
