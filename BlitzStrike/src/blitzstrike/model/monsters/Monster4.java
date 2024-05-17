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
import java.util.List;
import java.util.Random;

public class Monster4 extends Monster {

    private static final double WRONG_DECISION_PROBABILITY = 0.3;
    private Random rand;

    public Monster4(Position position, Cell[][] space, Game game, Player pl1, Player pl2) {
        super(position, space, game, pl1, pl2);
        this.speed = STANDARD_SPEED;
        this.monsterType = MonsterType.MONSTER4;
        this.rand = new Random();
    }

    /**
     * Moves the monster with its own logic
     *
     */
    @Override
    public void move() {
        //System.out.println("Type4 monster should move");
        updateTarget();
        updateIgnoredPlayer();
        if (this.getIgnoredPlayer() != null) {
            Player targetPlayer = null;
            for (Player player : getPlayers()) {
                if (!player.equals(this.getIgnoredPlayer())) {
                    targetPlayer = player;
                    break;
                }
            }

            if (targetPlayer != null) {
                moveTowardsWithChanceOfWrongDecision(targetPlayer.getPosition());
            } else {
                settleCurrentDirectionRandomly();
                moveWithCurrentDirection();
            }
        }
        else if (this.getTargetPlayer() != null) {
            moveToTarget(getTargetPlayer().getPosition());
        } else {

            List<List<Position>> shortestPaths = new ArrayList<>();
            for (Player player : getPlayers()) {
                shortestPaths.add(calculateShortestPath(player.getPosition()));
            }

            int closestPlayerIndex = determineClosestPlayer(shortestPaths);
            //System.out.println("Closest player: " + closestPlayerIndex);

            if (closestPlayerIndex != -1) {
                List<Position> shortestPathToClosestPlayer = shortestPaths.get(closestPlayerIndex);
                if (!shortestPathToClosestPlayer.isEmpty()) {
                    //System.out.println("Moving towards player: " + closestPlayerIndex);
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
                            //System.out.println("Making a wrong decision");
                            newDirection = makeWrongDecision(directionTowardsPlayer);
                        } while (!isValidPosition(this.position.translate(newDirection)));

                        directionTowardsPlayer = newDirection;
                    }

                    if (isValidPosition(this.position.translate(directionTowardsPlayer))) {
                        this.position = this.position.translate(directionTowardsPlayer);
                    }
                }
            } else {
                //System.out.println("Walking randomly");
                settleCurrentDirectionRandomly();
                moveWithCurrentDirection();
            }

            updateAlivePlayers();
        }
    }

    /**
     * Determines the wrong directions for the monster to move
     * 
     * @param the direction in which the monster should move
     *
     * @return the wrong direction for monster to make
     */
    private Direction makeWrongDecision(Direction correctDirection) {
        Direction wrongDirection = correctDirection;
        do {
            wrongDirection = Direction.values()[rand.nextInt(Direction.values().length)];
        } while (wrongDirection == correctDirection);

        return wrongDirection;
    }

    /**
     * Updates the list of alive players
     *
     */
    private void updateAlivePlayers() {
        List<Player> alivePlayers = new ArrayList<>();
        for (Player player : getPlayers()) {
            if (player.isAlive()) {
                alivePlayers.add(player);
            }
        }
        setPlayers(alivePlayers);
    }
    
    private void moveTowardsWithChanceOfWrongDecision(Position targetPosition) {
        Direction directionTowardsPlayer = determineDirectionTowardsPosition(targetPosition);

        if (rand.nextDouble() < WRONG_DECISION_PROBABILITY) {
            Direction newDirection;
            do {
                newDirection = makeWrongDecision(directionTowardsPlayer);
            } while (!isValidPosition(this.position.translate(newDirection)));

            directionTowardsPlayer = newDirection;
        }

        if (isValidPosition(this.position.translate(directionTowardsPlayer))) {
            this.position = this.position.translate(directionTowardsPlayer);
        }
    }
}
