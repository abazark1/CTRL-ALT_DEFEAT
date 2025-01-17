/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import blitzstrike.model.effects.Effect;

/**
 *
 * @author medina
 */
public class Box extends Cell {

    private Effect effect;

    public Box(Position position) {
        super(position);
        this.destroyable = true;
        this.mineable = false;
        this.walkable = false;
    }

    public void getDestroyed() {
        if (!this.destroyed) {
            System.out.println("I'm destroyed");
            this.destroyed = true;
            this.walkable = true;
            this.destroyable = false;
            this.mineable = true;
            this.effect = Effect.getRandomEffect();
        }
    }

    public Effect getEffect() {
        return this.effect;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public boolean isDestoyable() {
        return this.destroyable;
    }

    public Position getPosition() {
        return this.position;
    }
}
