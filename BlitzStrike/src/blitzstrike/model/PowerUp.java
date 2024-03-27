/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author medina
 */
public class PowerUp extends Effect{
    
    protected PowerUp(){}
    private static PowerUp powerUpInstance = null;
    
    public static PowerUp getInstance(){
        if (powerUpInstance == null){
            powerUpInstance = new PowerUp();
        }
        return powerUpInstance;
    }
    
    public void removeEffect(Player player){
        
    }
}
