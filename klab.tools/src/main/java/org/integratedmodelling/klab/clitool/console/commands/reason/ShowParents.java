package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ShowParents implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws KlabValidationException {
        String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();
        IConcept concept = null;
        if (declaration.startsWith("k:")) {
            concept = Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2));
        }
        if (concept == null) {
            IObservable observable = Observables.INSTANCE.declare(declaration);
            concept = observable.getType();
        }
        
        if (concept == null) {
            throw new KlabValidationException("expression '" + declaration + "' does not specify a concept");
        }
        return listParents(concept, 0);
    }

    private String listParents(IConcept concept, int i) {
        String label = concept.getMetadata().get(IMetadata.DC_LABEL, String.class);
        String ret = StringUtils.repeat(" ", i) + concept + (label == null ? "" : (" (" + label + ")"));
        for (IConcept child : concept.getParents()) {
            ret += "\n" + listParents(child, i + 3);
        }
        return ret;
    }

}
