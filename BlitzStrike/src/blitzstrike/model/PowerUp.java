/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author medina
 */
class PowerUp extends Effect{
    
    public void removeEffect(Player player){
        
    }
}

class RollerSkate extends PowerUp{
    private double bonusToSpeed;
    
    public RollerSkate(double bonusToSpeed){
        this.bonusToSpeed= bonusToSpeed;
    }
    
    @Override
    public void applyEffect(Player player){
        
    }
    
    private void increaseSpeed(Player player){
        
    }
}

class BombIncrease extends PowerUp{
    
    @Override
    public void applyEffect(Player player){
        
    }
    
    private void increaseBombs(Player player){
        
    }
}

class Invincibility extends PowerUp{
    private int duration;
    
    public Invincibility(int duration){
        this.duration= duration;
    }
    
    @Override
    public void applyEffect(Player player){
        
    }
    
    @Override
    public void removeEffect(Player player){
        
    }
    
    private void makeInvincible(Player player){
        
    }
}
  
class BlastRange extends PowerUp{
    
    @Override
    public void applyEffect(Player player){
        
    }
    
    @Override
    public void removeEffect(Player player){
        
    }
    
    private void increaseRange(Player player){
        
    }
}

class Ghost extends PowerUp{
    private int duration;
    
    public Ghost(int duration){
        this.duration= duration;
    }
    
    @Override
    public void applyEffect(Player player){
        
    }
    
    @Override
    public void removeEffect(Player player){
        
    }
    
    private void makeGhost(Player player){
        
    }
}

class Detonator extends PowerUp{
    
    @Override
    public void applyEffect(Player player){
        
    }
    
    @Override
    public void removeEffect(Player player){
        
    }
    
    private void activateDetonator(Player player){
        
    }
}

class Obstacle extends PowerUp{
    private int numberOfObstacles;
    
    public Obstacle(int numberOfObstacles){
        this.numberOfObstacles= numberOfObstacles;
    }
    
    @Override
    public void applyEffect(Player player){
        
    }
    
    @Override
    public void removeEffect(Player player){
        
    }
    
    private void increaseObstacles(Player player){
        
    }
}
