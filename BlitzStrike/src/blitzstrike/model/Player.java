/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author artur
 */
public class Player {

    private String name;
    private Position position;
    private boolean alive;
    private int gamesWon;
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
    
    private Cell[][] space;

    public Player(String name, Cell[][] space) {
        this.name = name;
        this.space = space;
        // TO BE CHANGED
        this.position = new Position(0, 0);
        this.bombs = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.alive = true;
        this.gamesWon = 0;
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

    public void placeBomb() {
        Bomb bomb = new Bomb(this.position, this);
        this.bombs.add(bomb);
        System.out.println("I've placed a bomb");
    }

    public void placeObstacle() {
        this.obstacles.add(this.position);
        System.out.println("I've placed an obstacle");
    }

    public void movePlayer(Direction direction) {
        this.position = this.position.translate(direction);
    }

    public void getExploded() {
        this.alive = false;
    }

    public boolean isValidPosition(Direction direction) {
        Position newPosition = this.position.translate(direction);
        return this.space[newPosition.getY()][newPosition.getX()].isWalkable();
    }

    public void activateEffect(Effect effect) {
        effect.applyEffect(this);
    }

    public void setImmediateBombCurse(boolean value){
        this.placeBombImmediatelyCurse = value;
    }
    
    public LocalTime getImmediateBombCurseTimer(){
        return this.immediateBombCurseTimer;
    }
    
    public void setImmediateBombCurseTimer(LocalTime time) {
        if (time != null){
            this.immediateBombCurseTimer = time;
        } else {
            this.immediateBombCurseTimer = null;
        }
    }
    
    public void setNoBombCurse(boolean value){
        this.noBombCurse = value;
    }
    
    public LocalTime getNoBombCurseTimer(){
        return this.noBombCurseTimer;
    }
    
    public void setNoBombCurseTimer(LocalTime time){
        if (time != null){
            this.immediateBombCurseTimer = time;
        } else {
            this.immediateBombCurseTimer = null;
        }
    }
    
    public void setBombRangeCurse(int value){
        this.blastRange = value;
    }
    
    public LocalTime getBombRangeCurseTimer(){
        return this.blastRangeDecreaseTimer;
    }
    
    public void setBombRangeCurseTimer(LocalTime time){
        if (time != null){
            this.immediateBombCurseTimer = time;
        } else {
            this.immediateBombCurseTimer = null;
        }
    }
    
    public void setSpeedCurse(double value){
        this.speed = value;
    }
    
    public LocalTime getSpeedCurseTimer(){
        return this.speedDecreaseTimer;
    }
    
    public void setSpeedCurseTimer(LocalTime time){
        if (time != null){
            this.immediateBombCurseTimer = time;
        } else {
            this.immediateBombCurseTimer = null;
        }
    }
}
