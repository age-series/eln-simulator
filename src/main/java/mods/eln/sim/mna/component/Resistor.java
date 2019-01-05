package mods.eln.sim.mna.component;

import mods.eln.common.Units;
import mods.eln.sim.mna.SubSystem;
import mods.eln.sim.mna.MnaConst;
import mods.eln.sim.mna.state.State;

/**
 * Resistor: A two terminal device with resistance
 */
public class Resistor extends Bipole {
    // r: the resistance of the resistor. rInv: the inverse of the resistance of the resistor.
    private double r = MnaConst.HIGH_IMPEDANCE, rInv = 1 / MnaConst.HIGH_IMPEDANCE;

    public Resistor() {}

    /**
     * Basic Resistor with default of HIGH_IMPEDANCE.
     *
     * @param aPin pin of the resistor
     * @param bPin pin of the resistor
     */
    public Resistor(State aPin, State bPin) {
        super(aPin, bPin);
    }

    /**
     * Basic resistor
     *
     * @param resistance resistance of the resistor, in ohms
     * @param aPin pin of the resistor
     * @param bPin pin of the resistor
     */
    public Resistor(double resistance, State aPin, State bPin) {
        super(aPin, bPin);
        setR(resistance);
    }

    /**
     * Basic Resistor with default of HIGH_IMPEDANCE.
     *
     * @param name name of the resistor
     * @param aPin pin of the resistor
     * @param bPin pin of the resistor
     */
    public Resistor(String name, State aPin, State bPin) {
        super(name, aPin, bPin);
    }

    public Resistor(String name, double resistance, State aPin, State bPin) {
        super(name, aPin, bPin);
        setR(resistance);
    }

    public double getRInv() {
        return rInv;
    }

    /**
     * getR: Get the resistance value of the resistor, in ohms
     *
     * @return resistance of the resistor, in ohms
     */
    public double getR() {
        return r;
    }

    /**
     * getI: Get the amperage drawn through the resistor, in amps
     *
     * @return amps drawn through the resistor
     */
    public double getI() {
        return getCurrent();
    }

    /**
     * getP: Get the power across the resistor, in watts
     *
     * @return power across resistor
     */
    public double getP() {
        return getU() * getCurrent();
    }

    /**
     * getU: Get the voltage drop across the resistor, in volts
     *
     * @return voltage drop across resistor
     */
    public double getU() {
        return (aPin == null ? 0 : aPin.state) - (bPin == null ? 0 : bPin.state);
    }

    /**
     * setR: set the resistance across the resistor, in ohms
     *
     * @param r resistance, in ohms
     * @return this Resistor instance
     */
    public Resistor setR(double r) {
        if (this.r != r) {
            this.r = r;
            this.rInv = 1 / r;
            dirty();
        }
        return this;
    }

    /**
     * highImpedance: Set the resistance to HIGH_IMPEDANCE
     *
     * @return this Resistor instance
     */
    public Resistor highImpedance() {
        setR(MnaConst.HIGH_IMPEDANCE);
        return this;
    }

    /**
     * ultraImpedance: Set the resistance to ULTRA_IMPEDANCE
     *
     * @return this Resistor instance
     */
    public Resistor ultraImpedance() {
        setR(MnaConst.ULTRA_IMPEDANCE);
        return this;
    }

    /**
     * pullDown: set the resistance to PULL_DOWN
     *
     * @return this Resistor instance
     */
    public Resistor pullDown() {
        setR(MnaConst.PULL_DOWN);
        return this;
    }

    boolean canBridge() {
        return false;
    }

    @Override
    public void applyTo(SubSystem s) {
        s.addToA(aPin, aPin, rInv);
        s.addToA(aPin, bPin, -rInv);
        s.addToA(bPin, bPin, rInv);
        s.addToA(bPin, aPin, -rInv);
    }

    @Override
    public double getCurrent() {
        return getU() * rInv;
    }

    @Override
    public String toString() {
        return "[" + aPin + " R(" + Units.ohms(r) + ") " + bPin + "]";
    }
}
