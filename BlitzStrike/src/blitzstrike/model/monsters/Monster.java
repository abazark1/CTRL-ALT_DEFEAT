/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.monsters;

import blitzstrike.model.Cell;
import blitzstrike.model.Direction;
import blitzstrike.model.Game;
import static blitzstrike.model.Game.MAP_SIZE;
import blitzstrike.model.Player;
import blitzstrike.model.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
    private List<Player> players;
    private Player targetPlayer;
    private Player ignoredPlayer;

    public Monster(Position position, Cell[][] space, Game game, Player pl1, Player pl2) {
        this.speed = STANDARD_SPEED;
        this.alive = true;
        this.position = position;
        this.space = space;
        this.currentDirection = Direction.STILL;
        this.game = game;
        this.players = new ArrayList<>();
        this.players.add(pl1);
        this.players.add(pl2);
        this.targetPlayer = null;
        this.ignoredPlayer = null;
    }

    /**
     * Getter if the monster is alive
     *
     * @return alive value
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Getter for the players in the game
     *
     * @return players list
     */
    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * Getter if the monster is alive
     *
     * @param players to update the list of alive players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Getter for the monster's current position
     *
     * @return position value
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Getter for the possible directions for the monster to make
     *
     * @return list of possible valid directions
     */
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

    /**
     * Getter for the target player that picked up the Speed Curse
     *
     * @return the cursed player - targetPlayer
     */
    public Player getTargetPlayer() {
        return this.targetPlayer;
    }
    
    public Player getIgnoredPlayer() {
        return this.ignoredPlayer;
    }

    /**
     * Getter of the index of the targeted player
     *
     * @return the index of the player
     */
    public int targetPlayer() {
        for (int i = 0; i < this.players.size(); i++) {
            if (players.get(i).getFollowedByMonsters()) {
                return i;
            }
        }
        return -1;
    }
    
    public int ignorePlayer() {
        for (int i = 0; i < this.players.size(); i++) {
            if (players.get(i).getRollerSkate()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Sets the alive value to dead
     *
     */
    public void die() {
        this.alive = false;
    }

    /**
     * Checks if the position to be made is valid
     *
     * @param p position to check if the next position is valid to do
     * @return is the position is valid
     */
    public boolean isValidPosition(Position p) {
        return this.space[p.getY()][p.getX()].isWalkable() && !this.game.getPlayer11().hasBombAtPosition(p.getX(), p.getY()) && !this.game.getPlayer22().hasBombAtPosition(p.getX(), p.getY());
    }

    /**
     * Moves the given player depending on its behavior
     *
     */
    public abstract void move();

    /**
     * Settles the current heuristic direction for the monster
     *
     */
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

    /**
     * Settles the current direction randomly so the monster does not move
     * heuristically
     *
     */
    protected void settleCurrentDirectionRandomly() {
        Random rand = new Random();
        List<Direction> possibleDirections = getPossibleDirections();
        if (!possibleDirections.isEmpty()) {
            int indexOfRandomDirection = rand.nextInt(possibleDirections.size());
            this.currentDirection = possibleDirections.get(indexOfRandomDirection);
        }
    }

    /**
     * Settles the monster to move with new decided direction
     *
     */
    protected void moveWithCurrentDirection() {
        Position newPositionWithNewDirection = this.position.translate(currentDirection);
        this.position = newPositionWithNewDirection;
    }

    /**
     * Determines the direction to be made towards the target position
     *
     * @param targetPosition which monster should reach
     * @return direction towards the target posiiton
     */
    protected Direction determineDirectionTowardsPosition(Position targetPosition) {
        int targetX = targetPosition.getX();
        int targetY = targetPosition.getY();

        int currentX = this.position.getX();
        int currentY = this.position.getY();

        int dx = targetX - currentX;
        int dy = targetY - currentY;

        if (dx == 0 && dy == 0) {
            return Direction.STILL;
        } else if (Math.abs(dx) > Math.abs(dy)) {
            return (dx > 0) ? Direction.RIGHT : Direction.LEFT;
        } else {
            return (dy > 0) ? Direction.DOWN : Direction.UP;
        }
    }

    /**
     * Calculates the shortest path to the target position using
     * BFS(Breadth-First Search)
     *
     * @param targetPosition which monster should reach
     * @return list of positions to take to reach the target position
     */
    protected List<Position> calculateShortestPath(Position targetPosition) {
        Queue<Position> queue = new LinkedList<>();
        queue.add(this.position);

        Map<Position, Position> parentMap = new HashMap<>();
        parentMap.put(this.position, null);

        while (!queue.isEmpty()) {
            Position currentPosition = queue.poll();
            if (currentPosition.equals(targetPosition)) {
                break;
            }
            for (Direction dir : Direction.values()) {
                Position nextPosition = currentPosition.translate(dir);
                if (isValidPosition(nextPosition) && !parentMap.containsKey(nextPosition)) {
                    queue.add(nextPosition);
                    parentMap.put(nextPosition, currentPosition);
                }
            }
        }

        List<Position> shortestPath = new ArrayList<>();
        Position current = targetPosition;
        while (current != null) {
            shortestPath.add(current);
            current = parentMap.get(current);
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    /**
     * Updates the targetPlayer value if any of the players got cursed
     *
     */
    public void updateTarget() {
        int index = targetPlayer();
        if (index != -1) {
            this.targetPlayer = players.get(index);
        } else {
            this.targetPlayer = null;
        }
    }
    
    public void updateIgnoredPlayer() {
        int index = ignorePlayer();
        if (index != -1) {
            this.ignoredPlayer = players.get(index);
        } else {
            this.ignoredPlayer = null;
        }
    }

    /**
     * Moves the monster towards the targetPlayer
     *
     * @param targetPosition of the player
     */
    protected void moveToTarget(Position targetPosition) {
        List<Position> path = calculateShortestPath(targetPosition);
        if (!path.isEmpty()) {
            System.out.println("Moving towards target at position: " + targetPosition.getX() + " " + targetPosition.getY());
            Position nextPositionTowardsTarget = path.get(0);
            if (nextPositionTowardsTarget.equals(this.position)) {
                if (path.size() > 1) {
                    nextPositionTowardsTarget = path.get(1);
                }
            }
            Direction directionTowardsTarget = determineDirectionTowardsPosition(nextPositionTowardsTarget);
            if (isValidPosition(this.position.translate(directionTowardsTarget))) {
                this.position = this.position.translate(directionTowardsTarget);
            }
        }
    }

    /**
     * Determines which player is the closest for monsters 3 and 4
     *
     * @param shortestPaths to get the paths towards both players and decide
     * which one is closer
     * @return the index of the closest player
     */
    protected int determineClosestPlayer(List<List<Position>> shortestPaths) {
        int closestPlayerIndex = -1;
        int minPathLength = Integer.MAX_VALUE;
        for (int i = 0; i < shortestPaths.size(); i++) {
            List<Position> path = shortestPaths.get(i);
            if (path != null && path.size() < minPathLength) {
                minPathLength = path.size();
                closestPlayerIndex = i;
            }
        }
        return closestPlayerIndex;
    }
}
