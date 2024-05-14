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
import blitzstrike.model.effects.curses.FollowedByMonsters;
import blitzstrike.model.effects.powerups.Detonator;
import blitzstrike.model.effects.powerups.Ghost;
import blitzstrike.model.effects.powerups.Invincibility;
import blitzstrike.model.effects.powerups.Obstacle;
import blitzstrike.model.effects.powerups.RollerSkate;
import java.util.Random;

public abstract class Effect {

    protected boolean isEmpty;
    protected boolean isCurse;
    protected boolean isPowerup;

    /**
     * Protected constructor
     */
    protected Effect() {
        this.isCurse = false;
        this.isEmpty = false;
        this.isPowerup = false;
    }

    /**
     * Returns true if it is an empty effect, otherwise, false
     *
     * @return isEmpty value
     */
    public boolean isEmpty() {
        return this.isEmpty;
    }

    /**
     * Returns true if it is a power up effect, otherwise, false
     *
     * @return isPowerup value
     */
    public boolean isPowerup() {
        return this.isPowerup;
    }

    /**
     * Returns true if it is a curse effect, otherwise, false
     *
     * @return isCurse value
     */
    public boolean isCurse() {
        return this.isCurse;
    }

    /**
     * Sets isEmpty to the given value
     *
     * @param isEmpty is the given value to set to
     */
    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    /**
     * Sets isPowerup to the given value
     *
     * @param isPowerup is the given value to set to
     */
    public void setIsPowerup(boolean isPowerup) {
        this.isPowerup = isPowerup;
    }

    /**
     * Sets isCurse to the given value
     *
     * @param isCurse is the given value to set to
     */
    public void setIsCurse(boolean isCurse) {
        this.isCurse = isCurse;
    }

    /**
     * Applies the effect to the given player
     *
     * @param player is the given player to apply effect to
     */
    public abstract void applyEffect(Player player);

    /**
     * Dynamically generates a random effect
     *
     * @return a random effect
     */
    static public Effect getRandomEffect() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(12);
        Effect effect;
        switch (randomNumber) {
            case 0:
                System.out.println("I dropped Bomb Increase randomly");
                effect = new BombIncrease(false);
                effect.setEverythingFalse();
                effect.setIsPowerup(true);
                return effect;
            case 1:
                System.out.println("I dropped Blast range randomly");
                effect = new BlastRange(false);
                effect.setEverythingFalse();
                effect.setIsPowerup(true);
                return effect;
            case 2:
                System.out.println("I dropped Bomb Range CURSE");
                effect = new BombRangeCurse();
                effect.setEverythingFalse();
                effect.setIsCurse(true);
                return effect;
            case 3:
                System.out.println("I dropped Immediate Bomb CURSE");
                effect = new ImmediateBombCurse();
                effect.setEverythingFalse();
                effect.setIsCurse(true);
                return effect;
            case 4:
                System.out.println("I dropped No Bomb CURSE");
                effect = new NoBombCurse();
                effect.setEverythingFalse();
                effect.setIsCurse(true);
                return effect;
            case 5:
                System.out.println("I dropped Speed CURSE (Followed by Monsters)");
                effect = new FollowedByMonsters();
                effect.setEverythingFalse();
                effect.setIsCurse(true);
                return effect;
            case 6:
                System.out.println("I dropped Roller Skate randomly (Unfollowed by Monsters)");
                effect = new RollerSkate(true);
                effect.setEverythingFalse();
                effect.setIsPowerup(true);
                return effect;
            case 7:
                System.out.println("I dropped Invincibility randomly");
                effect = new Invincibility(true);
                effect.setEverythingFalse();
                effect.setIsPowerup(true);
                return effect;
            case 8:
                System.out.println("I dropped Ghost randomly");
                effect = new Ghost(true);
                effect.setEverythingFalse();
                effect.setIsPowerup(true);
                return effect;

            case 9:
                System.out.println("I dropped Obstacle power up randomly");
                effect = new Obstacle(false);
                effect.setEverythingFalse();
                effect.setIsPowerup(true);
                return effect;

            case 10:
                System.out.println("I dropped detonator power up randomly");
                effect = new Detonator(false);
                effect.setEverythingFalse();
                effect.setIsPowerup(true);
                return effect;
                
            default:
                System.out.println("I dropped Empty effect randomly");
                effect = new EmptyEffect();
                effect.setEverythingFalse();
                effect.setIsEmpty(true);
                return effect;
        }
    }

    /**
     * Sets isCurse, isEmpty, isPowerup fields to false
     */
    private void setEverythingFalse() {
        this.isCurse = false;
        this.isEmpty = false;
        this.isPowerup = false;
    }
}
