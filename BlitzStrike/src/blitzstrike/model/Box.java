/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import blitzstrike.Effect;

/**
 *
 * @author artur
 */
public class Box extends Cell {
    
    private boolean destroyed;
    private Effect effect;
    
    public Box(Position position){
        super(position);
        this.destroyable = true;
        this.mineable = false;
        this.walkable = false;
        this.destroyed = false;
    }
    
    public void getDestroyed(){
        this.destroyed = true;
        System.out.println("I'm destroyed");
    }
}
