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
public abstract class Effect {

    private static Effect instance;
    protected boolean isEmpty;
    protected boolean isCurse;
    protected boolean isPowerup;

    protected Effect() {
        this.isCurse = false;
        this.isCurse = false;
        this.isPowerup = false;
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

    public abstract void applyEffect(Player player);

    static public Effect getRandomEffect() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(3);
        Effect effect;
        switch (randomNumber) {
            case 0:
                System.out.println("I dropped Bomb Increase randomly");
                //effect = BombIncrease.getInstance();
                effect = new BombIncrease();
                effect.setEverythingFalse();
                effect.setIsPowerup();
                return effect;
            case 1:
                System.out.println("I dropped Blast range randomly");
                //effect = BlastRange.getInstance();
                effect = new BlastRange();
                effect.setEverythingFalse();
                effect.setIsPowerup();
                return effect;
            default:
                System.out.println("I dropped Empty effect randomly");
                //effect = EmptyEffect.getInstance();
                effect = new EmptyEffect();
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
