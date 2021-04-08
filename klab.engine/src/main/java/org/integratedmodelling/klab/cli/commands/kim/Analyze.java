package org.integratedmodelling.klab.cli.commands.kim;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.StringUtils;

public class Analyze implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		String ret = "";
		String expression = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();
		// TODO options
		Descriptor descriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE).describe(expression);
		
		ret += "Identifiers in scalar scope:\n";
		for (String id : descriptor.getIdentifiersInScalarScope()) {
			ret += "   " + id + "\n";
		}
		ret += "Identifiers in non-scalar scope:\n";
		for (String id : descriptor.getIdentifiersInNonscalarScope()) {
			ret += "   " + id + "\n";
		}
		
		return ret;
	}



}
