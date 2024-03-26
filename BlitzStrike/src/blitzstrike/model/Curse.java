/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import java.time.LocalTime;

/**
 *
 * @author aliia
 */
class Curse extends Effect{
    private int duration;
    private static Curse instance;

    
    public Curse(int duration){
        this.duration = duration;
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public void removeEffect(Player p){
        
    }
    
    public static Curse getInstance(int duration) {
        if (instance == null) {
            instance = new Curse(duration);
        }
        return instance;
    }
}

class ImmediateBombCurse extends Curse {
    
    public ImmediateBombCurse(int duration){
        super(duration);
    }
    
    @Override
    public void applyEffect(Player p){
        activateImmediateBomb(p);
    }
    
    @Override
    public void removeEffect(Player p){
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

class NoBombCurse extends Curse {
    
    public NoBombCurse(int duration){
        super(duration);
    }
    
    @Override
    public void applyEffect(Player p){
        activateNoBombCurse(p);
    }
    
    @Override
    public void removeEffect(Player p){
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

class BombRangeCurse extends Curse {
    
    public BombRangeCurse(int duration){
        super(duration);
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

class SpeedCurse extends Curse {
    
    public SpeedCurse(int duration){
        super(duration);
    }
    
    @Override
    public void applyEffect(Player p){
        activateSpeedCurse(p);
    }
    
    @Override
    public void removeEffect(Player p){
        long timePassed = LocalTime.now().toSecondOfDay() - p.getSpeedCurseTimer().toSecondOfDay();
        
        if(timePassed >= this.getDuration()){
            p.setSpeedCurse(1.0);
            p.setSpeedCurseTimer(null);
        }
    }
    
    private void activateSpeedCurse(Player p){
        p.setSpeedCurse(0.8);
        p.setSpeedCurseTimer(LocalTime.now());
    }
}
