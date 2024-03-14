/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author artur
 */
public class Box extends Cell {
    public Box(Position position){
        super(position);
        this.destroyable = true;
        this.mineable = false;
        this.walkable = false;
    }
}
