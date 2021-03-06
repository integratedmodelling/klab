package org.integratedmodelling.kim.ui.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.kim.Annotation;
import org.integratedmodelling.kim.kim.Concept;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.kim.kim.ConceptReference;
import org.integratedmodelling.kim.kim.ConceptStatementBody;
import org.integratedmodelling.kim.kim.Function;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.ConceptDescriptor;

public class KimHoverProvider extends DefaultEObjectHoverProvider {

	@Override
	protected String getFirstLine(EObject o) {

		String name = null;
		if (o instanceof ConceptReference && ((ConceptReference) o).getName() != null) {
			name = ((ConceptReference) o).getName();
		} else if (o instanceof Concept && ((Concept) o).getName() != null
				&& ((Concept) o).getName().getName() != null) {
			name = ((Concept) o).getName().getName();
		}

		if (name != null) {

			// Agh
			boolean isCore = o.eContainer() instanceof Concept
					&& o.eContainer().eContainer() instanceof ConceptDeclaration
					&& o.eContainer().eContainer().eContainer() instanceof ConceptStatementBody
					&& ((ConceptStatementBody) o.eContainer().eContainer().eContainer()).isCoreConcept();

			if (isCore) {
				return "Core ontology concept <b>" + name + "</b>";
			}

			ConceptDescriptor cd = Kim.INSTANCE.getConceptDescriptor(name);
			if (cd != null) {
				String ret = cd.is(Type.SUBJECTIVE) ? "subjective" : "";
				if (cd.is(Type.DENIABLE)) {
					ret += (ret.isEmpty() ? "" : " ") + "deniable ";
				}
				if (cd.is(Type.INTENSIVE_PROPERTY)) {
					ret += (ret.isEmpty() ? "" : " ") + "intensive physical property <b>" + name + "</b>";
				} else if (cd.is(Type.EXTENSIVE_PROPERTY)) {
					ret += (ret.isEmpty() ? "" : " ") + "extensive physical property <b>" + name + "</b>";
				} else if (cd.is(Type.QUALITY)) {
					ret += (ret.isEmpty() ? "" : " ") + "quality <b>" + name + "</b>";
				} else if (cd.is(Type.ATTRIBUTE)) {
					ret += (ret.isEmpty() ? "" : " ") + "attribute <b>" + name + "</b>";
				} else if (cd.is(Type.REALM)) {
					ret += (ret.isEmpty() ? "" : " ") + "realm <b>" + name + "</b>";
				} else if (cd.is(Type.IDENTITY)) {
					ret += (ret.isEmpty() ? "" : " ") + "identity <b>" + name + "</b>";
				} else if (cd.is(Type.PROCESS)) {
					ret += (ret.isEmpty() ? "" : " ") + "process <b>" + name + "</b>";
				} else if (cd.is(Type.RELATIONSHIP)) {
					ret += (ret.isEmpty() ? "" : " ") + (cd.is(Type.FUNCTIONAL) ? "functional " : "")
							+ (cd.is(Type.STRUCTURAL) ? "structural " : "")
							+ (cd.is(Type.BIDIRECTIONAL) ? "bond <b>" : "relationship <b>") + name + "</b>";
				} else if (cd.is(Type.EVENT)) {
					ret += (ret.isEmpty() ? "" : " ") + "event <b>" + name + "</b>";
				} else if (cd.is(Type.ROLE)) {
					ret += (ret.isEmpty() ? "" : " ") + "role <b>" + name + "</b>";
				} else if (cd.is(Type.CONFIGURATION)) {
					ret += (ret.isEmpty() ? "" : " ") + "configuration <b>" + name + "</b>";
				} else if (cd.is(Type.DOMAIN)) {
					ret += (ret.isEmpty() ? "" : " ") + "domain <b>" + name + "</b>";
				} else if (cd.is(Type.EXTENT)) {
					ret += (ret.isEmpty() ? "" : " ") + "extent <b>" + name + "</b>";
				} else if (cd.is(Type.SUBJECT)) {
					ret += (ret.isEmpty() ? "" : " ") + "subject <b>" + name + "</b>";
				} else if (cd.is(Type.AGENT)) {
					ret += (ret.isEmpty() ? "" : " ") + (cd.is(Type.INTERACTIVE) ? "interactive " : "")
							+ (cd.is(Type.REACTIVE) ? "reactive " : "")
							+ (cd.is(Type.DELIBERATIVE) ? "deliberative " : "") + "agent <b>" + name + "</b>";
				}

				return ret;

			} else {
				return "Undefined concept <b>" + name + "</b>";
			}
		} else if (o instanceof ConceptStatementBody) {
			return "Definition of <b>" + ((ConceptStatementBody) o).getName() + "</b>";
		} else if (o instanceof Function) {
			return "k.IM function <b>" + ((Function) o).getName() + "</b>";
		} else if (o instanceof Annotation) {
			return "k.IM annotation <b>" + ((Annotation) o).getName().substring(1) + "</b>";
		}
		return super.getFirstLine(o);
	}

}
