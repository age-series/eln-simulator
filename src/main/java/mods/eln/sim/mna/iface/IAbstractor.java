package mods.eln.sim.mna.iface;

import mods.eln.sim.mna.SubSystem;
import mods.eln.sim.mna.component.Component;

public interface IAbstractor {

    void dirty(Component component);

    SubSystem getAbstractorSubSystem();
}
