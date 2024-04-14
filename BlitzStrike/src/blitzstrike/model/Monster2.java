/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import static blitzstrike.model.Game.MAP_SIZE;
import static blitzstrike.model.Monster.STANDARD_SPEED;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ebazali
 */
public class Monster2 extends Monster {

    public Monster2(Position position, Cell[][] space, Game game) {
        super(position, space, game);
        this.speed = STANDARD_SPEED * 0.8;
        this.currentDirection = Direction.STILL;
        this.monsterType = MonsterType.MONSTER2;
    }

    @Override
    public void move() {
        System.out.println("Monster 2 is moving");
        if (this.currentDirection == Direction.STILL) {
            settleCurrentDirection();
            moveWithCurrentDirection();
        } else {
            Position nextPosition = position.translate(currentDirection);
            if (nextPosition.getY() <= 0 || nextPosition.getX() <= 0 || nextPosition.getY() >= MAP_SIZE - 1 || nextPosition.getX() >= MAP_SIZE - 1) {
                settleCurrentDirection();
                moveWithCurrentDirection();
            } else {
                if (space[this.position.getY()][this.position.getX()] instanceof Box || space[this.position.getY()][this.position.getX()] instanceof Wall) {
                    if (space[nextPosition.getY()][nextPosition.getX()] instanceof Empty) {
                        this.position = nextPosition;
                        settleCurrentDirection();
                    } else {
                        moveWithCurrentDirection();
                    }
                } else {
                    moveWithCurrentDirection();
                }
            }
        }
    }
    
    
    protected void settleCurrentDirectionRandomly() {
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

    protected void moveWithCurrentDirection() {
        Position newPositionWithNewDirection = this.position.translate(currentDirection);
        this.position = newPositionWithNewDirection;
    }

    protected void settleCurrentDirection() {
        Position newPosUp = position.translate(Direction.UP);
        Position newPosDown = position.translate(Direction.DOWN);
        Position newPosLeft = position.translate(Direction.LEFT);
        Position newPosRight = position.translate(Direction.RIGHT);
        List<Direction> possibleDirections = new ArrayList<>();
        possibleDirections.add(Direction.STILL);
        if (isValidPosition(newPosUp)) {
            possibleDirections.add(Direction.UP);
        }
        if (isValidPosition(newPosDown)) {
            possibleDirections.add(Direction.DOWN);
        }
        if (isValidPosition(newPosLeft)) {
            possibleDirections.add(Direction.LEFT);
        }
        if (isValidPosition(newPosRight)) {
            possibleDirections.add(Direction.RIGHT);
        }
        Random rand = new Random();
        int indexOfRandomDirection = rand.nextInt(possibleDirections.size());
        this.currentDirection = possibleDirections.get(indexOfRandomDirection);
    }
}
