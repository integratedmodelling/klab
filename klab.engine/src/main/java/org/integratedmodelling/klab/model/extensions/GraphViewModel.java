package org.integratedmodelling.klab.model.extensions;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.model.KimObject;

public class GraphViewModel extends KimObject implements IViewModel {

	Map<?, ?> definition;
	private String viewClass;
	private String name;
	private INamespace namespace;

	public GraphViewModel(Object definition, IKimSymbolDefinition statement, INamespace namespace, IMonitor monitor) {
		super(statement);
		this.viewClass = statement.getDefineClass();
		this.name = statement.getName();
		this.namespace = namespace;

		if (!(definition instanceof Map)) {
			throw new KlabValidationException("definition is not compatible with a graph view");
		}
		this.definition = (Map<?, ?>) definition;
	}

	@Override
	public String getViewClass() {
		return this.viewClass;
	}

	@Override
	public List<IObservable> getObservables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IKnowledgeView compileView(IObservation target, IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return this.name;
	}

	@Override
	public String getName() {
		return this.namespace.getName() + "." + this.name;
	}

	@Override
	public INamespace getNamespace() {
		return this.namespace;
	}
}
