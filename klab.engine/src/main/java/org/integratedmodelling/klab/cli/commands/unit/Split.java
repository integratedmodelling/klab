package org.integratedmodelling.klab.cli.commands.unit;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Split a unit into the non-extentual and the extentual part corresponding to a
 * passed extent. .
 * 
 * @author Ferd
 *
 */
public class Split implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String ret = "";

		Unit unit = null;
		
		int i = 0;
		ExtentDimension dim = null;
		for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
			if (i == 0) {
				unit = Unit.create(urn.toString());
			} else {
				dim = ExtentDimension.valueOf(urn.toString());
			}
			i++;
		}

		Pair<IUnit, IUnit> split = unit.splitExtent(dim);

		ret += "Decontextualized unit: " + split.getFirst() + "\n"; 
		ret += "Distribution unit:     " + split.getSecond(); 

		return ret;
	}

}
