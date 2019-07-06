package org.integratedmodelling.klab.clitool.console.commands.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.utils.MapUtils;
import org.integratedmodelling.klab.utils.Pair;

import com.google.common.collect.Sets;

/**
 * Print all the possible contextualizations of a unit into a geometry, possibly
 * after constraining one or more dimensions to extensive or intensive.
 * .
 * @author Ferd
 *
 */
public class Contextualize implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws Exception {

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

        /*
         * produce all possible base units: gather the extents in the geometry
         */
        Set<ExtentDimension> aggregatable = new HashSet<>();
        for (IGeometry.Dimension dimension : geometry.getDimensions()) {
            aggregatable.add(dimension.getExtentDimension());
        }

        ret += "In geometry " + geometry + ", the unit can be any of:";
        IUnit fullyContextualized = Units.INSTANCE.contextualize(unit, aggregatable);
        ret += "\n   The fully contextualized unit: " + fullyContextualized;

        List<Pair<Set<ExtentDimension>, IUnit>> potentialUnits = new ArrayList<>();
        for (Set<ExtentDimension> set : Sets.powerSet(aggregatable)) {
            IUnit aggregated = Units.INSTANCE.removeExtents(fullyContextualized, set);
            potentialUnits.add(new Pair<>(set, aggregated));
            ret += ("\n   After aggregating in " + set + " -> " + aggregated);
        }

        if (forcings.isEmpty()) {
            ret += "\n Chosen unit for no forcings:\n*  " + fullyContextualized;
        } else {

            Set<ExtentDimension> whitelist = new HashSet<>();
            Set<ExtentDimension> blacklist = new HashSet<>();
            for (ExtentDimension d : forcings.keySet()) {
                if (forcings.get(d) == ExtentDistribution.EXTENSIVE) {
                    whitelist.add(d);
                } else {
                    blacklist.add(d);
                }
            }

            for (Pair<Set<ExtentDimension>, IUnit> punit : potentialUnits) {
                if (Sets.intersection(punit.getFirst(), whitelist).size() == whitelist.size()
                        && Sets.intersection(punit.getFirst(), blacklist).size() == 0) {
                    ret += "\n Chosen unit for " + MapUtils.toString(forcings) + " forcings:\n*  " + punit.getSecond();
                    break;
                }
            }
        }

        return ret;
    }

}
