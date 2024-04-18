/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects;

import blitzstrike.model.effects.powerups.BlastRange;
import blitzstrike.model.effects.powerups.BombIncrease;
import blitzstrike.model.Player;
import blitzstrike.model.effects.curses.BombRangeCurse;
import blitzstrike.model.effects.curses.ImmediateBombCurse;
import blitzstrike.model.effects.curses.NoBombCurse;
import blitzstrike.model.effects.curses.SpeedCurse;
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
        this.isEmpty = false;
        this.isPowerup = false;
    }

    public boolean isEmpty() {
        return this.isEmpty;
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

    public void setIsCurse() {
        this.isCurse = true;
    }

    public abstract void applyEffect(Player player);

    static public Effect getRandomEffect() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(7);
        Effect effect;
        switch (randomNumber) {
            case 0:
                System.out.println("I dropped Bomb Increase randomly");
                effect = new BombIncrease();
                effect.setEverythingFalse();
                effect.setIsPowerup();
                return effect;
            case 1:
                System.out.println("I dropped Blast range randomly");
                effect = new BlastRange();
                effect.setEverythingFalse();
                effect.setIsPowerup();
                return effect;
            case 2:
                System.out.println("I dropped Bomb Range CURSE");
                effect = new BombRangeCurse();
                effect.setEverythingFalse();
                effect.setIsCurse();
                return effect;
            case 3:
                System.out.println("I dropped Immediate Bomb CURSE");
                effect = new ImmediateBombCurse();
                effect.setEverythingFalse();
                effect.setIsCurse();
                return effect;
            case 4:
                System.out.println("I dropped No Bomb CURSE");
                effect = new NoBombCurse();
                effect.setEverythingFalse();
                effect.setIsCurse();
                return effect;
            case 5:
                System.out.println("I dropped Speed CURSE");
                effect = new SpeedCurse();
                effect.setEverythingFalse();
                effect.setIsCurse();
                return effect;
            default:
                System.out.println("I dropped Empty effect randomly");
                effect = new EmptyEffect();
                effect.setEverythingFalse();
                effect.setIsEmpty();
                return effect;
        }
    }

    private void setEverythingFalse() {
        this.isCurse = false;
        this.isEmpty = false;
        this.isPowerup = false;
    }
}
