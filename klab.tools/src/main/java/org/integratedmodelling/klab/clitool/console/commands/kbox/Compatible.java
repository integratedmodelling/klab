package org.integratedmodelling.klab.clitool.console.commands.kbox;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.StringUtils;

public class Compatible implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		String ret = "";
		String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();

		IConcept concept = declaration.startsWith("k:")
				? Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2))
				: Observables.INSTANCE.declare(declaration);

		Set<Long> ids = Models.INSTANCE.getKbox().getCompatibleTypeIds(Observable.promote(concept));		
				
		for (long id : ids) {
			String idst = "[" + id + "]";
			ret += StringUtils.rightPad(idst, 8) + Models.INSTANCE.getKbox().getTypeDefinition(id) + "\n";
		}
		
		return ret;
	}

}
