/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.monsters;

import blitzstrike.model.Cell;
import blitzstrike.model.Direction;
import blitzstrike.model.Game;
import blitzstrike.model.Box;
import blitzstrike.model.Empty;
import blitzstrike.model.Wall;
import static blitzstrike.model.Game.MAP_SIZE;
import blitzstrike.model.Position;
import static blitzstrike.model.monsters.Monster.STANDARD_SPEED;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ebazali
 */
public class Monster2 extends Monster {

    public Monster2(Position position, Cell[][] space, Game game) {
        super(position, space, game);
        this.speed = STANDARD_SPEED * 0.8;
        this.currentDirection = Direction.STILL;
        this.monsterType = MonsterType.MONSTER2;
    }

    @Override
    public void move() {
        System.out.println("Monster 2 is moving");
        if (this.currentDirection == Direction.STILL) {
            settleCurrentDirection();
            moveWithCurrentDirection();
        } else {
            Position nextPosition = position.translate(currentDirection);
            if (nextPosition.getY() <= 0 || nextPosition.getX() <= 0 || nextPosition.getY() >= MAP_SIZE - 1 || nextPosition.getX() >= MAP_SIZE - 1) {
                settleCurrentDirection();
                moveWithCurrentDirection();
            } else {
                if (space[this.position.getY()][this.position.getX()] instanceof Box || space[this.position.getY()][this.position.getX()] instanceof Wall) {
                    if (space[nextPosition.getY()][nextPosition.getX()] instanceof Empty) {
                        this.position = nextPosition;
                        settleCurrentDirection();
                    } else {
                        moveWithCurrentDirection();
                    }
                } else {
                    moveWithCurrentDirection();
                }
            }
        }
    }
}
