/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.monsters;

import blitzstrike.model.Cell;
import blitzstrike.model.Direction;
import blitzstrike.model.Game;
import blitzstrike.model.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ebazali
 */
public class Monster1 extends Monster {

    private int counterUntilChangeDirection;
    private final int LIMIT_TO_CHANGE_DIRECTION_RANDOMLY = 3;

    public Monster1(Position position, Cell[][] space, Game game) {
        super(position, space, game);
        this.counterUntilChangeDirection = 0;
        this.monsterType = MonsterType.BASIC;

    }

    //@Override
    public void move() {
        System.out.println("Monster 1 is moving");
        if (this.currentDirection == Direction.STILL) {
            settleCurrentDirectionRandomly();
            moveWithCurrentDirection();
        } else {
            Position newPosition = this.position.translate(currentDirection);
            if (isValidPosition(newPosition)) {
                this.position = newPosition;
            } else {
                this.counterUntilChangeDirection++;
                if (this.counterUntilChangeDirection >= LIMIT_TO_CHANGE_DIRECTION_RANDOMLY) {
                    settleCurrentDirectionRandomly();
                    moveWithCurrentDirection();
                    this.counterUntilChangeDirection = 0;
                } else {
                    settleCurrentDirection();
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
