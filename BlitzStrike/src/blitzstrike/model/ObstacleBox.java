/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

public class ObstacleBox extends Cell {

    private Player owner; 
    
    public ObstacleBox(Position position, Player owner) {
        super(position);
        this.destroyable = true;
        this.mineable = false;
        this.walkable = false;
        this.owner = owner;
    }
    
    public Player getOwner(){
        return this.owner;
    }

    /**
     * Method to reset the fields if the obstacleBox is destroyed
     */
    public void getDestroyed() {
        this.destroyed = true;
        this.walkable = true;
        this.destroyable = false;
        this.mineable = true;
        this.owner.removeObstacle(this);
        System.out.println("I'm destroyed");
    }

    /**
     * Returns true if the obstacle is destroyed, otherwise false
     *
     * @return destroyed (boolean)
     */
    public boolean isDestroyed() {
        return this.destroyed;
    }

    /**
     * Returns true if the obstacle is destroyable, otherwise false
     *
     * @return destroyable (boolean)
     */
    public boolean isDestoyable() {
        return this.destroyable;
    }

    /**
     * Returns the position of an obstacle
     *
     * @return position value
     */
    public Position getPosition() {
        return this.position;
    }
}
