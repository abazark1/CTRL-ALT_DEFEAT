/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import blitzstrike.model.effects.Effect;
import blitzstrike.model.effects.curses.Curse;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author artur
 */
public class Player {

    public static final int STANDARD_BOMB_RANGE = 3;
    public static final int STANDARD_BOMB_NUMBER = 1;
    public static final double STANDARD_PLAYERS_SPEED = 1.0;
    public static final int STANDARD_OBSTACLE_NUMBER = 0;

    private String name;
    private Position position;
    private boolean alive;
    private List<Bomb> bombs;
    private List<Position> obstacles;
    private int bombRange;
    private double speed;
    private int maxBombNumber;
    private boolean isInvincible;
    private LocalTime invincibleTimer;
    private boolean followedByMonsters;
    private boolean isGhost;
    private LocalTime ghostTimer;
    private boolean isDetonatorOn;
    private int maxNumberOfObstacles;
//    private LocalTime bombRangeCurseStartTime;
    private LocalTime speedCurseStartTime;
    private boolean noBombCurse;
    private LocalTime noBombCurseStartTime;
    private boolean placeBombImmediatelyCurse;
    private LocalTime immediateBombCurseStartTime;
    private LocalTime deathTime;
    private List<Curse> curses;

    private Cell[][] space;

