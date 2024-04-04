/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

/**
 *
 * @author medina
 */

//basic power up

public class BlastRange extends PowerUp{
    
    @Override
    public void applyEffect(Player player){
        increaseRange(player);
    }
    
    
    private void increaseRange(Player player){
        int range =player.getBlastRange();
        range++;
        player.setBlastRange(range);
    }
}
