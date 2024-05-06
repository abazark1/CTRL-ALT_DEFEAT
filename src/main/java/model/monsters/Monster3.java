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

public class Monster3 extends Monster {

    private Direction currentDirection;

    public Monster3(Position position, Cell[][] space, Game game, Player pl1, Player pl2) {
        super(position, space, game, pl1, pl2);
        this.currentDirection = Direction.STILL;
        this.speed = STANDARD_SPEED * 2;
        this.monsterType = MonsterType.MONSTER3;
    }

    /**
     * Moves the monster with its own logic
     *
     */
    @Override
    public void move() {
        //System.out.println("Type3 monster should move");
        updateTarget();
        updateIgnoredPlayer();
        if (this.getIgnoredPlayer() != null) {
            //System.out.println("Monster 3 i ignoring player");

            settleCurrentDirectionRandomly();
            moveWithCurrentDirection();
        }
        else if (this.getTargetPlayer() != null) {
            moveToTarget(getTargetPlayer().getPosition());
        }  else {
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
                    if (isValidPosition(this.position.translate(directionTowardsPlayer))) {
                        this.position = this.position.translate(directionTowardsPlayer);
                    }
                }
            } else {
                System.out.println("Walking randomly");
                settleCurrentDirectionRandomly();
                moveWithCurrentDirection();
            }

            List<Player> alivePlayers = new ArrayList<>();
            for (Player player : getPlayers()) {
                if (player.isAlive()) {
                    alivePlayers.add(player);
                }
            }
            setPlayers(alivePlayers);
        }
    }
}
