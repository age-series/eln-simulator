package mods.eln.sim.mna.component;

import mods.eln.sim.mna.SubSystem;
import mods.eln.sim.mna.iface.ISubSystemProcessI;
import mods.eln.sim.mna.state.CurrentState;
import mods.eln.sim.mna.state.State;

public class Inductor extends Bipole implements ISubSystemProcessI {

    // l: Inductance, in Henry's
    private double l = 0;
    // ldt: Inductace delta time
    double ldt;
    // currentState: something that handles the current
    private CurrentState currentState = new CurrentState();

    public Inductor() {}

    /**
     * Inductor: Basic inductor with default of 0
     *
     * @param aPin pin of the inductor
     * @param bPin pin of the inductor
     */
    public Inductor(State aPin, State bPin) {
        super(aPin, bPin);
    }

    /**
     * Inductor: Basic inductor with default of 0
     *
     * @param name name of the inductor
     * @param aPin pin of the inductor
     * @param bPin pin of the inductor
     */
    public Inductor(String name, State aPin, State bPin) {
        super(name, aPin, bPin);
    }

    /**
     * Inductor: Basic inductor
     *
     * @param l inductance of the inductor, in Henry's
     * @param aPin pin of the inductor
     * @param bPin pin of the inductor
     */
    public Inductor(double l, State aPin, State bPin) {
        super(aPin, bPin);
        setL(l);
    }

    /**
     * Inductor: Basic inductor
     *
     * @param name name of the inductor
     * @param l inductance of the inductor, in Henry's
     * @param aPin pin of the inductor
     * @param bPin pin of the inductor
     */
    public Inductor(String name, double l, State aPin, State bPin) {
        super(name, aPin, bPin);
        setL(l);
    }

    /**
     * getL: Get the inductance of the inductor
     *
     * @return inductance, in Henry's
     */
    public double getL() {
        return l;
    }

    /**
     * setL: Set the inductance of the inductor
     * @param l inductance, in Henry's
     */
    public void setL(double l) {
        this.l = l;
        dirty();
    }

    /**
     * getE: Get the energy stored in the inductor
     *
     * @return energy in the inductor, in Joules
     */
    public double getE() {
        final double i = getCurrent();
        return i * i * l / 2;
    }

    public CurrentState getCurrentState() {
        return currentState;
    }

    public void resetStates() {
        currentState.state = 0;
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

    /**
     * getCurrent: Get the current across the inductor
     *
     * @return current in amps
     */
    @Override
    public double getCurrent() {
        return currentState.state;
    }
}
