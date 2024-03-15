/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike;

/**
 *
 * @author aliia
 */
class Curse extends Effect{
    private int duration;
    
    public Curse(int duration){
        this.duration = duration;
    }
    
    public void applyEffect(Player p){
        
    }
    
    public void removeEffect(Player p){
        
    }
}

class ImmediateBombCurse extends Curse {
    
    public ImmediateBombCurse(int duration){
        super(duration);
    }
    
    @Override
    public void applyEffect(Player p){
        
    }
    
    @Override
    public void removeEffect(Player p){
        
    }
    
    private void activateImmediateBomb(Player p){
        
    }
}

class NoBombCurse extends Curse {
    
    public NoBombCurse(int duration){
        super(duration);
    }
    
    @Override
    public void applyEffect(Player p){
        
    }
    
    @Override
    public void removeEffect(Player p){
        
    }
    
    private void activateNoBombCurse(Player p){
        
    }
}

class BombRangeCurse extends Curse {
    
    public BombRangeCurse(int duration){
        super(duration);
    }
    
    @Override
    public void applyEffect(Player p){
        
    }
    
    @Override
    public void removeEffect(Player p){
        
    }
    
    private void activateBombRangeCurse(Player p){
        
    }
}

class SpeedCurse extends Curse {
    
    public SpeedCurse(int duration){
        super(duration);
    }
    
    @Override
    public void applyEffect(Player p){
        
    }
    
    @Override
    public void removeEffect(Player p){
        
    }
    
    private void activateSpeedCurse(Player p){
        
    }
}
