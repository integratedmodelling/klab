package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.exceptions.KlabUnauthorizedUrnException;
import org.integratedmodelling.klab.exceptions.KlabUnknownUrnException;

public class Model extends KimObject implements IModel {

	private static final long serialVersionUID = 6405594042208542702L;

	private Optional<IResource> resource = Optional.empty();
	private Optional<IDocumentation> documentation = Optional.empty();
	private List<IObservable> observables = new ArrayList<>();
	private List<IObservable> dependencies = new ArrayList<>();
	private Map<String, IObservable> attributeObservables = new HashMap<>();

	public Model(IKimModel model, IMonitor monitor) {

		super(model);

		for (IKimObservable observable : model.getObservables()) {
			if (observable.getAttribute().isPresent()) {
				attributeObservables.put(observable.getAttribute().get(),
						Observables.INSTANCE.declare(observable, monitor));
			} else {
				observables.add(Observables.INSTANCE.declare(observable, monitor));
			}
		}

		for (IKimObservable dependency : model.getDependencies()) {
			dependencies.add(Observables.INSTANCE.declare(dependency, monitor));
		}

		if (model.getResourceUrn().isPresent()) {
			try {
				this.resource = Optional.of(Resources.INSTANCE.getResource(model.getResourceUrn().get()));
			} catch (KlabUnknownUrnException | KlabUnauthorizedUrnException e) {
				monitor.error(e, model);
			}
		}

		/*
		 * TODO contextualizers
		 */

		/*
		 * TODO actions
		 */

		if (model.getMetadata() != null) {
			setMetadata(new Metadata(model.getMetadata()));
		}

		/*
		 * TODO documentation
		 */
	}

	@Override
	public List<IObservable> getObservables() {
		return observables;
	}

	@Override
	public Optional<IResource> getResource() {
		return resource;
	}

	@Override
	public Map<String, IObservable> getAttributeObservables() {
		return attributeObservables;
	}

	@Override
	public String getLocalNameFor(IObservable observable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isResolved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInstantiator() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReinterpreter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<IDocumentation>	 getDocumentation() {
		return documentation;
	}

	@Override
	public IKimStatement getStatement() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public INamespace getNamespace() {
        // TODO Auto-generated method stub
        return null;
    }

}
