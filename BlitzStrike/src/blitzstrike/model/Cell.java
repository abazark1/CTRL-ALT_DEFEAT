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
