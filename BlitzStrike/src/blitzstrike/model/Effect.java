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
    public boolean isEmpty(){
        return false;
    }
    
    public void applyEffect(Player player){
        
    }
    
//    static public Effect getRandomEffect(){
//        Random rand = new Random();
//        int randomNumber = rand.nextInt(6);
//        switch (randomNumber){
//            case 0:
//                return new BombIncrease();
//            case 1:
//                return new BlastRange();
//            default:
//                return new Empty();
//        }
//    }
}

class EmptyEffect extends Effect {
    @Override
    public boolean isEmpty(){
        return true;
    }
}
