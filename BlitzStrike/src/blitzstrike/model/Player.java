/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import static blitzstrike.model.Game.MAP_SIZE;
import blitzstrike.model.effects.Effect;
import blitzstrike.model.effects.curses.Curse;
import blitzstrike.model.effects.powerups.PowerUp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {

    public static final int STANDARD_BOMB_RANGE = 4;
    public static final int STANDARD_BOMB_NUMBER = 1;
    public static final int STANDARD_OBSTACLE_NUMBER = 0;

    private String name;
    private Position position;
    private boolean alive;
    private List<Bomb> bombs;
    private List<ObstacleBox> obstacles;
    private int bombRange;
    private int maxBombNumber;
    private boolean isInvincible;
    private boolean followedByMonsters;
    private boolean isGhost;
    private boolean isDetonatorOn;
    private int maxNumberOfObstacles;
    private LocalTime speedCurseStartTime;
    private boolean noBombCurse;
    private LocalTime noBombCurseStartTime;
    private boolean placeBombImmediatelyCurse;
    private LocalTime immediateBombCurseStartTime;
    private LocalTime deathTime;
    private List<Curse> curses;
    private List<PowerUp> powerups;

    private Cell[][] space;
    private boolean rollerSkateWorking;

    public Player(String name) {
        this.name = name;
        this.bombs = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.curses = new ArrayList<>();
        this.powerups = new ArrayList<>();
        this.alive = true;
        this.bombRange = STANDARD_BOMB_RANGE;
        this.maxBombNumber = STANDARD_BOMB_NUMBER;
        this.isInvincible = false;
        this.isGhost = false;
        this.isDetonatorOn = false;
        this.maxNumberOfObstacles = STANDARD_OBSTACLE_NUMBER;
        this.noBombCurse = false;
        this.placeBombImmediatelyCurse = false;
        this.followedByMonsters = false;
        this.rollerSkateWorking = false;
    }
    
    public int getMaxNumOfObstacles(){
        return this.maxNumberOfObstacles;
    }

    public List<Bomb> getBombs() {
        return this.bombs;
    }

    public List<ObstacleBox> getObstacles() {
        return this.obstacles;
    }

    public String getName() {
        return this.name;
    }

    public int getBombNumber() {
        return this.maxBombNumber;
    }

    public boolean getRollerSkate() {
        return this.rollerSkateWorking;
    }

    public LocalTime getDeathTime() {
        return this.deathTime;
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

    public ObstacleBox getObstacle(int x, int y) {
        for (ObstacleBox obstacle : obstacles) {
            Position obstPos = obstacle.getPosition();
            if (obstPos.getX() == x && obstPos.getY() == y) {
                return obstacle;
            }
        }
        return null;
    }

    public int getBlastRange() {
        return this.bombRange;
    }

    public Position getPosition() {
        return this.position;
    }

    public LocalTime getSpeedCurseTimer() {
        return this.speedCurseStartTime;
    }

    public LocalTime getNoBombCurseTimer() {
        return this.noBombCurseStartTime;
    }

    public LocalTime getImmediateBombCurseTimer() {
        return this.immediateBombCurseStartTime;
    }

    public List<PowerUp> getPowerups() {
        return this.powerups;
    }

    public List<Curse> getCurses() {
        return this.curses;
    }

    /*
     * Getter to check if the players should get followed by monsters
     *
     * @return followedByMonsters value
     */
    public boolean getFollowedByMonsters() {
        return this.followedByMonsters;
    }

    public int getMaxNumberOfObstacles() {
        return this.maxNumberOfObstacles;
    }

    public void setMaxNumberOfObstacles(int newNum) {
        this.maxNumberOfObstacles = newNum;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSpace(Cell[][] space) {
        this.space = space;
    }

    /*
     * Sets the followedByMonsters value to true or false
     *
     * @param value that should the followedByMonsters value
     */
    public void setFollowedByMonsters(boolean value) {
        this.followedByMonsters = value;
    }

    public void setRollerSkate(boolean value) {
        this.rollerSkateWorking = value;
    }

    public void setInvincible(boolean value) {
        this.isInvincible = value;
    }

    public void setGhost(boolean value) {
        this.isGhost = value;
    }

    public void setIsDetonatorOn(boolean value) {
        this.isDetonatorOn = value;
    }

    public void setImmediateBombCurse(boolean value) {
        this.placeBombImmediatelyCurse = value;
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

    public void setSpeedCurseTimer(LocalTime time) {
        if (time != null) {
            this.immediateBombCurseStartTime = time;
        } else {
            this.immediateBombCurseStartTime = null;
        }
    }

    public void setBombNumber(int newBombNumber) {
        this.maxBombNumber = newBombNumber;
    }

    public void setBlastRange(int newBlastRange) {
        this.bombRange = newBlastRange;
    }

    public void setBombs(ArrayList<Bomb> bombs) {
        this.bombs = bombs;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public boolean isInvincible() {
        return this.isInvincible;
    }

    public boolean isGhost() {
        return this.isGhost;
    }

    public boolean isDetonatorOn() {
        return this.isDetonatorOn;
    }

    //////////////////OPERATIONS////////////////////////////////////////////////
    /*
     * Places a bomb on the player's current position if the player is alive,
     * has not reached the maximum number of bombs they can place, and is not
     * affected by the no-bomb curse.
     *
     * When a bomb is placed, it's added to the player's list of bombs. The game
     * will process this bomb's explosion when its countdown finishes.
     */
    public void placeBomb() {
        if (this.isDetonatorOn) {
            if (this.getBombs().size() == maxBombNumber) {
                this.isDetonatorOn = false;
            }
            actuallyPlaceBomb();
        } else {
            actuallyPlaceBomb();
        }
    }

    /*
     * Places the bomb whenever the bombs are available and if the player is
     * still alive, if the immediate bomb curse is applied
     */
    public void placeBombFromImmediateBombCurse() {
        if (this.placeBombImmediatelyCurse) {
            placeBomb();
        }
    }

    /*
     * Sets bomb range to the standard value
     */
    public void resetBombRange() {
        this.bombRange = STANDARD_BOMB_RANGE;
    }

    /*
    * Adds curse to the ArrayList
     *
     * @param curse to be added to the ArrayList
     */
    public void addCurse(Curse curse) {
        this.curses.add(curse);
    }

    /*
     * Adds powerup to the ArrayList
     *
     * @param powerup to be added to the ArrayList
     */
    public void addPowerup(PowerUp powerup) {
        this.powerups.add(powerup);
    }

    /*
     * Removes used powerups from the list
     *
     * @param
     */
    public void removeTerminatedPowerUps() {
        Iterator<PowerUp> powerupsIterator = this.powerups.iterator();
        while (powerupsIterator.hasNext()) {
            PowerUp p = powerupsIterator.next();
            if (p.isTerminated()) {
                powerupsIterator.remove();
            }
        }
    }

    public void handlePowerupRemoval() {
        for (PowerUp p : this.powerups) {
            p.terminateEffect(this);
        }
    }

    /*
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

    /*
     * Removes the terminated effects from the ArrayList
     */
    public void handleCurseRemoval() {
        for (Curse c : this.curses) {
            c.terminateEffect(this);
        }
    }

    /*
     * For the player to place obstacle after obtaining the powerup
     *
     * @param
     */
    public void placeObstacle() {

        if (isAlive() && this.getObstacles().size() < maxNumberOfObstacles) {
            ObstacleBox obstacleBox = new ObstacleBox(this.position, this);
            addObstacle(obstacleBox);
            obstacleBox.walkable = false;
            this.space[position.getY()][position.getX()] = obstacleBox;
            System.out.println("I've placed an obstacle");
        }
    }

    public void addObstacle(ObstacleBox obstacleBox) {
        this.obstacles.add(obstacleBox);
    }

    /*
     * Method to control the movements of the players
     *
     * @param direction, player
     */
    public void movePlayer(Direction direction, Player player2) {
        if (isAlive() && isValidPosition(direction, player2)) {
            this.position = this.position.translate(direction);
        }

    }

    /*
     * Checks if the player has a bomb at the specified coordinates.
     *
     * @param x The x-coordinate of the position to check.
     * @param y The y-coordinate of the position to check.
     * @return true if there is a bomb at the specified position, false
     * otherwise.
     */
    public boolean hasBombAtPosition(int x, int y) {
        for (Bomb bomb : bombs) {
            Position bombPos = bomb.getPosition();
            return bombPos.getX() == x && bombPos.getY() == y;
        }
        return false;
    }

    public boolean hasObstacleAtPosition(int x, int y) {
        for (ObstacleBox obstacle : obstacles) {
            Position obstPos = obstacle.getPosition();
            return obstPos.getX() == x && obstPos.getY() == y;
        }
        return false;
    }

    /*
     * Removes a bomb from the player's list of bombs. The bomb to be removed is
     * identified by its position.
     *
     * @param bomb The bomb to be removed.
     */
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

    public void removeObstacle(ObstacleBox obst) {
        Iterator<ObstacleBox> obstIterator = this.obstacles.iterator();
        while (obstIterator.hasNext()) {
            ObstacleBox o = obstIterator.next();
            if (o.getPosition().getX() == obst.getPosition().getX() && o.getPosition().getY() == obst.getPosition().getY()) {
                obstIterator.remove();
                System.out.println("The bomb at X:" + obst.getPosition().getX() + " Y:" + obst.getPosition().getY() + " was removed");
            }
        }
    }

    /*
     * Marks the player as dead and logs their death time. Also triggers the
     * removal of the player from the map.
     */
    public void die() {
        System.out.println(this.name + " has died");
        this.alive = false;
        this.deathTime = LocalTime.now();
        removePlayerFromMap();
    }

    /*
     * Validates if the position in the given direction is walkable. The
     * validation checks for obstacles, other players, and bombs in the new
     * position.
     *
     * @param direction The direction the player wants to move to.
     * @param player2 The other player in the game.
     * @return true if the position is walkable, false otherwise.
     */
    public boolean isValidPosition(Direction direction, Player player2) {
        Position newPosition = this.position.translate(direction);

        if (newPosition.getX() == 0 || newPosition.getY() == 0 || newPosition.getX() == MAP_SIZE - 1 || newPosition.getY() == MAP_SIZE - 1) {
            return false;
        }
        
        if (this.isGhost()) {
            if (player2.getPosition().equals(newPosition)) {
                return false;
            }
            if (!this.space[newPosition.getY()][newPosition.getX()].isWalkable()) {
                return true;
            }

            if (hasBombAtPosition(newPosition.getX(), newPosition.getY())) {
                return true;
            }

        } else {
            if (!this.space[newPosition.getY()][newPosition.getX()].isWalkable()) {
                return false;
            }

            if (player2.getPosition().equals(newPosition)) {
                return false;
            }

            if (hasBombAtPosition(newPosition.getX(), newPosition.getY())) {
                return false;
            }

            if (hasObstacleAtPosition(newPosition.getX(), newPosition.getY())) {
                return false;
            }
        }
        return true;
    }

    /*
     * Applies an effect to the player.
     *
     * @param effect The effect to be applied.
     */
    public void activateEffect(Effect effect) {
        effect.applyEffect(this);
    }

    /*
     * Resets the player's state for a new round. It clears any bombs,
     * obstacles, and re-initializes player's attributes.
     */
    public void reset() {
        //this.position = new Position(0, 0);
        this.bombs = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.curses = new ArrayList<>();
        this.powerups = new ArrayList<>();
        this.alive = true;
        this.bombRange = STANDARD_BOMB_RANGE;
        this.maxBombNumber = STANDARD_BOMB_NUMBER;
        this.isInvincible = false;
        this.isGhost = false;
        this.isDetonatorOn = false;
        this.maxNumberOfObstacles = STANDARD_OBSTACLE_NUMBER;
        this.noBombCurse = false;
        this.placeBombImmediatelyCurse = false;
    }

    /*
     * Removes the player from the game map by setting their current cell to
     * empty. Also moves the player's position off the grid.
     */
    private void removePlayerFromMap() {
        Position playerPosition = this.position;
        space[playerPosition.getY()][playerPosition.getX()] = new Empty(playerPosition);
        this.position = new Position(-10, -10);
    }

    /**
     * Actually places a bomb
     */
    private void actuallyPlaceBomb() {
        if (isAlive() && this.getBombs().size() < maxBombNumber && !this.noBombCurse) {
            Bomb bomb = new Bomb(this.position, this, this.space);
            this.bombs.add(bomb);
            System.out.println("I've placed a bomb");
        }
    }
}