    public Player(String name) {
        this.name = name;
        this.bombs = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.curses = new ArrayList<>();
        this.alive = true;
        this.bombRange = STANDARD_BOMB_RANGE;
        this.speed = STANDARD_PLAYERS_SPEED;
        this.maxBombNumber = STANDARD_BOMB_NUMBER;
        this.isInvincible = false;
        this.isGhost = false;
        this.isDetonatorOn = false;
        this.maxNumberOfObstacles = STANDARD_OBSTACLE_NUMBER;
        this.noBombCurse = false;
        this.placeBombImmediatelyCurse = false;
        this.followedByMonsters = false;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSpace(Cell[][] space) {
        this.space = space;
    }

    public List<Bomb> getBombs() {
        return this.bombs;
    }

    /**
     * Getter to check if the players should get followed by monsters
     *
     * @return followedByMonsters value
     */
    public boolean getFollowedByMonsters(){
        return this.followedByMonsters;
    }
    
    /**
     * Sets the followedByMonsters value to true or false
     *
     * @param value that should the followedByMonsters value
     */
    public void setFollowedByMonsters(boolean value) {
        this.followedByMonsters = value;
    }
    
    public boolean isAlive() {
        return this.alive;
    }

    public LocalTime getDeathTime() {
        return this.deathTime;
    }

    public void placeBomb() {
        if (isAlive() && this.getBombs().size() < maxBombNumber && !this.noBombCurse) {
            Bomb bomb = new Bomb(this.position, this, this.space);
            this.bombs.add(bomb);
            System.out.println("I've placed a bomb");
        }
    }

    /**
     * Places the bomb whenever the bombs are available and if the player is
     * still alive, if the immediate bomb curse is applied
     */
    public void placeBombFromImmediateBombCurse() {
        if (this.placeBombImmediatelyCurse) {
            placeBomb();
        }
    }

    /**
     * Sets bomb range to the standard value
     */
    public void resetBombRange() {
        this.bombRange = STANDARD_BOMB_RANGE;
    }

    /**
     * Adds curse to the ArrayList
     *
     * @param curse to be added to the ArrayList
     */
    public void addCurse(Curse curse) {
        this.curses.add(curse);
    }

    /**
     * Removes terminated curses from the ArrayList
     */
    public void removeTerminatedCurses() {
        Iterator<Curse> cursesIterator = this.curses.iterator();
        while (cursesIterator.hasNext()) {
            Curse c = cursesIterator.next();
            if (c.isTerminated()) {
                cursesIterator.remove();
            }
        }
    }

    /**
     * Removes the terminated effects from the ArrayList
     */
    public void handleCurseRemoval() {
        for (Curse c : this.curses) {
            c.terminateEffect(this);
        }
    }

    public void placeObstacle() {
        this.obstacles.add(this.position);
        System.out.println("I've placed an obstacle");
    }

    public void movePlayer(Direction direction, Player player2) {
        if (isAlive() && isValidPosition(direction, player2)) {
            this.position = this.position.translate(direction);
        }

    }

    // check if the player has a bomb on certain field
    public boolean hasBombAtPosition(int x, int y) {
        for (Bomb bomb : bombs) {
            Position bombPos = bomb.getPosition();
            return bombPos.getX() == x && bombPos.getY() == y;
        }
        return false;
    }

    public Bomb getBomb(int x, int y) {
        for (Bomb bomb : bombs) {
            Position bombPos = bomb.getPosition();
            if (bombPos.getX() == x && bombPos.getY() == y) {
                return bomb;
            }
        }
        return null;
    }

    public void setBombs(ArrayList<Bomb> bombs) {
        this.bombs = bombs;
    }

    public void removeBomb(Bomb bomb) {
        Iterator<Bomb> bombIterator = this.bombs.iterator();
        while (bombIterator.hasNext()) {
            Bomb b = bombIterator.next();
            if (b.getPosition().getX() == bomb.getPosition().getX() && b.getPosition().getY() == bomb.getPosition().getY()) {
                bombIterator.remove();
                System.out.println("The bomb at X:" + bomb.getPosition().getX() + " Y:" + bomb.getPosition().getY() + " was removed");
            }
        }
    }

    public void die() {
        System.out.println(this.name + " has died");
        this.alive = false;
        this.deathTime = LocalTime.now();
        removePlayerFromMap();
    }

    public boolean isValidPosition(Direction direction, Player player2) {
        Position newPosition = this.position.translate(direction);

        if (!this.space[newPosition.getY()][newPosition.getX()].isWalkable()) {
            return false;
        }

        if (player2.getPosition().equals(newPosition)) {
            return false;
        }

        if (hasBombAtPosition(newPosition.getX(), newPosition.getY())) {
            return false;
        }
        return true;
    }

    public void activateEffect(Effect effect) {
        effect.applyEffect(this);
    }

    public void setImmediateBombCurse(boolean value) {
        this.placeBombImmediatelyCurse = value;
    }

    public LocalTime getImmediateBombCurseTimer() {
        return this.immediateBombCurseStartTime;
    }

    public void setImmediateBombCurseTimer(LocalTime time) {
        if (time != null) {
            this.immediateBombCurseStartTime = time;
        } else {
            this.immediateBombCurseStartTime = null;
        }
    }

    public void setNoBombCurse(boolean value) {
        this.noBombCurse = value;
    }

    public LocalTime getNoBombCurseTimer() {
        return this.noBombCurseStartTime;
    }

    public void setNoBombCurseTimer(LocalTime time) {
        if (time != null) {
            this.immediateBombCurseStartTime = time;
        } else {
            this.immediateBombCurseStartTime = null;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBombRangeCurse(int value) {
        this.bombRange = value;
    }

//    public LocalTime getBombRangeCurseTimer() {
//        return this.bombRangeCurseStartTime;
//    }
//
//    public void setBombRangeCurseStartTime() {
//        this.bombRangeCurseStartTime = LocalTime.now();
//    }


    public LocalTime getSpeedCurseTimer() {
        return this.speedCurseStartTime;
    }

    public void setSpeedCurseTimer(LocalTime time) {
        if (time != null) {
            this.immediateBombCurseStartTime = time;
        } else {
            this.immediateBombCurseStartTime = null;
        }
    }

    public Position getPosition() {
        return this.position;
    }

    public int getBombNumber() {
        return this.maxBombNumber;
    }

    public void setBombNumber(int newBombNumber) {
        this.maxBombNumber = newBombNumber;
    }

    public int getBlastRange() {
        return this.bombRange;
    }

    public void setBlastRange(int newBlastRange) {
        this.bombRange = newBlastRange;
    }

    public String getName() {
        return this.name;
    }

    public void reset() {
        //this.position = new Position(0, 0);
        this.bombs = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.alive = true;
        this.bombRange = 3;
        this.speed = 1.0;
        this.maxBombNumber = 1;
        this.isInvincible = false;
        this.isGhost = false;
        this.isDetonatorOn = false;
        this.maxNumberOfObstacles = 0;
        this.noBombCurse = false;
        this.placeBombImmediatelyCurse = false;
    }

    private void removePlayerFromMap() {
        Position playerPosition = this.position;
        space[playerPosition.getY()][playerPosition.getX()] = new Empty(playerPosition);
        this.position = new Position(-10, -10);
    }
}
