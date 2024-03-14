/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author artur
 */
public class Wall extends Cell {
    public Wall(Position position){
        super(position);
        this.walkable = false;
        this.destroyable = false;
        this.mineable = false;
    }
    
}
