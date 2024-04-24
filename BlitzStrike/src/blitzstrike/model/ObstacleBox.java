/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

public class ObstacleBox extends Cell {
    
    
    public ObstacleBox(Position position){
        super(position);
        this.destroyable = true;
        this.mineable = false;
        this.walkable = false;
    }
    
    /**
     * Method to reset the fields
     * if the obstacleBox is destroyed
     */
    public void getDestroyed(){
        this.destroyed = true;
        this.walkable = true;
        this.destroyable = false;
        this.mineable = true;
        System.out.println("I'm destroyed");
    }
    
    /**
     * Returns true if the obstacle is destroyed, otherwise false
     * @return destroyed (boolean)
     */
    @Override
    public boolean isDestroyed(){
        return this.destroyed;
    }
    
    /**
     * Returns true if the obstacle is destroyable, otherwise false
     * @return destroyable (boolean)
     */
    public boolean isDestoyable(){
        return this.destroyable;
    }
    
    /**
     * Returns the position of an obstacle
     * @return position value
     */
    public Position getPosition() {
        return this.position;
    }
}

