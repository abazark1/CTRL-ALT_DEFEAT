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

    protected double speed;
    protected boolean alive;
    protected Position position;
    protected Cell[][] space = new Cell[MAP_SIZE][MAP_SIZE];
    protected Direction currentDirection;
    private Game game;
    
    public MonsterType monsterType;


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

    
}
