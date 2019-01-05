package mods.eln.sim.mna.component;

import mods.eln.common.Units;
import mods.eln.sim.mna.SubSystem;
import mods.eln.sim.mna.iface.ISubSystemProcessI;
import mods.eln.sim.mna.state.CurrentState;
import mods.eln.sim.mna.state.State;

public class VoltageSource extends Bipole implements ISubSystemProcessI {

    // voltage of the source
    double u = 0;
    // ???
    private CurrentState currentState = new CurrentState();

    public VoltageSource(String name) {
        this.name = name;
    }

    public VoltageSource(String name, State aPin, State bPin) {
        super(aPin, bPin);
        this.name = name;
    }

    public VoltageSource setU(double u) {
        this.u = u;
        return this;
    }

    public CurrentState getCurrentState() {
        return currentState;
    }

    public double getP() {
        return getU() * getI();
    }

    public double getU() {
        return u;
    }

    public double getI() {
        return -getCurrentState().state;
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

    @Override
    public void applyTo(SubSystem s) {
        s.addToA(aPin, getCurrentState(), 1.0);
        s.addToA(bPin, getCurrentState(), -1.0);
        s.addToA(getCurrentState(), aPin, 1.0);
        s.addToA(getCurrentState(), bPin, -1.0);
    }

    @Override
    public void simProcessI(SubSystem s) {
        s.addToI(getCurrentState(), u);
    }

    @Override
    public double getCurrent() {
        return -getCurrentState().state;
    }

    public String toString() {
        if (bPin != null) {
            return "[" + aPin + " V(" + Units.volts(u) + ") " + bPin + "]";
        }else{
            return "[" + aPin + " V(" + Units.volts(u) + ")]";
        }
    }
}
