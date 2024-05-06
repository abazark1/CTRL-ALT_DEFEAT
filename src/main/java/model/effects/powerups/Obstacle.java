/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.powerups;

import blitzstrike.model.Player;

public class Obstacle extends PowerUp {

    public static final int NUMBER_OF_OBSTACLES = 3;

    /**
     * Applies the obstacle power up to the given player
     *
     * @param player is the given player
     */
    @Override
    public void applyEffect(Player player) {
        System.out.println(player.getName() + " has just got obstacle powerUp!");
        increaseObstacles(player);
        player.addPowerup(this);
        player.setIsObstacleOn(true);
    }

    /**
     * Removes the obstacle power up from the given player
     *
     * @param player is the given player
     */
    @Override
    public void removeEffect(Player player) {

    }

    /**
     * Increases the max number of obstacles to the given player
     *
     * @param player is the given player
     */
    private void increaseObstacles(Player player) {
         
        int maxNum = player.getMaxNumberOfObstacles();
        maxNum = maxNum + NUMBER_OF_OBSTACLES;
        player.setMaxNumberOfObstacles(maxNum);
        System.out.println(maxNum);
    }
}
