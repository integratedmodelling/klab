package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.RestrictionCollector;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;

public class ShowRestrictions implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws KlabValidationException {
        String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();
        IConcept concept = declaration.startsWith("k:")
                ? Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2))
                : Observables.INSTANCE.declare(declaration);
        if (concept == null) {
            throw new KlabValidationException("expression '" + declaration + "' does not specify a concept");
        }

        RestrictionCollector collector = new RestrictionCollector(concept.getType());
        String ret = "";

        for (OWLQuantifiedRestriction<?, ?, ? extends OWLClassExpression> c : collector.getRestrictions()) {
            String rname = c.getClass().getSimpleName();
            if (rname.startsWith("OWLObject")) {
                rname = rname.substring(9);
            }
            if (rname.endsWith("Impl")) {
                rname = rname.substring(0, rname.length() - 4);
            }
            IProperty property = OWL.INSTANCE.getPropertyFor((OWLProperty<?, ?>) c.getProperty());
            ret += (ret.isEmpty() ? "" : "\n") + rname + "  " + display(property) + " "
                    + owlToString(c.getFiller());
        }
        return ret;
    }

    public String display(IKnowledge k) {
        String label = k.getMetadata().getString(IMetadata.DC_LABEL);
        return label == null ? k.toString() : label;
    }

    public String owlToString(OWLClassExpression owlClassExpression) {

        String ret = "nothing";
        if (owlClassExpression != null) {
            if (owlClassExpression instanceof OWLObjectComplementOf) {
                ret = "(not " + owlToString(((OWLObjectComplementOf) owlClassExpression).getOperand()) + ")";
            } else if (owlClassExpression instanceof OWLObjectUnionOf) {
                ret = "";
                for (OWLClassExpression operand : ((OWLObjectUnionOf) owlClassExpression).getOperands()) {
                    ret += (ret.isEmpty() ? "" : " or ") + owlToString(operand);
                }
                ret = "(" + ret + ")";
            } else if (owlClassExpression instanceof OWLObjectIntersectionOf) {
                ret = "";
                for (OWLClassExpression operand : ((OWLObjectIntersectionOf) owlClassExpression)
                        .getOperands()) {
                    ret += (ret.isEmpty() ? "" : " or ") + owlToString(operand);
                }
                ret = "(" + ret + ")";
            } else if (owlClassExpression.isAnonymous()) {
                ret = owlClassExpression.toString();
            } else {
                IConcept f = OWL.INSTANCE.getConceptFor(owlClassExpression.asOWLClass(), false);
                if (f != null) {
                    ret = display(f);
                } else {
                    ret = owlClassExpression.toString();
                }
            }
        }
        return ret;
    }

}
