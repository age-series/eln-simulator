package mods.eln.sim.mna.component;

import mods.eln.sim.mna.MnaConst;
import mods.eln.sim.mna.state.State;

public class ResistorSwitch extends Resistor {

    // whether to use HIGH_IMPEDANCE (default) or ULTRA_IMPEDANCE (if this is true)
    boolean ultraImpedance = false;
    // whether the switch is on or off
    boolean state = false;
    // the base resistance for when the switch is on. You want this to be the resistance of the cable most likely?
    protected double baseR = 1;

    /**
     * ResistorSwitch: A switch implementation using resistance
     *
     * @param aPin pin of the switch
     * @param bPin pin of the switch
     */
    public ResistorSwitch(State aPin, State bPin) {
        super(aPin, bPin);
    }

    /**
     * ResistorSwitch: A switch implementation using resistance
     *
     * @param name name of the switch
     * @param aPin pin of the switch
     * @param bPin pin of the switch
     */
    public ResistorSwitch(String name, State aPin, State bPin) {
        super(name, aPin, bPin);
    }

    /**
     * ResistorSwitch: A switch implementation using resistance
     *
     * @param baseR base resistance when passing as closed (cable resistance)
     * @param aPin pin of the switch
     * @param bPin pin of the switch
     */
    public ResistorSwitch(double baseR, State aPin, State bPin) {
        super(aPin, bPin);
        this.baseR = baseR;
    }

    /**
     * ResistorSwitch: A switch implementation using resistance
     *
     * @param name name of the switch
     * @param baseR base resistance when passing as closed (cable resistance)
     * @param aPin pin of the switch
     * @param bPin pin of the switch
     */
    public ResistorSwitch(String name, double baseR, State aPin, State bPin) {
        super(name, aPin, bPin);
        this.baseR = baseR;
    }

    /**
     * setState: Set the state of the switch (off or on)
     *
     * @param state true=on, false=off
     */
    public void setState(boolean state) {
        this.state = state;
        setR(baseR);
    }

    @Override
    public Resistor setR(double r) {
        baseR = r;
        return super.setR(state ? r : (ultraImpedance ? MnaConst.ULTRA_IMPEDANCE : MnaConst.HIGH_IMPEDANCE));
    }

    /**
     * getState: Get the state of the switch (off or on)
     *
     * @return true=on, false=off
     */
    public boolean getState() {
        return state;
    }

    /**
     * Calling this will cause the switch to use ULTRA_IMPEDANCE instead of HIGH_IMPEDANCE
     */
    public void mustUseUltraImpedance() {
        ultraImpedance = true;
    }
}
