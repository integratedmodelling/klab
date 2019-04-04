package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Roles;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.utils.StringUtils;

public class ShowInfo implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

        String ret = "";
        String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();

        if (!declaration.contains(":")) {
            INamespace namespace = Namespaces.INSTANCE.getNamespace(declaration);
            if (namespace == null) {
                throw new KlabValidationException("expression '" + declaration
                        + "' does not specify a concept or an ontology");
            }
            ret += describe(namespace.getOntology());

        } else {

            IConcept concept = declaration.startsWith("k:")
                    ? Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2))
                    : Observables.INSTANCE.declare(declaration);

            if (concept == null) {
                throw new KlabValidationException("expression '" + declaration
                        + "' does not specify a concept");
            }

            for (IConcept c : concept.getOperands()) {
                ret += (ret.isEmpty() ? "" : (concept.is(Type.UNION) ? "\n  OR\n" : "\n  AND\n"))
                        + describe(c);
            }
        }
        return ret;
    }

    private String describe(IOntology ontology) {

        String ret = "";
        ret += "Imports:\n" + printImports(ontology, 3, new HashSet<>());
        ret += "Concepts:\n";
        for (IConcept c : ontology.getConcepts()) {
            ret += "   " + c + " [" + c.getDefinition() + "]" + "\n";
        }
        return ret;
    }

    private String printImports(IOntology owlOntology, int i, Set<IOntology> done) {
        String ret = "";
        String spaces = StringUtils.spaces(i);
        for (IOntology o : owlOntology.getImports(false)) {
            boolean added = done.add(o);
            ret += spaces + o + "\n"
                    + (added ? printImports(o, i + 3, done) : "");
        }
        return ret;
    }

    public static String describe(IConcept concept) {

        String ret = "";
        ret += "Core observable: " + Observables.INSTANCE.getCoreObservable(concept).getDefinition() + "\n";
        ret += "Definition:    " + concept.getDefinition() + "\n";
        ret += Arrays.toString(((Concept) concept.getType()).getTypeSet().toArray()) + "\n";
        ret += "        Context type: " + decl(Observables.INSTANCE.getContextType(concept.getType()))
                + " [direct: "
                + decl(Observables.INSTANCE.getDirectContextType(concept.getType())) + "]\n";
        ret += "       Inherent type: " + decl(Observables.INSTANCE.getInherentType(concept.getType()))
                + " [direct: "
                + decl(Observables.INSTANCE.getDirectInherentType(concept.getType())) + "]\n";
        ret += "        Causant type: " + decl(Observables.INSTANCE.getCausantType(concept.getType()))
                + " [direct: "
                + decl(Observables.INSTANCE.getDirectCausantType(concept.getType())) + "]\n";
        ret += "         Caused type: " + decl(Observables.INSTANCE.getCausedType(concept.getType()))
                + " [direct: "
                + decl(Observables.INSTANCE.getDirectCausedType(concept.getType())) + "]\n";
        ret += "           Goal type: " + decl(Observables.INSTANCE.getGoalType(concept.getType()))
                + " [direct: "
                + decl(Observables.INSTANCE.getDirectGoalType(concept.getType())) + "]\n";
        ret += "       Adjacent type: " + decl(Observables.INSTANCE.getAdjacentType(concept.getType()))
                + " [direct: "
                + decl(Observables.INSTANCE.getDirectAdjacentType(concept.getType())) + "]\n";
        ret += "     Compresent type: " + decl(Observables.INSTANCE.getCompresentType(concept.getType()))
                + " [direct: "
                + decl(Observables.INSTANCE.getDirectCompresentType(concept.getType())) + "]\n";
        ret += "   Co-occurrent type: " + decl(Observables.INSTANCE.getCooccurrentType(concept.getType()))
                + " [direct: " + decl(Observables.INSTANCE.getDirectCooccurrentType(concept.getType()))
                + "]\n";

        Collection<IConcept> allTraits = Traits.INSTANCE.getTraits(concept.getType());
        Collection<IConcept> dirTraits = Traits.INSTANCE.getDirectTraits(concept.getType());
        if (!allTraits.isEmpty()) {
            ret += "Traits:\n";
            for (IConcept trait : allTraits) {
                ret += "    " + trait.getDefinition()
                        + (dirTraits.contains(trait) ? " [direct]" : " [indirect]") + "\n";
            }
        }

        Collection<IConcept> allRoles = Roles.INSTANCE.getRoles(concept.getType());
        Collection<IConcept> dirRoles = Roles.INSTANCE.getDirectRoles(concept.getType());
        if (!allRoles.isEmpty()) {
            ret += "Roles:\n";
            for (IConcept trait : allRoles) {
                ret += "    " + trait.getDefinition()
                        + (dirRoles.contains(trait) ? " [direct]" : " [indirect]") + "\n";
            }
        }

        return ret;
    }

    static String decl(IConcept concept) {
        return concept == null ? "NONE" : concept.getDefinition();
    }

}
