package org.integratedmodelling.klab.cli.commands;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;

public class Test implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

	    String ret = "";

	    List<Object> args = call.getParameters().get("arguments", List.class);
	    
        IGeometry geometry = Geometry.create("T1(23)S2(200,100)");
        IUnit unit = Unit.create(args.get(1).toString());
        IUnit contextualized = unit.contextualize(Observables.INSTANCE
                .declare("earth:PrecipitationVolume in m^3"/* + args.get(0) */), geometry);

        ret += ((Unit)contextualized).dumpStrategy() + "\n";
        
        return ret;
	    
	}

}
