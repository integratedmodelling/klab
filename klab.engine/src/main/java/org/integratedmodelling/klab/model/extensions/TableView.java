package org.integratedmodelling.klab.model.extensions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.klab.api.knowledge.IKnowledgeView;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.documentation.extensions.table.Spreadsheet;
import org.integratedmodelling.klab.documentation.extensions.table.Table;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.model.KimObject;

public class TableView extends KimObject implements IKnowledgeView {

	Map<?, ?> definition;
	private String viewClass;
	private String name;
	private INamespace namespace;
	Spreadsheet spreadsheet;

	public TableView(Object definition, IKimSymbolDefinition statement, INamespace namespace, IMonitor monitor) {
		super(statement);
		this.viewClass = statement.getDefineClass();
		this.name = statement.getName();
		this.namespace = namespace;

		if (!(definition instanceof Map)) {
			throw new KlabValidationException("definition is not compatible with a table view");
		}
		this.definition = (Map<?, ?>) definition;
		this.spreadsheet = new Spreadsheet(this.definition, null, monitor);
	}

	@Override
	public String getViewClass() {
		return this.viewClass;
	}

	@Override
	public List<IObservable> getObservables() {
		List<IObservable> ret = new ArrayList<>();
		for (ObservedConcept obs : spreadsheet.getObservables()) {
			ret.add(obs.getObservable());
		}
		return ret;
	}

	@Override
	public void compileView(IContextualizationScope scope) {
		Table table = spreadsheet.compute((IRuntimeScope)scope);
		// TODO set in the context
		// TODO compile and send through the monitor in the scope
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
