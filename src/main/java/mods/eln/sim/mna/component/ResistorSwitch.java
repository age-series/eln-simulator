package mods.eln.sim.mna.component;

import mods.eln.sim.mna.misc.MnaConst;
import mods.eln.sim.mna.state.State;

public class ResistorSwitch extends Resistor {

    boolean ultraImpedance = false;
    String name;

    boolean state = false;

    protected double baseR = 1;

    public ResistorSwitch(String name, State aPin, State bPin) {
        super(aPin, bPin);
        this.name = name;
    }

    public void setState(boolean state) {
        this.state = state;
        setR(baseR);
    }

    @Override
    public Resistor setR(double r) {
        baseR = r;
        return super.setR(state ? r : (ultraImpedance ? MnaConst.ultraImpedance : MnaConst.highImpedance));
    }

    public boolean getState() {
        return state;
    }

    public void mustUseUltraImpedance() {
        ultraImpedance = true;
    }
}
