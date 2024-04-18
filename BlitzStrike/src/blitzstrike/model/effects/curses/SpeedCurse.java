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
public class SpeedCurse extends Curse {

    public SpeedCurse() {
        super();
        this.duration = 3;
    }

    @Override
    public void applyEffect(Player p) {
        activateSpeedCurse(p);
    }

    @Override
    public void terminateEffect(Player p) {
        long timePassed = LocalTime.now().toSecondOfDay() - p.getSpeedCurseTimer().toSecondOfDay();

        if (timePassed >= this.getDuration()) {
            p.setSpeedCurse(1.0);
            p.setSpeedCurseTimer(null);
        }
    }

    private void activateSpeedCurse(Player p) {
        p.setSpeedCurse(0.8);
        p.setSpeedCurseTimer(LocalTime.now());
    }
}
