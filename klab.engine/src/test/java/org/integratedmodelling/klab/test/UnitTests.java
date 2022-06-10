package org.integratedmodelling.klab.test;

import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.junit.Test;

public class UnitTests {

    @Test
    public void test() {

        IGeometry geometry = Geometry.create("T1(23)S2(200,100)");
        IUnit unit = Unit.create("mm/day");
        IUnit contextualized = unit.contextualize(Observables.INSTANCE.declare("earth:PrecipitationVolume in mm"), geometry);
        
    }

}
