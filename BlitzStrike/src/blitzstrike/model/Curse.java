/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author aliia
 */
public class Curse extends Effect{
    private int duration;
    private static Curse instance;

    
    public Curse(int duration){
        this.duration = duration;
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public void removeEffect(Player p){
        
    }
    
    public static Curse getInstance(int duration) {
        if (instance == null) {
            instance = new Curse(duration);
        }
        return instance;
    }
}