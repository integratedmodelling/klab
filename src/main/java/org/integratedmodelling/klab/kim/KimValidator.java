package org.integratedmodelling.klab.kim;

import java.util.EnumSet;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.FunctionDescriptor;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.model.Namespace;

public class KimValidator implements Kim.Validator {

	IMonitor monitor;

	public KimValidator(IMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void synchronizeNamespaceWithRuntime(IKimNamespace namespace) {

		Namespaces.INSTANCE.release(namespace.getName());

		INamespace ns = new Namespace(namespace);

		/*
		 * these should never throw exceptions; instead they should notify any errors,
		 * no matter how internal, through the monitor
		 */
		for (IKimScope statement : namespace.getChildren()) {
			if (statement instanceof IKimConceptStatement) {
				ConceptBuilder.INSTANCE.build((IKimConceptStatement) statement, ns, monitor);
			} else if (statement instanceof IKimModel) {
				ModelBuilder.INSTANCE.build((IKimModel) statement, ns, monitor);
			} else if (statement instanceof IKimObserver) {
				ObservationBuilder.INSTANCE.build((IKimObserver) statement, ns, monitor);
			}
		}

		
		/*
		 * TODO finalize namespace and send any notification
		 */
		Namespaces.INSTANCE.registerNamespace(ns);
        Reasoner.INSTANCE.addOntology(ns.getOntology());
	}

	@Override
	public FunctionDescriptor classifyFunction(String functionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UrnDescriptor classifyUrn(String urn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumSet<Type> classifyCoreType(String string, EnumSet<Type> statedType) {
		IConcept coreType = Concepts.INSTANCE.getConcept(string);
		if (coreType == null) {
			return EnumSet.noneOf(Type.class);
		}
		/*
		 * TODO check type
		 */
		return statedType;
	}

}
