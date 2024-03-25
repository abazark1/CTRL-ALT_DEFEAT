/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author artur
 */
public class Cell {
    protected boolean destroyable;
    protected boolean mineable;
    protected boolean enable;
    protected boolean walkable;
    protected Position position;
    
    public Cell(Position position){
        this.destroyable = false;
        this.mineable = true;
        this.enable = true;
        this.walkable = true;
        this.position = position;
    }
    
    public boolean isWalkable(){
        return walkable;
    }
}

class Box extends Cell {
    
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
        this.walkable = true;
        this.destroyable = false;
        this.mineable = true;
        this.effect = Effect.getRandomEffect();
        System.out.println("I'm destroyed");
    }
    
    public Effect getEffect() {
        return this.effect;
    }
}

class Empty extends Cell {
    public Empty(Position position){
        super(position);
    }
}

class Wall extends Cell {
    public Wall(Position position){
        super(position);
        this.walkable = false;
        this.destroyable = false;
        this.mineable = false;
    }
}
