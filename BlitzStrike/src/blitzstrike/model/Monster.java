/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author aselh
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monster {

    protected double speed;
    protected boolean ableToGoThroughWalls;
    protected boolean alive;
    protected Position position;
    protected Cell[][] space;

    public Monster(double speed, Position position, Cell[][] space) {
        this.speed = speed;
        this.ableToGoThroughWalls = false;
        this.alive = true;
        this.position = position;
        this.space = space;
    }

    public void move() {

    }

    public void getExploded() {

    }

    public void attack(Player p) {

    }

//    public Position isPlayerClose(Position p) {
//        return;
//    }

    public boolean isValidPosition(Position p) {
        return this.space[p.getY()][p.getY()].isWalkable();
    }
    
    public Position getPosition() {
        return this.position;
    }
    
    //public abstract Monster getType();
}

class BasicMonster extends Monster {

    private int counterUntilChangeDirection;
    private Direction currentDirection;
    private final int LIMIT_TO_CHANGE_DIRECTION_RANDOMLY = 3;

    public BasicMonster(double speed, Position position, Cell[][] space) {
        super(speed, position, space);
        this.counterUntilChangeDirection = 0;
        // we initialize its currentDirection to any available direction first
        settleCurrentDirection();
    }

    // we call this move() constantly, it checks if it can move with the currentPosition. 
    //if it can, it moves. if it cannot, it changes the currentPosition and moves there.
    // also every LIMIT_TO_CHANGE_DIRECTION_RANDOMLY, when it faces the wall, it changes the direction randomly
    @Override
    public void move() {
        Position newPosition = this.position.translate(currentDirection);
        if (isValidPosition(newPosition)) {
            this.position = newPosition;
        } else {
            this.counterUntilChangeDirection++;
            if (this.counterUntilChangeDirection >= LIMIT_TO_CHANGE_DIRECTION_RANDOMLY) {
                settleCurrentDirectionRandomly();
                moveWithNewDirection();
                this.counterUntilChangeDirection = 0;
            } else {
                settleCurrentDirection();
                moveWithNewDirection();
            }
        }
    }

    private void moveWithNewDirection() {
        Position newPositionWithNewDirection = this.position.translate(currentDirection);
        this.position = newPositionWithNewDirection;
    }

    private void settleCurrentDirection() {
        Position newPosUp = position.translate(Direction.UP);
        Position newPosDown = position.translate(Direction.DOWN);
        Position newPosLeft = position.translate(Direction.LEFT);
        Position newPosRight = position.translate(Direction.RIGHT);
        if (isValidPosition(newPosUp)) {
            this.currentDirection = Direction.UP;
        } else if (isValidPosition(newPosDown)) {
            this.currentDirection = Direction.DOWN;
        } else if (isValidPosition(newPosLeft)) {
            this.currentDirection = Direction.LEFT;
        } else if (isValidPosition(newPosRight)) {
            this.currentDirection = Direction.RIGHT;
        } else {
            this.currentDirection = Direction.STILL;
        }
    }

    private void settleCurrentDirectionRandomly() {
        Position newPosUp = position.translate(Direction.UP);
        Position newPosDown = position.translate(Direction.DOWN);
        Position newPosLeft = position.translate(Direction.LEFT);
        Position newPosRight = position.translate(Direction.RIGHT);
        int numberOfPossibleDirections = 1;
        List<Direction> possibleDirections = new ArrayList<>();
        possibleDirections.add(Direction.STILL);
        if (isValidPosition(newPosUp)) {
            numberOfPossibleDirections++;
            possibleDirections.add(Direction.UP);
        }
        if (isValidPosition(newPosDown)) {
            numberOfPossibleDirections++;
            possibleDirections.add(Direction.DOWN);
        }
        if (isValidPosition(newPosLeft)) {
            numberOfPossibleDirections++;
            possibleDirections.add(Direction.LEFT);
        }
        if (isValidPosition(newPosRight)) {
            numberOfPossibleDirections++;
            possibleDirections.add(Direction.RIGHT);
        }
        Random rand = new Random();
        int indexOfRandomDirection = rand.nextInt(numberOfPossibleDirections);
        this.currentDirection = possibleDirections.get(indexOfRandomDirection);
    }
    
    /*@Override
    public Monster getType(){
        return Monseter.BASIC_MONSTER:
    }*/
}

//class Monster2 extends Monster {
//
//    @Override
//    public void move(Direction d) {
//
//    }
//
//}
//
//class Monster3 extends Monster {
//
//    @Override
//    public void move(Direction d) {
//
//    }
//
//    private Direction checkClosestPlayer(ArrayList<Cell> space) {
//
//        return;
//    }
//}
//
//class Monster4 extends Monster {
//
//    @Override
//    public void move(Direction d) {
//
//    }
//
//}
