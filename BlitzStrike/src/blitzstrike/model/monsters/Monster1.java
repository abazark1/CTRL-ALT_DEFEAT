/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.monsters;

import blitzstrike.model.Cell;
import blitzstrike.model.Direction;
import blitzstrike.model.Game;
import blitzstrike.model.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ebazali
 */
public class Monster1 extends Monster {

    private int counterUntilChangeDirection;
    private final int LIMIT_TO_CHANGE_DIRECTION_RANDOMLY = 3;

    public Monster1(Position position, Cell[][] space, Game game) {
        super(position, space, game);
        this.counterUntilChangeDirection = 0;
        this.monsterType = MonsterType.BASIC;

    }

    @Override
    public void move() {
        System.out.println("Monster 1 is moving");
        if (this.currentDirection == Direction.STILL) {
            settleCurrentDirectionRandomly();
            moveWithCurrentDirection();
        } else {
            Position newPosition = this.position.translate(currentDirection);
            if (isValidPosition(newPosition)) {
                this.position = newPosition;
            } else {
                this.counterUntilChangeDirection++;
                if (this.counterUntilChangeDirection >= LIMIT_TO_CHANGE_DIRECTION_RANDOMLY) {
                    settleCurrentDirectionRandomly();
                    moveWithCurrentDirection();
                    this.counterUntilChangeDirection = 0;
                } else {
                    settleCurrentDirection();
                    moveWithCurrentDirection();
                }
            }
        }
    }
}
