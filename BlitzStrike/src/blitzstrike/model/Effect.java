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

    protected Effect() {
    }

    ;

    public static Effect getInstance() {
        if (instance == null) {
            instance = new Effect();
        }
        return instance;
    }

    public boolean isEmpty() {
        return false;
    }

    public void applyEffect(Player player) {

    }

    static public Effect getRandomEffect() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(6);
        switch (randomNumber) {
            case 0:
                return BombIncrease.getInstance();
            case 1:
                return BlastRange.getInstance();
            default:
                return EmptyEffect.getInstance();
        }
    }
}
