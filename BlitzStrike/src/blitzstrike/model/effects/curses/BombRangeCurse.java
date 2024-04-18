/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model.effects.curses;

import blitzstrike.model.Player;
import java.time.LocalTime;

/**
 *
 * @author medina
 */
public class BombRangeCurse extends Curse {

    // I changed from private to public - aliia
    public BombRangeCurse() {
        super();
    }

    @Override
    public void applyEffect(Player p) {
        activateBombRangeCurse(p);
        p.addCurse(this);
    }

    /**
     * Checks the time passed and sets isTerminated to true if the time has reached the duration of the effect, false, otherwise
     * @param p player to whom the effect belongs
     */
    @Override
    public void terminateEffect(Player p) {
        if (this.startingTime != null) {
            long timePassed = LocalTime.now().toSecondOfDay() - this.startingTime.toSecondOfDay();
            if (timePassed >= this.getDuration()) {
                p.resetBombRange();
                this.isTerminated = true;
            }
        }
    }

    /**
     * Activates the bomb range curse
     * @param player to whom the bomb range curse should get applied to
     */
    private void activateBombRangeCurse(Player p) {
        System.out.println("Activated bomb range curse");
        p.setBombRangeCurse(1);
        this.startingTime = LocalTime.now();
//        p.setBombRangeCurseStartTime();
    }
}
