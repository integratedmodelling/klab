package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ShowChildren implements ICommand {

    @Override
    public Object execute(IKimFunctionCall call, ISession session) throws KlabValidationException {
        String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();
        IConcept concept = declaration.startsWith("k:")
                ? Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2))
                : Observables.INSTANCE.declare(declaration);
        if (concept == null) {
            throw new KlabValidationException("expression '" + declaration + "' does not specify a concept");
        }
        return listChildren(concept, 0);
    }

    private String listChildren(IConcept concept, int i) {
        String label = concept.getMetadata().getString(IMetadata.DC_LABEL);
        String ret = StringUtils.repeat(" ", i) + concept + (label == null ? "" : (" (" + label + ")"));
        for (IConcept child : concept.getChildren()) {
            ret += "\n" + listChildren(child, i + 3);
        }
        return ret;
    }

}
