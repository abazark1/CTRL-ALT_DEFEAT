/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author aliia
 */
public class Effect {
    public boolean isEmpty(){
        return false;
    }
}

class Empty extends Effect {
    @Override
    public boolean isEmpty(){
        return true;
    }
}
