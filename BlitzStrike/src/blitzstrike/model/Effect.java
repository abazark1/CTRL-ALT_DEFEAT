/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import java.util.Random;

/**
 *
 * @author aliia
 */
public class Effect {

    private static Effect instance;
    protected boolean isEmpty;
    protected boolean isCurse;
    protected boolean isPowerup;

    protected Effect() {
        this.isCurse = false;
        this.isCurse = false;
        this.isPowerup = false;
    }

    ;

    public static Effect getInstance() {
        if (instance == null) {
            instance = new Effect();
        }
        return instance;
    }

    public boolean isEmpty() {
        return this.isCurse;
    }

    public boolean isPowerup() {
        return this.isPowerup;
    }

    public boolean isCurse() {
        return this.isCurse;
    }

    public void setIsEmpty() {
        this.isEmpty = true;
    }

    public void setIsPowerup() {
        this.isPowerup = true;
    }

    public void applyEffect(Player player) {
        System.out.println(player.getName() + " has just got the empty effect!");
    }

    static public Effect getRandomEffect() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(3);
        Effect effect;
        switch (randomNumber) {
            case 0:
                System.out.println("I dropped Bomb Increase randomly");
                effect = BombIncrease.getInstance();
                effect.setEverythingFalse();
                effect.setIsPowerup();
                return effect;
            case 1:
                System.out.println("I dropped Blast range randomly");
                effect = BlastRange.getInstance();
                effect.setEverythingFalse();
                effect.setIsPowerup();
                return effect;
            default:
                System.out.println("I dropped Empty effect randomly");
                effect = EmptyEffect.getInstance();
                effect.setEverythingFalse();
                effect.setIsEmpty();
                return effect;
        }
    }

    private void setEverythingFalse() {
        this.isCurse = false;
        this.isCurse = false;
        this.isPowerup = false;
    }
}
