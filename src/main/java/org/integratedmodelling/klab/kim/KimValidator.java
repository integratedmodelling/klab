package org.integratedmodelling.klab.kim;

import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.FunctionDescriptor;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
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

		INamespace ns = new Namespace(namespace);

		/*
		 * TODO prepare kbox and caches to receive namespace
		 */
		
		for (IKimStatement statement : namespace.getStatements()) {
			if (statement instanceof IKimConceptStatement) {
				ConceptBuilder.INSTANCE.build((IKimConceptStatement) statement, ns, monitor);
			} else if (statement instanceof IKimModel) {
				ModelBuilder.INSTANCE.build((IKimModel) statement, ns, monitor);
			} else if (statement instanceof IKimObserver) {
				ObservationBuilder.INSTANCE.build((IKimObserver) statement, ns, monitor);
			} // TODO defines
		}
		
		/*
		 * TODO finalize namespace and send any notification
		 */
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
	public long classifyCoreType(String string) {
		// TODO Auto-generated method stub
		return 0;
	}

}
