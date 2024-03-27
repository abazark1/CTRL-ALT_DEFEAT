/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author medina
 */

//advanced power up

public class Obstacle extends PowerUp{
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
