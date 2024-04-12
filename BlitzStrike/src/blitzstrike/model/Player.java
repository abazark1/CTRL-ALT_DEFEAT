/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author artur
 */
public class Player {

    private String name;
    private Position position;
    private boolean alive;
    private List<Bomb> bombs;
    private List<Position> obstacles;
    private int blastRange;
    private double speed;
    private int maxBombNumber;
    private boolean isInvincible;
    private LocalTime invincibleTimer;
    private boolean isGhost;
    private LocalTime ghostTimer;
    private boolean isDetonatorOn;
    private int maxNumberOfObstacles;
    private LocalTime blastRangeDecreaseTimer;
    private LocalTime speedDecreaseTimer;
    private boolean noBombCurse;
    private LocalTime noBombCurseTimer;
    private boolean placeBombImmediatelyCurse;
    private LocalTime immediateBombCurseTimer;
    private LocalTime deathTime;

    private Cell[][] space;

    public Player(String name) {
        this.name = name;
        this.bombs = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.alive = true;
        this.blastRange = 3;
        this.speed = 1.0;
        this.maxBombNumber = 1;
        this.isInvincible = false;
        this.isGhost = false;
        this.isDetonatorOn = false;
        this.maxNumberOfObstacles = 0;
        this.noBombCurse = false;
        this.placeBombImmediatelyCurse = false;
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

    public boolean isAlive() {
        return this.alive;
    }

    public LocalTime getDeathTime() {
        return this.deathTime;
    }

    public void placeBomb() {
        if (isAlive() && this.getBombs().size() < maxBombNumber) {
            Bomb bomb = new Bomb(this.position, this, this.space);
            this.bombs.add(bomb);
            System.out.println("I've placed a bomb");
        }
    }

    public void placeObstacle() {
        this.obstacles.add(this.position);
        System.out.println("I've placed an obstacle");
    }

    public void movePlayer(Direction direction, Player player2, ArrayList<Monster> monsters) {
        if (isAlive() && isValidPosition(direction, player2, monsters)) {
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
        this.alive = false;
        this.deathTime = LocalTime.now();
        removePlayerFromMap();
    }

    public boolean isValidPosition(Direction direction, Player player2, ArrayList<Monster> monsters) {
        Position newPosition = this.position.translate(direction);

        if (!this.space[newPosition.getY()][newPosition.getX()].isWalkable()) {
            return false;
        } else if (player2.getPosition().equals(newPosition)) {
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
        return this.immediateBombCurseTimer;
    }

    public void setImmediateBombCurseTimer(LocalTime time) {
        if (time != null) {
            this.immediateBombCurseTimer = time;
        } else {
            this.immediateBombCurseTimer = null;
        }
    }

    public void setNoBombCurse(boolean value) {
        this.noBombCurse = value;
    }

    public LocalTime getNoBombCurseTimer() {
        return this.noBombCurseTimer;
    }

    public void setNoBombCurseTimer(LocalTime time) {
        if (time != null) {
            this.immediateBombCurseTimer = time;
        } else {
            this.immediateBombCurseTimer = null;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBombRangeCurse(int value) {
        this.blastRange = value;
    }

    public LocalTime getBombRangeCurseTimer() {
        return this.blastRangeDecreaseTimer;
    }

    public void setBombRangeCurseTimer(LocalTime time) {
        if (time != null) {
            this.immediateBombCurseTimer = time;
        } else {
            this.immediateBombCurseTimer = null;
        }
    }

    public void setSpeedCurse(double value) {
        this.speed = value;
    }

    public LocalTime getSpeedCurseTimer() {
        return this.speedDecreaseTimer;
    }

    public void setSpeedCurseTimer(LocalTime time) {
        if (time != null) {
            this.immediateBombCurseTimer = time;
        } else {
            this.immediateBombCurseTimer = null;
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
        return this.blastRange;
    }

    public void setBlastRange(int newBlastRange) {
        this.blastRange = newBlastRange;
    }
    
    public String getName() {
        return this.name;
    }

    public void reset() {
        this.position = new Position(0, 0);
        this.bombs = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.alive = true;
        this.blastRange = 3;
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
