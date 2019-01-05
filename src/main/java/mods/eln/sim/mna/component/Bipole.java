package mods.eln.sim.mna.component;

import mods.eln.sim.mna.state.State;

public abstract class Bipole extends Component {

    /**
     * aPin, bPin: Pins on the resistor
     */
    public State aPin, bPin;

    public Bipole() {
    }

    /**
     * Bipole
     *
     * @param aPin pin
     * @param bPin pin
     */
    public Bipole(State aPin, State bPin) {
        connectTo(aPin, bPin);
    }

    /**
     * Bipole
     *
     * @param name name of device
     * @param aPin pin
     * @param bPin pin
     */
    public Bipole(String name, State aPin, State bPin) {
        super(name);
        connectTo(aPin, bPin);
    }

    /**
     * connectTo: Connects the device to the pins after disconnecting existing connections.
     *
     * @param aPin pin
     * @param bPin pin
     * @return this Bipole instnace
     */
    public Bipole connectTo(State aPin, State bPin) {
        breakConnection();

        this.aPin = aPin;
        this.bPin = bPin;

        //TODO: Reject null pins?

        if (aPin != null) aPin.add(this);
        if (bPin != null) bPin.add(this);
        return this;
    }

    /**
     * connectGhostTo: Connects the device to the pins after disconnecting existing connections.
     * This is different from connectTo because it doesn't connect the pins on the actual solver, just in this class.
     *
     * @param aPin pin
     * @param bPin pin
     * @return this Bipole object
     */
    public Bipole connectGhostTo(State aPin, State bPin) {
        breakConnection();

        this.aPin = aPin;
        this.bPin = bPin;
        return this;
    }

    /**
     * breakConnection: Breaks connections with other devices and State instances, if State instances are not null.
     */
    @Override
    public void breakConnection() {
        if (aPin != null) aPin.remove(this);
        if (bPin != null) bPin.remove(this);
    }

    /**
     * getConnectedStates: Get the State instances we're associated with.
     *
     * @return State[aPin, bPin]
     */
    @Override
    public State[] getConnectedStates() {
        return new State[]{aPin, bPin};
    }

    public abstract double getCurrent();

    /**
     * getU: Get voltage across the pins of this device
     *
     * @return voltage between pins, in volts
     */
    public double getU() {
        return (aPin == null ? 0 : aPin.state) - (bPin == null ? 0 : bPin.state);
    }

    /**
     * See getU
     *
     * @return voltage between pins, in volts
     */
    public double getBipoleU() {
        return getU();
    }

    public String toString() {
        return "[" + aPin + " B " + bPin + "]";
    }
}
