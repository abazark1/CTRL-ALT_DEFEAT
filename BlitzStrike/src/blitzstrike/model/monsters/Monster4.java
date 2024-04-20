/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.monsters;

/**
 *
 * @author ebazali
 */
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

public class Monster4 extends Monster {
    
    private static final double WRONG_DECISION_PROBABILITY = 0.3;
    private Random rand;
    private List<Player> players;


    public Monster4(Position position, Cell[][] space, Game game, Player pl1, Player pl2) {
        super(position, space, game);
        this.players = new ArrayList<>();
        this.players.add(pl1);
        this.players.add(pl2);
        this.speed = STANDARD_SPEED;
        this.monsterType = MonsterType.MONSTER4;
        this.rand = new Random();
    }

    @Override
    public void move() {
        System.out.println("Type4 monster should move");

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
                    nextPositionTowardsPlayer = shortestPathToClosestPlayer.get(1); 
                } else {
                    nextPositionTowardsPlayer = shortestPathToClosestPlayer.get(0);
                }
                Direction directionTowardsPlayer = determineDirectionTowardsPosition(nextPositionTowardsPlayer);

                if (rand.nextDouble() < WRONG_DECISION_PROBABILITY) {
                    Direction newDirection;
                    do {
                        System.out.println("Making a wrong decision");
                        newDirection = makeWrongDecision(directionTowardsPlayer);
                    } while (!isValidPosition(this.position.translate(newDirection)));

                    directionTowardsPlayer = newDirection;
                }

                if (isValidPosition(this.position.translate(directionTowardsPlayer))) {
                    this.position = this.position.translate(directionTowardsPlayer);
                }
            }
        } else {
            System.out.println("Walking randomly");
            settleCurrentDirectionRandomly();
            moveWithCurrentDirection();
        }

        updateAlivePlayers();
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
    

    private Direction makeWrongDecision(Direction correctDirection) {
        Direction wrongDirection = correctDirection;
        do {
            wrongDirection = Direction.values()[rand.nextInt(Direction.values().length)];
        } while (wrongDirection == correctDirection);

        return wrongDirection;
    }
    

    private void updateAlivePlayers() {
        List<Player> alivePlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.isAlive()) {
                alivePlayers.add(player);
            }
        }
        players = alivePlayers;
    }
}

