package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ShowTraits implements ICommand {

    @Override
    public Object execute(IKimFunctionCall call, ISession session) throws KlabValidationException {
        String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();
        IConcept concept = declaration.startsWith("k:")
                ? Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2))
                : Observables.INSTANCE.declare(declaration);
        if (concept == null) {
            throw new KlabValidationException("expression '" + declaration + "' does not specify a concept");
        }
        String ret = "";
        for (IConcept c : Traits.INSTANCE.getTraits(concept.getType())) {
            ret += (ret.isEmpty() ? "" : "\n") + ""
                    + Traits.INSTANCE.getBaseParentTrait(c) + ": " + c.getType();
        }
        return ret;
    }

}
