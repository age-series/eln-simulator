package mods.eln.sim.mna.component;

import mods.eln.sim.mna.SubSystem;
import mods.eln.sim.mna.misc.ISubSystemProcessI;
import mods.eln.sim.mna.state.CurrentState;
import mods.eln.sim.mna.state.State;

public class Inductor extends Bipole implements ISubSystemProcessI {

    String name;

    private double l = 0;
    double ldt;

    private CurrentState currentState = new CurrentState();

    public Inductor(String name) {
        this.name = name;
    }

    public Inductor(String name, State aPin, State bPin) {
        super(aPin, bPin);
        this.name = name;
    }

    @Override
    public double getCurrent() {
        return currentState.state;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
        dirty();
    }

    public double getE() {
        final double i = getCurrent();
        return i * i * l / 2;
    }

    @Override
    public void applyTo(SubSystem s) {
        ldt = -l / s.getDt();

        s.addToA(aPin, currentState, 1);
        s.addToA(bPin, currentState, -1);
        s.addToA(currentState, aPin, 1);
        s.addToA(currentState, bPin, -1);
        s.addToA(currentState, currentState, ldt);
    }

    @Override
    public void simProcessI(SubSystem s) {
        s.addToI(currentState, ldt * currentState.state);
    }

    @Override
    public void quitSubSystem() {
        subSystem.states.remove(getCurrentState());
        subSystem.removeProcess(this);
        super.quitSubSystem();
    }

    @Override
    public void addedTo(SubSystem s) {
        super.addedTo(s);
        s.addState(getCurrentState());
        s.addProcess(this);
    }

    public CurrentState getCurrentState() {
        return currentState;
    }

    public void resetStates() {
        currentState.state = 0;
    }
}
