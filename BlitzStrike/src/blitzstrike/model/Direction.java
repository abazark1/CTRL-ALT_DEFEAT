/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author artur
 */
public enum Direction {
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0), STILL(0, 0);
    
    /**
     * Initializes Direction
     * @param x
     * @param y 
     */
    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public final int x, y;
}
