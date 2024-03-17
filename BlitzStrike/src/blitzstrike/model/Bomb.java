/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import java.time.LocalTime;

/**
 *
 * @author artur
 */
public class Bomb {
    private LocalTime startingTime;
    private Position position;
//     private Player owner;
    
    public Bomb(Position position){
        this.startingTime = LocalTime.now();
        this.position = position;
    }
    
    public void explode(){
        System.out.println("I exploded");
    }
    
    public void handleExplosion(){
        System.out.println("I am handling explosion");
    }
    
}
