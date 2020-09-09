package org.integratedmodelling.klab.clitool.console.commands.geometry;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.StringUtils;

public class Info implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {
		String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();
		return Geometry.create(declaration).toString();
	}

}
