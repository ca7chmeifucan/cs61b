package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

public class Clorus extends Creature {
    private int r = 34;
    private int g = 0;
    private int b = 231;

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        return color(r,g,b);
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public void move() {
        energy -= 0.03;
    }

    public void stay() {
        energy -= 0.01;
    }

    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Random generator = new Random();
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        boolean anyPlip = false;
        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.add(d);
            }
            if (neighbors.get(d).name().equals("plip")) {
                anyPlip = true;
                plipNeighbors.add(d);
            }
        }

        //case 1
        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        //case 2
        if (anyPlip) {
            int len1 = plipNeighbors.size();
            int randomindex1 = generator.nextInt(len1);
            return new Action(Action.ActionType.ATTACK, plipNeighbors.toArray(new Direction[len1])[randomindex1]);
        }

        int len2 = emptyNeighbors.size();
        int randomindex2 = generator.nextInt(len2);
        //case 3
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, emptyNeighbors.toArray(new Direction[len2])[randomindex2]);
        }

        return new Action(Action.ActionType.MOVE, emptyNeighbors.toArray(new Direction[len2])[randomindex2]);
    }
}
