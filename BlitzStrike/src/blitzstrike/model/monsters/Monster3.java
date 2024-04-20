/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.monsters;

import blitzstrike.model.Cell;
import blitzstrike.model.Direction;
import blitzstrike.model.Game;
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

/**
 *
 * @author ebazali
 */
public class Monster3 extends Monster {

    private Direction currentDirection;
    private List<Player> players;

    public Monster3(Position position, Cell[][] space, Game game, Player pl1, Player pl2) {
        super(position, space, game);
        this.currentDirection = Direction.STILL;
        this.speed = STANDARD_SPEED * 2;
        this.players = new ArrayList<>();
        this.players.add(pl1);
        this.players.add(pl2);
        this.monsterType = MonsterType.MONSTER3;
    }

    //@Override
    public void move() {
        System.out.println("Type3 monster should move");

        List<List<Position>> shortestPaths = new ArrayList<>();
        for (Player player : players) {
            shortestPaths.add(calculateShortestPath(player.getPosition()));
        }

        int closestPlayerIndex = determineClosestPlayer(shortestPaths);
        System.out.println("Closest player: " + closestPlayerIndex);

        if (closestPlayerIndex != -1) {
            List<Position> shortestPathToClosestPlayer = shortestPaths.get(closestPlayerIndex);
            if (!shortestPathToClosestPlayer.isEmpty()) {
                System.out.println("Moving towards player: " + closestPlayerIndex);
                Position nextPositionTowardsPlayer;
                if (shortestPathToClosestPlayer.size() > 1) {
                    nextPositionTowardsPlayer = shortestPathToClosestPlayer.get(1); // Get the second position in the path
                } else {
                    nextPositionTowardsPlayer = shortestPathToClosestPlayer.get(0); // Get the only available position
                }
                Direction directionTowardsPlayer = determineDirectionTowardsPosition(nextPositionTowardsPlayer);
                if (directionTowardsPlayer != null) {
                    this.position = this.position.translate(directionTowardsPlayer);
                }
            }
        } else {
            System.out.println("Walking randomly");
            settleCurrentDirectionRandomlyMonster3();
            moveWithNewDirection();
        }

        List<Player> alivePlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.isAlive()) {
                alivePlayers.add(player);
            }
        }
        players = alivePlayers;
    }



    private Direction determineDirectionTowardsPosition(Position targetPosition) {
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

    
    private List<Position> calculateShortestPath(Position targetPosition) {
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


    private int determineClosestPlayer(List<List<Position>> shortestPaths) {
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

    private void settleCurrentDirectionRandomlyMonster3() {
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
    
    private void moveWithNewDirection() {
        Position newPositionWithNewDirection = this.position.translate(currentDirection);
        this.position = newPositionWithNewDirection;
    }
}
