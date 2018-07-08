package org.integratedmodelling.klab.clitool.console.commands.resources;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.scale.Scale;

public class Info implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {
		String ret = "";
		for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
			ret += resourceInfo(Resources.INSTANCE.getLocalResourceCatalog().get(urn)) + "\n";
		}
		return ret;
	}

	private String resourceInfo(IResource resource) {

		String ret = "";

		/*
		 * Build a sample observation for the resource scale
		 */
		Scale scale = Scale.create(resource.getGeometry());
		if (!scale.isEmpty()) {
			ret += "observe " + (scale.getSpace() != null ? "earth:Region" : "im:Thing") + " named test\n   over";
			List<IServiceCall> scaleSpecs = scale.getKimSpecification();
			for (int i = 0; i < scaleSpecs.size(); i++) {
				ret += " " + scaleSpecs.get(i).getSourceCode()
						+ ((i < scaleSpecs.size() - 1) ? (",\n" + "      ") : ";");
			}
		}
		
		/*
		 * TODO more
		 */

		return ret;
	}
}
