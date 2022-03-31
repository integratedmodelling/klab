package org.integratedmodelling.klab.cli.commands.reason;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class Affected implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

        String ret = "";

        List<String> declarations = new ArrayList<>();
        String declaration = "";
        for (Object p : (List<?>) call.getParameters().get("arguments")) {
            if (p.toString().equals("vs.")) {
                declarations.add(declaration.trim());
                declaration = "";
            } else {
                declaration += (declaration.isEmpty() ? "" : " ") + p.toString();
            }
        }

        List<IConcept> concepts = new ArrayList<>();
        if (!declarations.isEmpty()) {
            if (!declaration.isEmpty()) {
                declarations.add(declaration);
            }
            for (String d : declarations) {
                concepts.add(Observables.INSTANCE.declare(d).getType());
            }
        }

        if (concepts.size() != 2) {
            throw new KlabIllegalArgumentException(
                    "use two concepts to check if the first is affected or created by the second");
        }

        boolean affected = false;
        if (Observables.INSTANCE.isAffectedBy(concepts.get(0), concepts.get(1))) {
            affected = true;
            ret += concepts.get(0).getDefinition() + " IS AFFECTED BY " + concepts.get(1).getDefinition()
                    + "\n";
        }
        if (Observables.INSTANCE.isCreatedBy(concepts.get(0), concepts.get(1))) {
            affected = true;
            ret += concepts.get(0).getDefinition() + " IS CREATED BY " + concepts.get(1).getDefinition()
                    + "\n";
        }

        if (!affected) {
            ret += concepts.get(0).getDefinition() + " IS NOT AFFECTED OR CREATED BY "
                    + concepts.get(1).getDefinition();
        }

        return ret;
    }

}
