/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects;

import blitzstrike.model.Player;

public class EmptyEffect extends Effect {

    /**
     * Protected constructor
     */
    protected EmptyEffect() {
        super();
        this.isEmpty = true;
    }

    /**
     * Applies the empty effect to the given player that is no effect gets
     * applied
     *
     * @param player is the given player to apply effect to
     */
    @Override
    public void applyEffect(Player player) {
        if (this.isEmpty()) {
            System.out.println(player.getName() + " has just got the empty effect!");
        }
    }
}
