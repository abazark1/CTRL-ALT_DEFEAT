/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author aselh
 */
import static blitzstrike.model.Game.MAP_SIZE;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Monster {

    public static final double STANDARD_SPEED = 1;

    protected double speed;
    protected boolean alive;
    protected Position position;
    protected Cell[][] space = new Cell[MAP_SIZE][MAP_SIZE];
    protected Direction currentDirection;
    private Game game;

    public Monster(Position position, Cell[][] space, Game game) {
        this.speed = STANDARD_SPEED;
        this.alive = true;
        this.position = position;
        this.space = space;
        this.currentDirection = Direction.STILL;
        this.game = game;
    }

    public abstract void move();

    public boolean isAlive() {
        return this.alive;
    }

    public void getExploded() {
        this.alive = false;
    }

    public void attack(Player p) {

    }

    public boolean isValidPosition(Position p) {
        return this.space[p.getY()][p.getX()].isWalkable() && !this.game.getPlayer11().hasBombAtPosition(p.getX(), p.getY()) && !this.game.getPlayer22().hasBombAtPosition(p.getX(), p.getY());
    }

    public Position getPosition() {
        return this.position;
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

class BasicMonster extends Monster {

    private int counterUntilChangeDirection;
    private final int LIMIT_TO_CHANGE_DIRECTION_RANDOMLY = 3;

    public BasicMonster(Position position, Cell[][] space, Game game) {
        super(position, space, game);
        this.counterUntilChangeDirection = 0;
    }

    @Override
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
}

class Monster2 extends Monster {

    public Monster2(Position position, Cell[][] space, Game game) {
        super(position, space, game);
        this.speed = STANDARD_SPEED * 0.8;
        this.currentDirection = Direction.STILL;
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

}

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
