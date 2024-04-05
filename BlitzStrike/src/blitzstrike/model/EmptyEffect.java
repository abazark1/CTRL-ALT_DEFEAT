/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author medina
 */
public class EmptyEffect extends Effect {

    protected EmptyEffect() {
        super();
        this.isEmpty = true;
    }
    
    @Override
    public void applyEffect(Player player){
        if (this.isEmpty()){
            System.out.println(player.getName() + " has just got the empty effect!");
        }
    }
}
