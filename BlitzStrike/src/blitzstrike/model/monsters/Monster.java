/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.monsters;

/**
 *
 * @author aselh
 */
import blitzstrike.model.Cell;
import blitzstrike.model.Direction;
import blitzstrike.model.Game;
import static blitzstrike.model.Game.MAP_SIZE;
import blitzstrike.model.Player;
import blitzstrike.model.Position;
import blitzstrike.model.monsters.MonsterType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Monster {

    public static double STANDARD_SPEED = 1;
    public MonsterType monsterType;

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

    public void die() {
        this.alive = false;
    }

//    public void attack(Player p) {
//
//    }

    public boolean isValidPosition(Position p) {
        return this.space[p.getY()][p.getX()].isWalkable() && !this.game.getPlayer11().hasBombAtPosition(p.getX(), p.getY()) && !this.game.getPlayer22().hasBombAtPosition(p.getX(), p.getY());
    }

    public Position getPosition() {
        return this.position;
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
        
        
    protected void settleCurrentDirectionRandomly() {
        Random rand = new Random();
        List<Direction> possibleDirections = getPossibleDirections();
        if (!possibleDirections.isEmpty()) {
            int indexOfRandomDirection = rand.nextInt(possibleDirections.size());
            this.currentDirection = possibleDirections.get(indexOfRandomDirection);
        }
    }

    protected List<Direction> getPossibleDirections() {
        List<Direction> possibleDirections = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            Position newPos = position.translate(dir);
            if (isValidPosition(newPos)) {
                possibleDirections.add(dir);
            }
        }
        return possibleDirections;
    }
    
    protected void moveWithCurrentDirection() {
        Position newPositionWithNewDirection = this.position.translate(currentDirection);
        this.position = newPositionWithNewDirection;
    }
}
