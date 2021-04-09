package org.integratedmodelling.klab.clitool.console.commands.unit;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.mediation.IUnit.UnitContextualization;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;

/**
 * Print all the possible contextualizations of a unit into a geometry, possibly
 * after constraining one or more dimensions to extensive or intensive.
 * 
 * @author Ferd
 *
 */
public class Contextualize implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {

        String ret = "";

        IUnit unit = null;
        Geometry geometry = null;
        Map<ExtentDimension, ExtentDistribution> forcings = new HashMap<>();

        int i = 0;
        ExtentDimension dim = null;
        for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
            if (i == 0) {
                unit = Unit.create(urn.toString());
            } else if (i == 1) {
                geometry = Geometry.create(urn.toString());
            } else {
                if (dim == null) {
                    dim = ExtentDimension.valueOf(urn.toString());
                } else {
                    forcings.put(dim, ExtentDistribution.valueOf(urn.toString()));
                    dim = null;
                }
            }
            i++;
        }

        UnitContextualization contextualization = Units.INSTANCE.getContextualization(unit, geometry, forcings);
        
        ret += "Chosen unit: " + contextualization.getChosenUnit();
        ret += "\nCandidates: ";
        for (IUnit candidate : contextualization.getCandidateUnits()) {
            ret += "\n" + candidate.getAggregatedDimensions() + "\t" + candidate;
        }
        

        return ret;
    }

}
