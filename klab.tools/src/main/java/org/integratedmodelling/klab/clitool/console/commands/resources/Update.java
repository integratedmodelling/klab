package org.integratedmodelling.klab.clitool.console.commands.resources;

import java.util.ArrayList;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.Utils;

public class Update implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		int i = 0;
		IResource resource = null;
		boolean setMode = false;
		java.util.List<String> args = new ArrayList<>();
		String log = "";
		
		for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
			
			if (i == 0) {
				resource = Resources.INSTANCE.resolveResource(urn.toString());
				if (resource == null) {
					throw new KlabResourceNotFoundException("resource " + urn + " not found: first argument must be a valid resource URN");
				}
			} else if (i == 2 && urn.toString().equals("set")) {
				setMode = true;
			} else {
				args.add(urn.toString());
			}
			i++;
		}

		if (args.size() == 0) {
			session.getMonitor().warn("no arguments given: no changes saved");
			return null;
		}
		
		if (setMode) {
			
			String key = args.get(0);
			args.remove(0);
			if (args.size() > 0) {
				String val = StringUtils.joinCollection(args, ' ');
				resource.getParameters().put(key, Utils.asPOD(val));
				log += (log.isEmpty() ? "" : "\n") + "   Set " + key + " to '" + val + "'";
				// TODO add notification to resource history
			} else {
				resource.getParameters().remove(key);
				log += (log.isEmpty() ? "" : "\n") + "   Removed parameter " + key;
				// TODO add notification to resource history
			}
			
		} else {
			
			for (String kv : args) {
				String[] kvp = kv.split("\\=");
				if (kvp.length != 2) {
					throw new KlabValidationException("arguments following the URN must be in the form key=value");
				}
				Object value = Utils.asPOD(kvp[1]);
				resource.getParameters().put(kvp[0], value);
				log += (log.isEmpty() ? "" : "\n") + "   Set " + kvp[0] + " to '" + value + "'";
				// TODO add notification to resource history
			}
		}
		
		Resources.INSTANCE.getLocalResourceCatalog().put(resource.getUrn(), resource);
		
		return log;
	}
}
